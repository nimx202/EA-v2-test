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
        public static final int UNGUELTIGE_ID = -1;

    /** Anzeigetext für unbekannte Werte */
    public static final String ANZEIGE_UNBEKANNT = "unbekannt";

    /** Trenner fuer Baujahr-Spannen wie 2023-2024 */
    public static final String BAUJAHR_SPANNEN_TRENNER = "-";

    // ==================== Main View Konstanten ====================

    /** Limit für Top-Windparks Anzeige */
    public static final int TOP_LIMIT = 5;

    /** Limit für Beispiel-Datensätze */
    public static final int BEISPIEL_LIMIT = 10;

    /**
     * Standard-Limit für die Ausgabe von Koordinaten-Korrekturen (kann zur Laufzeit angepasst werden)
     * Default ist gleich BEISPIEL_LIMIT. Bei Bedarf kann dieses Feld im Code gesetzt werden,
     * um mehr oder weniger Datensätze standardmäßig anzuzeigen.
     */
    /* Paket-sichtbares, zur Laufzeit anpassbares Default-Limit für die
       Ausgabe von Koordinaten-Korrekturen (standard: BEISPIEL_LIMIT) */
    static int korrekturAusgabeLimit = BEISPIEL_LIMIT;

    /** Konvertierung von Nanosekunden zu Millisekunden */
    public static final float NANOS_ZU_MILLIS = 1_000_000.0f;
    public static final float NANOS_ZU_MILLIS = 1_000_000.0f;

    /** Platzhalter für unbekannte Orte */
    public static final String UNBEKANNTER_ORT = "<unbekannt>";

    // ==================== Ausgabetexte ====================

    /** Fehlermeldung: CSV-Datei nicht gefunden */
    public static final String CSV_NICHT_GEFUNDEN = "CSV-Datei nicht gefunden: ";

    /** Erfolgsmeldung: Laden abgeschlossen */
    public static final String LADEN_ABGESCHLOSSEN = "Einlesen der CSV abgeschlossen.";

    /** Info: Dateipfad */
    public static final String DATEI_INFO = "Datei: ";

    /** Format: Anzahl Datensaetze */
        public static final String DATENSAETZE_ANZAHL = "Anzahl eingelesener Datensaetze: %d%n";

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

    /** Überschrift: Beispiel-Datensaetze */
        public static final String BEISPIEL_DATENSAETZE = "Einige Beispiel-Datensaetze:";

    /** Fehler-Präfix */
    public static final String FEHLER_PREFIX = "Fehler beim Einlesen: ";

    /** Trennzeichen für Ausgabe (Doppelpunkt und Leerzeichen) */
    public static final String AUSGABE_TRENNZEICHEN = ": ";

    // ==================== CsvParser Konstanten ====================

    /** Anführungszeichen-Zeichen */
        public static final char ANFUEHRUNGSZEICHEN = '\"';

    /** Komma-Zeichen */
    public static final char KOMMA = ',';

    /** Wagenrücklauf-Zeichen */
        public static final char WAGENRUECKLAUF = '\r';

    /** Zeilenumbruch-Zeichen */
    public static final char ZEILENUMBRUCH = '\n';

    /** Markierungsabstand für BufferedReader */
    public static final int MARKIERUNGSABSTAND = 1;

    /** Escaped Anführungszeichen (zwei Anführungszeichen) */
        public static final String ESCAPED_ANFUEHRUNGSZEICHEN = "\"\"";

        /** Einzelnes Anfuehrungszeichen als String */
        public static final String EINZELNES_ANFUEHRUNGSZEICHEN = "\"";

    // ==================== Koordinaten-Validierung ====================

    /** Minimaler gültiger Breitengrad für Deutschland */
    public static final float MIN_BREITENGRAD_DE = 47.0f;

    /** Maximaler gültiger Breitengrad für Deutschland */
    public static final float MAX_BREITENGRAD_DE = 55.0f;

    /** Minimaler gültiger Längengrad für Deutschland */
        public static final float MIN_LAENGENGRAD_DE = 5.0f;

        /** Maximaler gueltiger Laengengrad für Deutschland */
        public static final float MAX_LAENGENGRAD_DE = 16.0f;

    /** Fehlerschwelle für Breitengrad-Korrektur (Faktor 1000) */
    public static final float BREITENGRAD_FEHLERFAKTOR = 1000.0f;
    public static final float BREITENGRAD_FEHLERFAKTOR = 1000.0f;

    /** Fehlerschwelle für Längengrad-Korrektur (Faktor 1000) */
        public static final float LAENGENGRAD_FEHLERFAKTOR = 1000.0f;

    // ==================== Ausgabetexte Koordinaten-Korrektur ====================

    /** Überschrift: Koordinaten-Validierung */
    public static final String VALIDIERUNG_UEBERSCHRIFT = "\n=== Ueberpruefung und Korrektur der Koordinaten ===";

    /** Format: Validierungszeit */
    public static final String VALIDIERUNG_ZEIT = "Dauer der Ueberpruefung: %.3f ms%n";

    /** Format: Korrekturzeit */
    public static final String KORREKTUR_ZEIT = "Dauer der Korrektur: %.3f ms%n";

    /** Format: Anzahl fehlerhafte Koordinaten */
    public static final String FEHLERHAFTE_KOORDINATEN = "Anzahl Datensaetze mit fehlerhaften Koordinaten: %d%n";

    /** Format: Anzahl korrigierte Koordinaten */
    public static final String KORRIGIERTE_KOORDINATEN = "Anzahl korrigierte Datensätze: %d%n";

    /** Überschrift: Fehlerhafte Datensaetze */
    public static final String FEHLERHAFTE_DATENSAETZE = "\nFehlerhafte Datensaetze (vor Korrektur):";

    /** Format: Fehlerhafter Datensatz */
    public static final String FEHLER_DATENSATZ = "ID %d | %s | Breitengrad: %.4f | Laengengrad: %.4f%n";

    /** Überschrift: Korrigierte Datensaetze */
    public static final String KORRIGIERTE_DATENSAETZE = "\nKorrigierte Datensaetze (nach Korrektur):";

    /** Format: Korrigierter Datensatz */
    public static final String KORREKTUR_DATENSATZ = "ID %d | %s | Alt: (%.4f, %.4f) -> Neu: (%.4f, %.4f)%n";

    // ==================== AusgabeManager Konstanten ====================

    /** Trennstrich-Zeichen fuer Ueberschriften */
    public static final String TRENNSTRICH_ZEICHEN = "=";

    /** Minimale Laenge fuer Ueberschriften */
    public static final int UEBERSCHRIFT_MIN_LAENGE = 40;

    // ==================== ZeitStatistiken Konstanten ====================

    /** Überschrift: Zusammenfassung */
    public static final String ZUSAMMENFASSUNG_UEBERSCHRIFT = "\n=== Zusammenfassung: Zeiten und Statistiken ===";

    /** Überschrift: Zeiten */
    public static final String ZEITEN_UEBERSCHRIFT = "\n-- Zeiten (ms) --";

    /** Überschrift: Statistiken */
    public static final String STATISTIKEN_UEBERSCHRIFT = "\n-- Statistiken --";

    /** Text: Keine Zeit-Messungen */
    public static final String KEINE_ZEITMESSUNGEN = "(Keine Zeit-Messungen vorhanden)";

    /** Text: Keine Statistiken */
    public static final String KEINE_STATISTIKEN = "(Keine Statistiken vorhanden)";

    /** Format: Zeitanzeige */
    public static final String ZEIT_FORMAT = "%s: %.3f ms\n";

    /** Format: Gesamtzeit */
    public static final String GESAMTZEIT_FORMAT = "Gesamt gemessene Zeit (Summe): %.3f ms\n";

    /** Format: Statistik-Zeile */
    public static final String STATISTIK_FORMAT = "%s: %s\n";

    /** Text: Ende Zusammenfassung */
    public static final String ENDE_ZUSAMMENFASSUNG = "=== Ende Zusammenfassung ===\n";

    // ==================== Main-View Ausgabetexte ====================

    /** Format: Koordinaten korrigiert */
    public static final String KOORDINATEN_KORRIGIERT = "Koordinaten korrigiert: %d (%.3f ms)%n";

    /** Name: Laden CSV Operation */
    public static final String OPERATION_LADEN_CSV = "Laden CSV";

    /** Name: Koordinaten-Korrektur Operation */
    public static final String OPERATION_KOORDINATEN_KORREKTUR = "Koordinaten-Korrektur";

    /** Statistik: Geladene Datensaetze */
    public static final String STAT_GELADENE_DATENSAETZE = "Geladene Datensaetze";

    /** Name: gebeStatistikenAus Operation */
    public static final String OPERATION_STATISTIKEN = "gebeStatistikenAus";

    /** Statistik: Anlagen mit Koordinaten */
    public static final String STAT_MIT_KOORDINATEN = "Anlagen mit Koordinaten";

    /** Statistik: Anlagen ohne Betreiber */
    public static final String STAT_OHNE_BETREIBER = "Anlagen ohne Betreiber";

    /** Statistik: Anzahl korrigierte Koordinaten */
    public static final String STAT_KOORDINATEN_KORRIGIERT = "Koordinaten korrigiert";

    /** Name: gebeTopWindparksAus Operation */
    public static final String OPERATION_TOP_WINDPARKS = "gebeTopWindparksAus";

    /** Statistik: Anzahl Parks */
    public static final String STAT_ANZAHL_PARKS = "Anzahl Parks (einzigartig)";

    /** Statistik: Top N Parks Präfix */
    public static final String STAT_TOP_PARKS_PREFIX = "Top ";

    /** Statistik: Parks Suffix */
    public static final String STAT_PARKS_SUFFIX = " Parks";

    /** Name: gebeBeispielDatensaetzeAus Operation */
    public static final String OPERATION_BEISPIEL_DATENSAETZE = "gebeBeispielDatensaetzeAus";

    /** Statistik: Anzahl Beispiel-Datensaetze ausgegeben */
    public static final String STAT_BEISPIEL_AUSGEGEBEN = "Anzahl Beispiel-Datensaetze ausgegeben";

    /** Format: Anzahl Beispiel-Datensaetze ausgegeben bei Koordinaten-Korrekturen */
    public static final String KORREKTUR_BEISPIEL_AUSGABE = "Anzahl Beispiel-Datensaetze ausgegeben: %d%n";

    /** Trennzeichen zwischen Parkname und Anzahl */
    public static final String PARK_ANZAHL_TRENNER = ", ";

    /** Format für Park mit Anzahl */
    public static final String PARK_FORMAT = "%s(%s)";

    // ==================== Koordinaten-Korrektur Ausgaben ====================

    /** Überschrift: Koordinaten-Korrekturen */
    public static final String KORREKTUR_UEBERSCHRIFT = "\n=== Koordinaten-Korrekturen ===";

    /** Format: Anzahl Korrekturen */
    public static final String KORREKTUR_ANZAHL = "Anzahl korrigierter Datensaetze: %d%n";

    /** Format: ID und Name */
    public static final String KORREKTUR_ID_NAME = "ID: %d | %s%n";

    /** Format: Vorher-Werte */
    public static final String KORREKTUR_VORHER = "  Vorher: Breitengrad=%s, Laengengrad=%s%n";

    /** Format: Nachher-Werte */
    public static final String KORREKTUR_NACHHER = "  Nachher: Breitengrad=%s, Laengengrad=%s%n";

    /** Einzeilige Darstellung einer Korrektur (Vorher ----> Nachher) */
    public static final String KORREKTUR_EINZEILER_FORMAT = "ID: %d | %s: Vorher: Breitengrad=%s, Laengengrad=%s ----> Nachher: Breitengrad=%s, Laengengrad=%s%n";

    /** Format für Koordinaten */
    public static final String KOORDINATEN_FORMAT = "%.4f";

    // ==================== Windkraftanlage toString Konstanten ====================

    /** toString Präfix */
    public static final String TOSTRING_PREFIX = "Windkraftanlage{";

    /** toString Suffix */
    public static final String TOSTRING_SUFFIX = "}";

    /** toString Feldtrenner */
    public static final String TOSTRING_FELDTRENNER = ", ";

    /** toString Werttrenner */
    public static final String TOSTRING_WERTTRENNER = "=";

    /** toString String-Anführungszeichen */
    public static final String TOSTRING_QUOTE = "'";

    /** toString Feld: objektId */
    public static final String FELD_OBJEKT_ID = "objektId";

    /** toString Feld: name */
    public static final String FELD_NAME = "name";

    /** toString Feld: baujahr */
    public static final String FELD_BAUJAHR = "baujahr";

    /** toString Feld: gesamtLeistungMW */
    public static final String FELD_GESAMT_LEISTUNG_MW = "gesamtLeistungMW";

    /** toString Feld: anzahl */
    public static final String FELD_ANZAHL = "anzahl";

    /** toString Feld: typ */
    public static final String FELD_TYP = "typ";

    /** toString Feld: ort */
    public static final String FELD_ORT = "ort";

    /** toString Feld: landkreis */
    public static final String FELD_LANDKREIS = "landkreis";

    /** toString Feld: breitengrad */
    public static final String FELD_BREITENGRAD = "breitengrad";

    /** toString Feld: laengengrad */
    public static final String FELD_LAENGENGRAD = "laengengrad";

    /** toString Feld: betreiber */
    public static final String FELD_BETREIBER = "betreiber";

    /** toString Feld: bemerkungen */
    public static final String FELD_BEMERKUNGEN = "bemerkungen";

}
