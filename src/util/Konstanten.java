package util;

/**
 * Zentrale Konstantendefinitionen für das gesamte Projekt.
 * Alle Magic Numbers und Magic Strings sind hier definiert.
 * Dies ermöglicht einfaches Anpassen von Konfigurationen an einer Stelle.
 *
 * Design: Utility-Klasse mit nur statischen Konstanten (keine Instanzen möglich)
 */
public final class Konstanten {

    /**
     * Privater Konstruktor verhindert Instanziierung der Utility-Klasse.
     */
    private Konstanten() {
        // Keine Instanzen möglich
    }

    // ==================== CSV-Konfiguration ====================

    /** Zeichensatz für das Einlesen der CSV-Datei */
    public static final String CSV_ZEICHENSATZ = "UTF-8";

    /** Trennzeichen in CSV-Zeilen */
    public static final String CSV_TRENNZEICHEN = ",";

    /** Pfad zur CSV-Eingabedatei (relativ zum Projektverzeichnis) */
    public static final String RESSOURCENPFAD = "src/res/Windkraftanlagen_DE.csv";

    /** Erwartete Anzahl der Spalten in jeder CSV-Zeile */
    public static final int ERWARTET_FELDANZAHL = 12;

    // ==================== FeldParser Konstanten ====================

    /** Leerer String-Wert */
    public static final String LEERER_WERT = "";

    /** Ungültige ID für fehlerhafte Ganzzahl-Konvertierungen */
    public static final int UNGÜLTIGE_ID = -1;

    /** Anzeigetext für unbekannte Werte */
    public static final String ANZEIGE_UNBEKANNT = "unbekannt";

    // ==================== Main View Konstanten ====================

    /** Limit für Top-Windparks Anzeige */
    public static final int TOP_LIMIT = 5;

    /** Limit für Beispiel-Datensätze */
    public static final int BEISPIEL_LIMIT = 10;

    /** Konvertierung von Nanosekunden zu Millisekunden */
    public static final double NANOS_ZU_MILLIS = 1_000_000.0;

    /** Platzhalter für unbekannte Orte */
    public static final String UNBEKANNTER_ORT = "<unbekannt>";

    // ==================== Ausgabetexte ====================

    /** Fehlermeldung: CSV-Datei nicht gefunden */
    public static final String CSV_NICHT_GEFUNDEN = "CSV-Datei nicht gefunden: ";

    /** Erfolgsmeldung: Laden abgeschlossen */
    public static final String LADEN_ABGESCHLOSSEN = "Einlesen der CSV abgeschlossen.";

    /** Info: Dateipfad */
    public static final String DATEI_INFO = "Datei: ";

    /** Format: Anzahl Datensätze */
    public static final String DATENSÄTZE_ANZAHL = "Anzahl eingelesener Datensätze: %d%n";

    /** Format: Verstrichene Zeit */
    public static final String VERSTRICHENE_ZEIT = "Dauer des Einlesens: %.3f ms%n";

    /** Format: Anlagen mit Koordinaten */
    public static final String MIT_KOORDINATEN = "Anlagen mit Koordinaten: %d%n";

    /** Format: Anlagen ohne Betreiber */
    public static final String OHNE_BETREIBER = "Anlagen ohne Betreiber-Angabe: %d%n";

    /** Format: Top Windparks Überschrift */
    public static final String TOP_PARKS = "Top %d Windparks/Anlagen nach Anzahl:%n";

    /** Format: Gesamtanzahl Parks */
    public static final String GESAMT_PARKS = "Gesamtanzahl unterschiedlicher Windparks/Anlagen: %d%n";

    /** Überschrift: Beispiel-Datensätze */
    public static final String BEISPIEL_DATENSÄTZE = "Einige Beispiel-Datensätze:";

    /** Fehler-Präfix */
    public static final String FEHLER_PREFIX = "Fehler beim Einlesen: ";

    /** Trennzeichen für Ausgabe (Doppelpunkt und Leerzeichen) */
    public static final String AUSGABE_TRENNZEICHEN = ": ";

    // ==================== CsvParser Konstanten ====================

    /** Anführungszeichen-Zeichen */
    public static final char ANFÜHRUNGSZEICHEN = '\"';

    /** Komma-Zeichen */
    public static final char KOMMA = ',';

    /** Wagenrücklauf-Zeichen */
    public static final char WAGENRÜCKLAUF = '\r';

    /** Zeilenumbruch-Zeichen */
    public static final char ZEILENUMBRUCH = '\n';

    /** Markierungsabstand für BufferedReader */
    public static final int MARKIERUNGSABSTAND = 1;

    /** Escaped Anführungszeichen (zwei Anführungszeichen) */
    public static final String ESCAPED_ANFÜHRUNGSZEICHEN = "\"\"";

    /** Einzelnes Anführungszeichen als String */
    public static final String EINZELNES_ANFÜHRUNGSZEICHEN = "\"";

    // ==================== Koordinaten-Validierung ====================

    /** Minimaler gültiger Breitengrad für Deutschland */
    public static final double MIN_BREITENGRAD_DE = 47.0;

