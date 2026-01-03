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
}
