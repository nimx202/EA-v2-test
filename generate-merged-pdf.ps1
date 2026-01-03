# PowerShell script to generate and merge code PDF and JavaDoc PDF
# Requirements: wkhtmltopdf (for HTML to PDF conversion) and PyPDF2 (for merging)

param(
    [string]$OutputFile = "complete_documentation.pdf",
    [switch]$Help
)

if ($Help) {
    Write-Host @"
Usage: .\generate-merged-pdf.ps1 [options]

Options:
  -OutputFile <filename>  Name of the final merged PDF (default: complete_documentation.pdf)
  -Help                   Show this help message

This script will:
  1. Generate styled code PDF from source code
  2. Generate JavaDoc PDF documentation
  3. Merge both PDFs into a single document

"@
    exit 0
}

$ErrorActionPreference = "Stop"

Write-Host "================================" -ForegroundColor Cyan
Write-Host "  Code + JavaDoc PDF Generator  " -ForegroundColor Cyan
Write-Host "================================" -ForegroundColor Cyan
Write-Host ""

# Define intermediate files
$CodePdfFile = "code_styled_temp.pdf"
$JavaDocPdfFile = "javadoc_temp.pdf"
$CodeHtmlFile = "code_styled.html"

# Step 1: Generate Code HTML
Write-Host "[1/4] Generating styled code HTML..." -ForegroundColor Yellow
& .\generate-code-pdf.bat $CodeHtmlFile | Out-Null

if (-not (Test-Path $CodeHtmlFile)) {
    Write-Host "[ERROR] Failed to generate code HTML" -ForegroundColor Red
    exit 1
}
Write-Host "      [OK] Code HTML generated" -ForegroundColor Green

# Step 2: Convert HTML to PDF using wkhtmltopdf
Write-Host "[2/4] Converting HTML to PDF..." -ForegroundColor Yellow

# Try to find wkhtmltopdf (same approach as javadoc script)
$wkhtmltopdfPaths = @(
    "C:\Program Files\wkhtmltopdf\bin\wkhtmltopdf.exe",
    "C:\Program Files (x86)\wkhtmltopdf\bin\wkhtmltopdf.exe",
    "wkhtmltopdf.exe"  # If in PATH
)

$wkhtmltopdfPath = $null
foreach ($path in $wkhtmltopdfPaths) {
    if (Test-Path $path) {
        $wkhtmltopdfPath = $path
        break
    }
}

if ($wkhtmltopdfPath) {
    Write-Host "      Found wkhtmltopdf: $wkhtmltopdfPath" -ForegroundColor Green
    
    # Build wkhtmltopdf command with landscape orientation and minimal margins
    $wkhtmlArgs = @(
        "--enable-local-file-access",
        "-O", "Landscape",
        "--margin-top", "0.15in",
        "--margin-bottom", "0.15in",
        "--margin-left", "0.15in",
        "--margin-right", "0.15in",
        "--dpi", "300",
        "--print-media-type",
        "--quiet",
        "$CodeHtmlFile",
        "$CodePdfFile"
    )
    
    & $wkhtmltopdfPath @wkhtmlArgs
    
    if ($LASTEXITCODE -eq 0 -and (Test-Path $CodePdfFile) -and ((Get-Item $CodePdfFile).Length -gt 0)) {
        Write-Host "      [OK] HTML converted to PDF (Landscape, minimal margins)" -ForegroundColor Green
    } else {
        Write-Host "      [WARNING] wkhtmltopdf conversion failed or produced empty file" -ForegroundColor Yellow
        Remove-Item -Force -ErrorAction SilentlyContinue -Path $CodePdfFile
    }
} else {
    Write-Host "      [WARNING] wkhtmltopdf not found" -ForegroundColor Yellow
    Write-Host "      Install from: https://wkhtmltopdf.org/downloads.html" -ForegroundColor Cyan
}

# Step 3: Generate JavaDoc PDF
Write-Host "[3/4] Generating JavaDoc PDF..." -ForegroundColor Yellow
$javadocOutput = & .\generate-javadoc-pdf.ps1 2>&1
Write-Host $javadocOutput

# Find the generated JavaDoc PDF (check multiple locations)
$possiblePaths = @(
    "C:\Users\Nassr\javadoc\Javadoc.pdf",
    "$PSScriptRoot\javadoc\Javadoc.pdf",
    ".\javadoc.pdf",
    "javadoc.pdf"
)

$JavaDocPdfFound = $false
foreach ($path in $possiblePaths) {
    if (Test-Path $path) {
        $JavaDocPdfFile = (Resolve-Path $path).Path
        Write-Host "      [OK] JavaDoc PDF found: $(Split-Path $JavaDocPdfFile -Leaf)" -ForegroundColor Green
        $JavaDocPdfFound = $true
        break
    }
}