    /** Maximaler gültiger Breitengrad für Deutschland */
    public static final double MAX_BREITENGRAD_DE = 55.0;

    /** Minimaler gültiger Längengrad für Deutschland */
    public static final double MIN_LÄNGENGRAD_DE = 5.0;

    /** Maximaler gültiger Längengrad für Deutschland */
    public static final double MAX_LÄNGENGRAD_DE = 16.0;

    /** Fehlerschwelle für Breitengrad-Korrektur (Faktor 1000) */
    public static final double BREITENGRAD_FEHLERFAKTOR = 1000.0;

    /** Fehlerschwelle für Längengrad-Korrektur (Faktor 1000) */
    public static final double LÄNGENGRAD_FEHLERFAKTOR = 1000.0;

    // ==================== Baujahr-Validierung ====================

    /** Minimales gültiges Baujahr (erste Windkraftanlagen in Deutschland) */
    public static final int MIN_BAUJAHR = 1980;

    /** Maximales gültiges Baujahr (aktuelles Jahr) */
    public static final int MAX_BAUJAHR = 2026;

    /** Fehlerfaktor für Baujahr-Korrektur (überzählige Ziffer) */
    public static final int BAUJAHR_FEHLERFAKTOR = 10;

    // ==================== Gesamtleistung-Validierung ====================

    /** Minimale gültige Gesamtleistung in MW (keine negativen Werte) */
    public static final double MIN_GESAMTLEISTUNG_MW = 0.0;

    /** Maximale gültige Gesamtleistung in MW (realistischer Wert für Einzelanlage) */
    public static final double MAX_GESAMTLEISTUNG_MW = 200.0;

    /** Fehlerfaktor für Gesamtleistung-Korrektur (fehlendes Komma) */
    public static final double GESAMTLEISTUNG_FEHLERFAKTOR = 100.0;

    // ==================== Ausgabetexte Koordinaten-Korrektur ====================

    /** Überschrift: Koordinaten-Validierung */
    public static final String VALIDIERUNG_ÜBERSCHRIFT = "\n=== Überprüfung und Korrektur der Koordinaten ===";

    /** Format: Validierungszeit */
    public static final String VALIDIERUNG_ZEIT = "Dauer der Überprüfung: %.3f ms%n";

    /** Format: Korrekturzeit */
    public static final String KORREKTUR_ZEIT = "Dauer der Korrektur: %.3f ms%n";

    /** Format: Anzahl fehlerhafte Koordinaten */
    public static final String FEHLERHAFTE_KOORDINATEN = "Anzahl Datensätze mit fehlerhaften Koordinaten: %d%n";

    /** Format: Anzahl korrigierte Koordinaten */
    public static final String KORRIGIERTE_KOORDINATEN = "Anzahl korrigierte Datensätze: %d%n";

    /** Überschrift: Fehlerhafte Datensätze */
    public static final String FEHLERHAFTE_DATENSÄTZE = "\nFehlerhafte Datensätze (vor Korrektur):";

    /** Format: Fehlerhafter Datensatz */
    public static final String FEHLER_DATENSATZ = "ID %d | %s | Breitengrad: %.4f | Längengrad: %.4f%n";

    /** Überschrift: Korrigierte Datensätze */
    public static final String KORRIGIERTE_DATENSÄTZE = "\nKorrigierte Datensätze (nach Korrektur):";

    /** Format: Korrigierter Datensatz */
    public static final String KORREKTUR_DATENSATZ = "ID %d | %s | Alt: (%.4f, %.4f) -> Neu: (%.4f, %.4f)%n";

    // ==================== Ausgabetexte Baujahr/Leistung-Korrektur ====================

    /** Format: Anzahl fehlerhafte Baujahre */
    public static final String FEHLERHAFTE_BAUJAHRE = "Anzahl Datensätze mit fehlerhaftem Baujahr: %d%n";

    /** Format: Anzahl korrigierte Baujahre */
    public static final String KORRIGIERTE_BAUJAHRE = "Anzahl korrigierte Baujahre: %d%n";

    /** Format: Fehlerhaftes Baujahr */
    public static final String FEHLER_BAUJAHR = "ID %d | %s | Baujahr: %d%n";

    /** Format: Korrigiertes Baujahr */
    public static final String KORREKTUR_BAUJAHR = "ID %d | %s | Alt: %d -> Neu: %s%n";

    /** Format: Anzahl fehlerhafte Gesamtleistung */
    public static final String FEHLERHAFTE_LEISTUNGEN = "Anzahl Datensätze mit fehlerhafter Gesamtleistung: %d%n";

    /** Format: Anzahl korrigierte Gesamtleistung */
    public static final String KORRIGIERTE_LEISTUNGEN = "Anzahl korrigierte Gesamtleistungen: %d%n";

    /** Format: Fehlerhafte Gesamtleistung */
    public static final String FEHLER_LEISTUNG = "ID %d | %s | Gesamtleistung: %.2f MW%n";

    /** Format: Korrigierte Gesamtleistung */
    public static final String KORREKTUR_LEISTUNG = "ID %d | %s | Alt: %.2f MW -> Neu: %s%n";
}
