package view;

<<<<<<< HEAD
// Datei kodiert in UTF-8

/**
 * Hauptklasse der Anwendung.
 * Delegiert den Ablauf an {@link AnwendungsAblaufKoordinator}.
 *
 * Design-Prinzipien:
 * - KISS: Nur Einstiegspunkt ohne Logik
 * - Modularisierung: Auslagerung der Orchestrierung
 * - Single Responsibility: Startet lediglich den Runner
 *
 * Pre: Keine speziellen Bedingungen
 * Post: Anwendung wurde gestartet oder Fehler angezeigt
=======
import controler.WindkraftanlageRepository;
import model.Windkraftanlage;
import model.WindparkEintrag;
import util.AusgabeManager;
import util.Konstanten;
import util.KoordinatenKorrekturTracker;
import util.StatistikBerechner;
import util.WindparkAnalysierer;
import util.ZeitMessung;
import util.ZeitStatistiken;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Hauptklasse der Anwendung.
 * Laedt CSV-Datei und zeigt Statistiken an.
 * 
 * Design-Prinzipien:
 * - KISS: Nur Orchestrierung, keine komplexe Logik
 * - Single Responsibility: Nur Start und Koordinierung
 * - Delegation: Alle Berechnungen in Utility-Klassen
 * 
 * Pre: CSV-Datei existiert im erwarteten Pfad
 * Post: Statistiken wurden ausgegeben
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
 */
public final class Main {

    private Main() {
        // Keine Instanzen nötig
    }

    /**
<<<<<<< HEAD
     * Einstiegspunkt der Anwendung.
     *
     * Pre: Keine
    * Post: AnwendungsAblaufKoordinator wurde ausgefuehrt
     *
     * @param args Kommandozeilenargumente (nicht verwendet)
     */
    public static void main(String[] args) {
        AnwendungsAblaufKoordinator koordinator = new AnwendungsAblaufKoordinator();
        koordinator.run();
=======
     * Hauptmethode - Einstiegspunkt der Anwendung.
     * Orchestriert das Laden und Analysieren der Windkraftanlagen-Daten.
     * 
     * Pre: Kommandozeilenargumente werden nicht verwendet
     * Post: Alle Daten geladen, analysiert und ausgegeben
     * 
     * @param args Kommandozeilenargumente (nicht verwendet)
     */
    public static void main(String[] args) {
        try {
            // Pruefe ob CSV-Datei existiert
            Path csvPfad = Paths.get(Konstanten.RESSOURCENPFAD);
            if (!Files.exists(csvPfad)) {
                AusgabeManager.gebeFehlerAus(Konstanten.CSV_NICHT_GEFUNDEN + csvPfad.toAbsolutePath());
                return;
            }

            // Lade Daten
            WindkraftanlageRepository datenSpeicher = new WindkraftanlageRepository();
            ladeCsvDatei(datenSpeicher, csvPfad);
            
            // Korrigiere Koordinaten mit Tracking
            KoordinatenKorrekturTracker korrekturTracker = new KoordinatenKorrekturTracker();
            korrigiereKoordinaten(datenSpeicher, korrekturTracker);
            
            // Hole alle Anlagen
            List<Windkraftanlage> alleAnlagen = datenSpeicher.getAll();
            
            // Zeige Statistiken
            zeigeStatistiken(alleAnlagen);
            zeigeTopWindparks(alleAnlagen);
            zeigeBeispielAnlagen(alleAnlagen);

            // Zeige Zusammenfassung
            ZeitStatistiken.druckeZusammenfassung();

        } catch (Exception fehler) {
            AusgabeManager.gebeFehlerAus(Konstanten.FEHLER_PREFIX + fehler.getMessage());
            fehler.printStackTrace();
        }
    }

    /**
     * Lädt CSV-Datei in das Repository und misst die Zeit.
     * Delegiert das eigentliche Laden an das Repository.
     * 
     * Pre: datenSpeicher nicht null; csvPfad existiert
     * Post: Daten geladen; Zeit gemessen; Statistik aufgezeichnet
     * 
     * @param datenSpeicher Das Repository zum Speichern der Daten
     * @param csvPfad Pfad zur CSV-Datei
     * @return Anzahl geladener Datensätze
     * @throws Exception bei Lesefehlern
     */
    private static int ladeCsvDatei(WindkraftanlageRepository datenSpeicher, Path csvPfad) throws Exception {
        ZeitMessung timer = ZeitMessung.starte();
        int anzahlGeladen = datenSpeicher.ladeAusCsv(csvPfad.toString());
        double zeitInMillis = timer.stoppeUndGibMillis();

        AusgabeManager.gebeAus(Konstanten.LADEN_ABGESCHLOSSEN);
        AusgabeManager.gebeAus(Konstanten.DATEI_INFO + csvPfad.toAbsolutePath());
        AusgabeManager.gebeAusFormat(Konstanten.DATENSAETZE_ANZAHL, anzahlGeladen);
        AusgabeManager.gebeAusFormat(Konstanten.VERSTRICHENE_ZEIT, zeitInMillis);

        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_LADEN_CSV, zeitInMillis);
        ZeitStatistiken.zeichneStat(Konstanten.STAT_GELADENE_DATENSAETZE, String.valueOf(anzahlGeladen));

        return anzahlGeladen;
    }

