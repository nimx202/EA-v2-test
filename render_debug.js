const puppeteer = require('puppeteer');
const path = require('path');
(async(){
  const inPath = process.argv[2];
  const pngOut = process.argv[3];
  const pdfOut = process.argv[4];
  const abs = path.resolve(inPath);
  const browser = await puppeteer.launch({args:['--no-sandbox','--disable-setuid-sandbox']});
  const page = await browser.newPage();
  await page.setViewport({width:3200,height:2200});
  await page.goto('file://' + abs, {waitUntil:'networkidle0'});
  await page.screenshot({path: pngOut, fullPage: true});
  await page.pdf({path: pdfOut, format:'A4', printBackground:true, scale:1.05, landscape:true, margin:{top:'0.05in', bottom:'0.05in', left:'0.05in', right:'0.05in'}});
  await browser.close();
})();