if (-not $JavaDocPdfFound) {
    Write-Host "      [ERROR] JavaDoc PDF not found at expected locations:" -ForegroundColor Red
    $possiblePaths | ForEach-Object { Write-Host "             $_" -ForegroundColor Red }
    exit 1
}

# Step 4: Merge PDFs using Python
Write-Host "[4/4] Merging PDFs..." -ForegroundColor Yellow

# Find Python installation
$pythonExe = $null
$pythonPaths = @(
    "py",
    "python3",
    "python"
)

foreach ($py in $pythonPaths) {
    try {
        $output = & $py --version 2>&1
        if ($LASTEXITCODE -eq 0) {
            $pythonExe = $py
            Write-Host "      Found Python: $output" -ForegroundColor Green
            break
        }
    } catch {
        # Continue to next Python path
    }
}

if (-not $pythonExe) {
    Write-Host "      [WARNING] Python not found in PATH" -ForegroundColor Yellow
    Write-Host "      Skipping PDF merge - using JavaDoc PDF only" -ForegroundColor Yellow
    Write-Host ""
    
    # Copy JavaDoc as the final output
    if (Test-Path $JavaDocPdfFile) {
        Copy-Item -Path $JavaDocPdfFile -Destination $OutputFile -Force
        Write-Host "      [OK] JavaDoc PDF copied as: $OutputFile" -ForegroundColor Green
    }
} else {
    # Create Python script for PDF merging
    $mergeScript = @"
import sys
import os

try:
    from PyPDF2 import PdfMerger
except ImportError:
    print("Installing PyPDF2...")
    os.system("py -m pip install PyPDF2 --quiet")
    from PyPDF2 import PdfMerger

try:
    code_pdf = sys.argv[1]
    javadoc_pdf = sys.argv[2]
    output_pdf = sys.argv[3]
    
    merger = PdfMerger()
    
    # Add code PDF if it exists
    if os.path.exists(code_pdf) and os.path.getsize(code_pdf) > 0:
        merger.append(code_pdf)
        print(f"  Added: {os.path.basename(code_pdf)}")
    else:
        print(f"  Skipping: {code_pdf} (not found or empty)")
    
    # Add JavaDoc PDF
    if os.path.exists(javadoc_pdf):
        merger.append(javadoc_pdf)
        print(f"  Added: {os.path.basename(javadoc_pdf)}")
    else:
        print(f"  Error: {javadoc_pdf} not found")
        sys.exit(1)
    
    # Write merged PDF
    merger.write(output_pdf)
    merger.close()
    
    print(f"  Merged: {output_pdf}")
    
except Exception as e:
    print(f"Error: {e}")
    sys.exit(1)
"@

    # Write and execute merge script
    $mergeScriptPath = "merge_pdfs.py"
    $mergeScript | Out-File -FilePath $mergeScriptPath -Encoding UTF8 -Force

    try {
        Write-Host "      Using Python: $pythonExe" -ForegroundColor Cyan
        & $pythonExe "$mergeScriptPath" "$CodePdfFile" "$JavaDocPdfFile" "$OutputFile"
        
        if ($LASTEXITCODE -eq 0 -and (Test-Path $OutputFile)) {
            Write-Host "      [OK] PDF merge successful" -ForegroundColor Green
        } else {
            Write-Host "      [ERROR] PDF merge failed" -ForegroundColor Red
            exit 1
        }
    } catch {
        Write-Host "      [ERROR] An error occurred during PDF merge" -ForegroundColor Red
        exit 1
    }
}

# Final output
Write-Host ""
Write-Host "================================" -ForegroundColor Green
Write-Host "  [OK] Complete!" -ForegroundColor Green
Write-Host "================================" -ForegroundColor Green
Write-Host ""
if (Test-Path $OutputFile) {
    Write-Host "Output: $OutputFile" -ForegroundColor Green
    Write-Host "Size: $([Math]::Round((Get-Item $OutputFile).Length / 1MB, 2)) MB" -ForegroundColor Green
    Write-Host ""
    
    # Cleanup temp files (only if they exist)
    $filesToClean = @($CodePdfFile, $CodeHtmlFile)
    if ($mergeScriptPath) { $filesToClean += $mergeScriptPath }
    
    foreach ($file in $filesToClean) {
        if (Test-Path $file) {
            Remove-Item -Force -ErrorAction SilentlyContinue -Path $file
        }
    }
    
    # Offer to open the PDF
    $openPdf = Read-Host "Open PDF? (y/n)"
    if ($openPdf -eq "y") {
        Invoke-Item $OutputFile
    }
}