    /** und zeigt Details an.
     * 
     * Pre: datenSpeicher nicht null; tracker nicht null
     * Post: Koordinaten korrigiert; Korrekturen ausgegeben; Zeit gemessen
     * 
     * @param datenSpeicher Das Repository mit den zu korrigierenden Daten
     * @param tracker Tracker zum Aufzeichnen der Korrekturen
     */
    private static void korrigiereKoordinaten(WindkraftanlageRepository datenSpeicher, 
                                               KoordinatenKorrekturTracker tracker) {
        ZeitMessung timer = ZeitMessung.starte();
        int anzahlKorrigiert = datenSpeicher.korrigiereKoordinaten(tracker);
        double zeitInMillis = timer.stoppeUndGibMillis();
        
        if (anzahlKorrigiert > 0) {
            AusgabeManager.gebeAusFormat(Konstanten.KOORDINATEN_KORRIGIERT, 
                anzahlKorrigiert, zeitInMillis);
            
            // Zeige Details aller Korrekturen
            tracker.gebeKorrekturenAus();
        }
        
        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_KOORDINATEN_KORREKTUR, zeitInMillis);
        ZeitStatistiken.zeichneStat(Konstanten.STAT_KOORDINATEN_KORRIGIERT, String.valueOf(anzahlKorrigiert));
    }

    /**
    * Zeigt Basis-Statistiken ueber alle Anlagen.
     * Delegiert Berechnungen an StatistikBerechner.
     * 
     * Pre: alleAnlagen nicht null
     * Post: Statistiken ausgegeben; Zeit gemessen
     * 
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    private static void zeigeStatistiken(List<Windkraftanlage> alleAnlagen) {
        ZeitMessung timer = ZeitMessung.starte();
        
        // Delegiere Berechnungen an StatistikBerechner
        int anlagenMitKoordinaten = StatistikBerechner.zaehleAnlagenMitKoordinaten(alleAnlagen);
        int anlagenOhneBetreiber = StatistikBerechner.zaehleAnlagenOhneBetreiber(alleAnlagen);

        // Ausgabe
        AusgabeManager.gebeAusFormat(Konstanten.MIT_KOORDINATEN, anlagenMitKoordinaten);
        AusgabeManager.gebeAusFormat(Konstanten.OHNE_BETREIBER, anlagenOhneBetreiber);

        // Zeit-Statistik
        double zeitInMillis = timer.stoppeUndGibMillis();
        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_STATISTIKEN, zeitInMillis);
        ZeitStatistiken.zeichneStat(Konstanten.STAT_MIT_KOORDINATEN, String.valueOf(anlagenMitKoordinaten));
        ZeitStatistiken.zeichneStat(Konstanten.STAT_OHNE_BETREIBER, String.valueOf(anlagenOhneBetreiber));
    }

    /**
     * Zeigt Top-Windparks nach Anzahl.
     * Delegiert Analyse an WindparkAnalysierer.
     * 
     * Pre: alleAnlagen nicht null
     * Post: Top-Windparks ausgegeben; Zeit gemessen
     * 
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    private static void zeigeTopWindparks(List<Windkraftanlage> alleAnlagen) {
        ZeitMessung timer = ZeitMessung.starte();
        
        // Delegiere Analyse an WindparkAnalysierer
        List<WindparkEintrag> topWindparks = WindparkAnalysierer.holeTopWindparks(
            alleAnlagen, Konstanten.TOP_LIMIT);
        
        // Ausgabe
        AusgabeManager.gebeAusFormat(Konstanten.TOP_PARKS, Konstanten.TOP_LIMIT);
        
        for (WindparkEintrag eintrag : topWindparks) {
            AusgabeManager.gebeAusMitTrennzeichen(eintrag.getName(), eintrag.getAnzahl());
        }
        
        // Berechne Gesamtanzahl unterschiedlicher Windparks
        int gesamtAnzahl = WindparkAnalysierer.zaehleWindparks(alleAnlagen).size();
        AusgabeManager.gebeAusFormat(Konstanten.GESAMT_PARKS, gesamtAnzahl);

        // Zeit-Statistik
        double zeitInMillis = timer.stoppeUndGibMillis();
        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_TOP_WINDPARKS, zeitInMillis);
        ZeitStatistiken.zeichneStat(Konstanten.STAT_ANZAHL_PARKS, String.valueOf(gesamtAnzahl));
    }

    /**
    * Zeigt Beispiel-Datensaetze.
     * Gibt die ersten N Anlagen aus.
     * 
     * Pre: alleAnlagen nicht null
     * Post: Beispiele ausgegeben; Zeit gemessen
     * 
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    private static void zeigeBeispielAnlagen(List<Windkraftanlage> alleAnlagen) {
        ZeitMessung timer = ZeitMessung.starte();
        
        AusgabeManager.gebeAus(Konstanten.BEISPIEL_DATENSAETZE);
        
        // Zeige maximal BEISPIEL_LIMIT Anlagen
        int anzahlZeigen = Math.min(Konstanten.BEISPIEL_LIMIT, alleAnlagen.size());
        for (int i = 0; i < anzahlZeigen; i++) {
            AusgabeManager.gebeAus(alleAnlagen.get(i).toString());
        }

        // Zeit-Statistik
        double zeitInMillis = timer.stoppeUndGibMillis();
        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_BEISPIEL_DATENSAETZE, zeitInMillis);
        ZeitStatistiken.zeichneStat(Konstanten.STAT_BEISPIEL_AUSGEGEBEN, String.valueOf(anzahlZeigen));
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
    }
}
