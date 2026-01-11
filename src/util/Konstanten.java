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

    /** Limit fuer Sortier-Ausgabe (Anzahl Datensaetze pro Sortierung) */
    public static final int SORTIER_AUSGABE_LIMIT = BEISPIEL_LIMIT;

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
    
    /** Überschrift: Datensaetze ohne Koordinaten */
    public static final String DATENSAETZE_OHNE_KOORDINATEN_UEBERSCHRIFT = "\nDatensaetze ohne Koordinaten";

    /** Format: Anzahl Datensaetze ohne Koordinaten */
    public static final String DATENSAETZE_OHNE_KOORDINATEN_FORMAT = "Anzahl Datensaetze ohne Koordinaten: %d%n";

    /** Überschrift: Sortierungen */
    public static final String SORTIERUNGEN_UEBERSCHRIFT = "Sortierte Windkraftanlagen";

    /** Überschrift: Sortierung nach Ort/Name/ID */
    public static final String SORTIERUNG_ORT_NAME_ID_UEBERSCHRIFT = "Sortierung: Ort > Name > ID";

    /** Überschrift: Sortierung nach Leistung/Baujahr/Name */
    public static final String SORTIERUNG_LEISTUNG_BAUJAHR_NAME_UEBERSCHRIFT = "Sortierung: Leistung (desc) > Baujahr > Name";

    /** Überschrift: Sortierung nach Landkreis/Ort/Name */
    public static final String SORTIERUNG_LANDKREIS_ORT_NAME_UEBERSCHRIFT = "Sortierung: Landkreis > Ort > Name";

    /** Format: Sortierzeit */
    public static final String SORTIERUNG_ZEIT_FORMAT = "Sortierzeit: %.3f ms%n";

    /** Format: Hinweis wie viele Eintraege ausgegeben werden */
    public static final String SORTIERUNG_ZEIGE_ERSTE_FORMAT = "Zeige erste %d Eintraege:%n";

    /** Allgemeiner Ausgabetext: Anzahl */
    public static final String AUSGABE_ANZAHL = "Anzahl";

    /** Allgemeiner Ausgabetext: Dauer in Millisekunden */
    public static final String AUSGABE_DAUER_MS = "Dauer (ms)";

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

    /** 
     * Aktiviert oder deaktiviert die Pufferung von Ausgaben.
     * True: Alle Ausgaben werden gepuffert und gemeinsam am Ende ausgegeben (Standard).
     * False: Ausgaben werden sofort ausgegeben (passthrough-Modus, nützlich für Debugging).
     */
    public static final boolean PUFFERUNG_AKTIVIERT = false;

    /** Leerer String */
    public static final String LEERSTRING = "";

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

    /** Name: Sortierung Ort/Name/ID Operation */
    public static final String OPERATION_SORT_ORT_NAME_ID = "Sortierung Ort/Name/ID";

    /** Name: Sortierung Leistung/Baujahr/Name Operation */
    public static final String OPERATION_SORT_LEISTUNG_BAUJAHR_NAME = "Sortierung Leistung/Baujahr/Name";

    /** Name: Sortierung Landkreis/Ort/Name Operation */
    public static final String OPERATION_SORT_LANDKREIS_ORT_NAME = "Sortierung Landkreis/Ort/Name";

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

    // ==================== Erweiterte Statistiken Ausgaben ====================

    /** Überschrift: Erweiterte Statistiken */
    public static final String ERWEITERTE_STATISTIKEN_UEBERSCHRIFT = "\n=== Erweiterte Statistiken ===";

    /** Format: Südlichste Anlage */
    public static final String SUEDLICHSTE_ANLAGE_FORMAT = "Suedlichste Anlage (kleinster Breitengrad):%n";

    /** Format: Anlagedetails (ID, Name, Ort) */
    public static final String ANLAGE_DETAILS_FORMAT = "  ID: %d | Name: %s | Ort: %s%n";

    /** Format: Breitengrad */
    public static final String BREITENGRAD_FORMAT = "  Breitengrad: %.4f%n";

    /** Format: Längengrad */
    public static final String LAENGENGRAD_FORMAT = "  Laengengrad: %.4f%n";

    /** Format: Anlage mit höchster Leistung */
    public static final String HOECHSTE_LEISTUNG_ANLAGE_FORMAT = "Anlage mit hoechster Gesamtleistung:%n";

    /** Format: Gesamtleistung */
    public static final String GESAMTLEISTUNG_FORMAT = "  Gesamtleistung: %.2f MW%n";

    /** Format: Anlage mit meisten Windrädern */
    public static final String MEISTE_WINDRAEDER_ANLAGE_FORMAT = "Anlage mit den meisten Windraedern:%n";

    /** Format: Anzahl Windräder */
    public static final String ANZAHL_WINDRAEDER_FORMAT = "  Anzahl Windraeder: %d%n";

    /** Format: Gesamtleistung aller Anlagen */
    public static final String GESAMTLEISTUNG_ALLER_FORMAT = "Gesamtleistung aller Windkraftanlagen: %.2f MW%n";

    /** Text: Keine Anlage gefunden */
    public static final String KEINE_ANLAGE_GEFUNDEN = "  (Keine Anlage gefunden)%n";

    /** Name: Erweiterte Statistiken Operation */
    public static final String OPERATION_ERWEITERTE_STATISTIKEN = "Erweiterte Statistiken";

    /** Statistik: Südlichste Anlage */
    public static final String STAT_SUEDLICHSTE_ANLAGE = "Suedlichste Anlage";

    /** Statistik: Anlage mit höchster Leistung */
    public static final String STAT_HOECHSTE_LEISTUNG = "Anlage mit hoechster Leistung";

    /** Statistik: Anlage mit meisten Windrädern */
    public static final String STAT_MEISTE_WINDRAEDER = "Anlage mit meisten Windraedern";

    /** Statistik: Gesamtleistung aller Anlagen */
    public static final String STAT_GESAMTLEISTUNG_ALLER = "Gesamtleistung aller Anlagen";

    // ==================== Format Strings für String.format() ====================

    /** Format: Dauer in Millisekunden mit 3 Nachkommastellen */
    public static final String FORMAT_DAUER_MS = "%.3f";

    /** Format: Leistung in MW mit 2 Nachkommastellen */
    public static final String FORMAT_LEISTUNG_MW = "%.2f";

    /** Format: Koordinate mit 4 Nachkommastellen */
    public static final String FORMAT_KOORDINATE = "%.4f";

    /** Format: Südlichste Anlage (für Statistik-Tracker) */
    public static final String FORMAT_SUEDLICHSTE_ANLAGE_STAT = "ID %d, %s (%s), Breitengrad " + FORMAT_KOORDINATE;

    /** Format: Anlage mit höchster Leistung (für Statistik-Tracker) */
    public static final String FORMAT_HOECHSTE_LEISTUNG_STAT = "ID %d, %s (%s), " + FORMAT_LEISTUNG_MW + " MW";

    /** Format: Anlage mit meisten Windrädern (für Statistik-Tracker) */
    public static final String FORMAT_MEISTE_WINDRAEDER_STAT = "ID %d, %s (%s), %d Windraeder";

    /** Format: Gesamtleistung (für Statistik-Tracker) */
    public static final String FORMAT_GESAMTLEISTUNG_STAT = FORMAT_LEISTUNG_MW + " MW";

    // ==================== Graph und Distanzberechnung ====================

    /** Erdradius in Kilometern (für Haversine-Formel) */
    public static final float ERDRADIUS_KM = 6371.0f;

    /** Maximale Distanz für Kantenbildung im Graphen (in Kilometern) */
    public static final float GRAPH_MAX_DISTANZ_KM = 20.0f;

    /** Anzahl der Beispiel-Nachbarschaften die ausgegeben werden */
    public static final int GRAPH_BEISPIEL_ANZAHL = 5;

    // ==================== Graph-Ausgabe ====================

    /** Überschrift: Graph-Zusammenfassung */
    public static final String GRAPH_UEBERSCHRIFT = "%n=== Windkraftanlagen-Graph ===%n";

    /** Format: Maximale Distanz für Kanten */
    public static final String GRAPH_MAX_DISTANZ = "Maximale Distanz fuer Kanten: %.1f km%n";

    /** Format: Anzahl Knoten */
    public static final String GRAPH_ANZAHL_KNOTEN = "Anzahl Knoten: %d%n";

    /** Format: Anzahl Kanten */
    public static final String GRAPH_ANZAHL_KANTEN = "Anzahl Kanten: %d%n";

    /** Format: Durchschnittlicher Grad */
    public static final String GRAPH_DURCHSCHNITT_GRAD = "Durchschnittlicher Grad: %.2f%n";

    /** Überschrift: Beispiel-Nachbarschaften */
    public static final String GRAPH_BEISPIELE_UEBERSCHRIFT = "%nBeispiele von Nachbarschaften:%n";

    /** Format: Knoten-Information */
    public static final String GRAPH_KNOTEN_INFO = "  %d. %s (%s) - %d Nachbarn%n";

    // ==================== Leistungsschätzung ====================

    /** Überschrift: Schätzungs-Statistiken */
    public static final String SCHAETZUNG_UEBERSCHRIFT = "%n=== Leistungsschaetzung ===%n";

    /** Format: Anzahl ergänzter Werte */
    public static final String SCHAETZUNG_ERGAENZT = "Anzahl ergaenzter Gesamtleistungen: %d%n";

    /** Format: Prozent ergänzter Werte */
    public static final String SCHAETZUNG_PROZENT = "Prozentsatz ergaenzt: %.2f%%%n";

    /** Name: Graph-Aufbau Operation (für Zeitmessung) */
    public static final String OPERATION_GRAPH_AUFBAU = "Graph-Aufbau";

    /** Name: Leistungsschätzung Operation (für Zeitmessung) */
    public static final String OPERATION_LEISTUNGSSCHAETZUNG = "Leistungsschaetzung";

    // ==================== Wartungsplanung ====================

    /** Wartungsdauer pro Anlage in Stunden */
    public static final float WARTUNG_DAUER_STUNDEN = 2.0f;

    /** Verfuegbare Arbeitsstunden pro Tag (8:00 bis 16:00) */
    public static final float ARBEITSSTUNDEN_PRO_TAG = 8.0f;

    /** Maximale Anlagen pro Arbeitstag (8h / 2h = 4) */
    public static final int ANLAGEN_PRO_TAG = (int) (ARBEITSSTUNDEN_PRO_TAG / WARTUNG_DAUER_STUNDEN);

    /** Transportgeschwindigkeit in km/h (Durchschnitt) */
    public static final float TRANSPORT_GESCHWINDIGKEIT_KMH = 60.0f;

    /** Verfuegbare Transportstunden pro Tag (16:00 bis 18:00) */
    public static final float TRANSPORTSTUNDEN_PRO_TAG = 2.0f;

    /** Maximale Transportdistanz pro Tag in km (60 km/h * 2h = 120 km) */
    public static final float MAX_TRANSPORT_DISTANZ_KM = TRANSPORT_GESCHWINDIGKEIT_KMH * TRANSPORTSTUNDEN_PRO_TAG;

    /** Maximale Distanz fuer Wartungsgraph-Kanten in km (= Transportlimit) */
    public static final float WARTUNG_GRAPH_MAX_DISTANZ_KM = MAX_TRANSPORT_DISTANZ_KM;

    /** Anzahl der Top-Hersteller die analysiert werden (nutzt BEISPIEL_LIMIT) */
    public static final int WARTUNG_TOP_HERSTELLER_ANZAHL = BEISPIEL_LIMIT;

    /** Platzhalter fuer unbekannte Hersteller */
    public static final String UNBEKANNTER_HERSTELLER = "<Unbekannt>";

    /** Leerzeichen als Trennzeichen fuer Hersteller-Extraktion */
    public static final String LEERZEICHEN = " ";

    // ==================== Wartungsplanung Ausgabetexte ====================

    /** Ueberschrift: Wartungsplanung */
    public static final String WARTUNG_UEBERSCHRIFT = "\n=== Wartungsplanung fuer Hersteller-Wegenetze ===";

    /** Format: Hersteller-Ueberschrift */
    public static final String WARTUNG_HERSTELLER_UEBERSCHRIFT = "\n--- Hersteller: %s (%d Anlagen) ---%n";

    /** Format: Cluster-Information */
    public static final String WARTUNG_CLUSTER_INFO = "Erkannte Cluster (zusammenhaengende Bereiche): %d%n";

    /** Format: Cluster-Warnung */
    public static final String WARTUNG_CLUSTER_WARNUNG = "  WARNUNG: Cluster %d ist isoliert (%d Anlagen, Distanz > %.0f km zum naechsten Cluster)%n";

    /** Format: Cluster-Details */
    public static final String WARTUNG_CLUSTER_DETAILS = "  Cluster %d: %d Anlagen%n";

    /** Format: Route-Startanlage */
    public static final String WARTUNG_START_ANLAGE = "Startanlage: ID %d - %s (%s)%n";

    /** Format: Routenlaenge */
    public static final String WARTUNG_ROUTEN_LAENGE = "Optimierte Routenlaenge: %.2f km%n";

    /** Format: Distanz-Warnung bei Ueberschreitung */
    public static final String WARTUNG_DISTANZ_WARNUNG = "  WARNUNG: Strecke von ID %d nach ID %d = %.1f km (> %.0f km Transportlimit)%n";

    /** Format: Tage pro Cluster */
    public static final String WARTUNG_TAGE_CLUSTER = "Wartungsdauer Cluster %d: %d Tage%n";

    /** Format: Gesamttage */
    public static final String WARTUNG_TAGE_GESAMT = "GESAMTDAUER fuer alle Anlagen dieses Herstellers: %d Tage%n";

    /** Format: Arbeitsplan-Tag */
    public static final String WARTUNG_TAG_FORMAT = "  Tag %d: Anlagen %s%n";

    /** Name: Wartungsplanung Operation (fuer Zeitmessung) */
    public static final String OPERATION_WARTUNGSPLANUNG = "Wartungsplanung";

    /** Name: Hersteller-Gruppierung Operation (fuer Zeitmessung) */
    public static final String OPERATION_HERSTELLER_GRUPPIERUNG = "Hersteller-Gruppierung";

    /** Name: Routen-Optimierung Operation (fuer Zeitmessung) */
    public static final String OPERATION_ROUTEN_OPTIMIERUNG = "Routen-Optimierung (2-Opt)";

    /** Statistik: Anzahl Hersteller */
    public static final String STAT_ANZAHL_HERSTELLER = "Anzahl verschiedene Hersteller";

    /** Statistik: Gesamte Wartungstage */
    public static final String STAT_WARTUNGSTAGE_GESAMT = "Gesamte Wartungstage (Top %d)";

    /** Format: Berechnungszeit */
    public static final String WARTUNG_BERECHNUNGSZEIT = "Berechnungszeit: %.3f ms%n";

    /** Trennzeichen fuer Anlagen-IDs */
    public static final String ANLAGEN_ID_TRENNER = ", ";

    /** Prefix fuer Anlagen-ID in Ausgabe */
    public static final String ANLAGEN_ID_PREFIX = "ID ";

}

