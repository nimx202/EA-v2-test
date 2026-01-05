package view;

import controler.WindkraftanlageRepository;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import model.Windkraftanlage;
import model.WindparkEintrag;
import util.AusgabeManager;
import util.Konstanten;
import util.KoordinatenKorrekturTracker;
import util.StatistikBerechner;
import util.WindparkAnalysierer;
import util.ZeitMessung;
import util.ZeitStatistiken;

/**
 * Koordiniert den kompletten Ablauf der Anwendung und haelt die Main-Klasse schlank.
 *
 * Design-Prinzipien:
 * - Single Responsibility: Verantwortlich fuer Orchestrierung
 * - KISS: Nutzt einfache Methodenketten ohne komplexe Logik
 * - Modularisierung: Trennt Ablaufsteuerung von Einstiegspunkt
 *
 * Pre: Konstanten enthalten gueltige Pfade und Limits
 * Post: CSV geladen, Daten analysiert und Ausgaben erstellt
 */
public class AnwendungsAblaufKoordinator {

    private final WindkraftanlageRepository datenSpeicher;
    private final KoordinatenKorrekturTracker korrekturTracker;

    /**
    * Erstellt einen neuen Koordinator mit den benoetigten Abhaengigkeiten.
     *
     * Pre: Keine, Konstruktor legt Abhaengigkeiten an
     * Post: Repository und Tracker wurden initialisiert
     */
    public AnwendungsAblaufKoordinator() {
        this.datenSpeicher = new WindkraftanlageRepository();
        this.korrekturTracker = new KoordinatenKorrekturTracker();
    }

    /**
     * Startet den kompletten Anwendungsablauf vom Laden bis zur Ausgabe.
     *
     * Pre: CSV-Datei muss existieren
     * Post: Statistiken wurden ausgegeben oder Fehler gemeldet
     */
    public void run() {
        try {
            Path csvPfad = ermittleCsvPfad();
            if (!pruefeCsvDatei(csvPfad)) {
                return;
            }

            ladeCsvDatei(csvPfad);
            korrigiereKoordinaten();
            List<Windkraftanlage> alleAnlagen = datenSpeicher.getAll();
            zeigeStatistiken(alleAnlagen);
            zeigeTopWindparks(alleAnlagen);
            zeigeBeispielAnlagen(alleAnlagen);
            ZeitStatistiken.druckeZusammenfassung();
        } catch (Exception fehler) {
            AusgabeManager.gebeFehlerAus(Konstanten.FEHLER_PREFIX + fehler.getMessage());
            fehler.printStackTrace();
        }
    }

    private Path ermittleCsvPfad() {
        return Paths.get(Konstanten.RESSOURCENPFAD);
    }

    private boolean pruefeCsvDatei(Path csvPfad) {
        if (!Files.exists(csvPfad)) {
            AusgabeManager.gebeFehlerAus(Konstanten.CSV_NICHT_GEFUNDEN + csvPfad.toAbsolutePath());
            return false;
        }
        return true;
    }

    private void ladeCsvDatei(Path csvPfad) throws Exception {
        ZeitMessung timer = ZeitMessung.starte();
        int anzahlGeladen = datenSpeicher.ladeAusCsv(csvPfad.toString());
        float zeitInMillis = timer.stoppeUndGibMillis();

        AusgabeManager.gebeAus(Konstanten.LADEN_ABGESCHLOSSEN);
        AusgabeManager.gebeAus(Konstanten.DATEI_INFO + csvPfad.toAbsolutePath());
        AusgabeManager.gebeAusFormat(Konstanten.DATENSAETZE_ANZAHL, anzahlGeladen);
        AusgabeManager.gebeAusFormat(Konstanten.VERSTRICHENE_ZEIT, zeitInMillis);

        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_LADEN_CSV, zeitInMillis);
        ZeitStatistiken.zeichneStat(Konstanten.STAT_GELADENE_DATENSAETZE, String.valueOf(anzahlGeladen));
    }

    private void korrigiereKoordinaten() {
        ZeitMessung timer = ZeitMessung.starte();
        int anzahlKorrigiert = datenSpeicher.korrigiereKoordinaten(korrekturTracker);
        float zeitInMillis = timer.stoppeUndGibMillis();

        if (anzahlKorrigiert > 0) {
            AusgabeManager.gebeAusFormat(Konstanten.KOORDINATEN_KORRIGIERT,
                anzahlKorrigiert, zeitInMillis);
            korrekturTracker.gebeKorrekturenAus();
        }

        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_KOORDINATEN_KORREKTUR, zeitInMillis);
        ZeitStatistiken.zeichneStat(Konstanten.STAT_KOORDINATEN_KORRIGIERT, String.valueOf(anzahlKorrigiert));
    }

    private void zeigeStatistiken(List<Windkraftanlage> alleAnlagen) {
        ZeitMessung timer = ZeitMessung.starte();
        int anlagenMitKoordinaten = StatistikBerechner.zaehleAnlagenMitKoordinaten(alleAnlagen);
        int anlagenOhneBetreiber = StatistikBerechner.zaehleAnlagenOhneBetreiber(alleAnlagen);

        AusgabeManager.gebeAusFormat(Konstanten.MIT_KOORDINATEN, anlagenMitKoordinaten);
        AusgabeManager.gebeAusFormat(Konstanten.OHNE_BETREIBER, anlagenOhneBetreiber);

        float zeitInMillis = timer.stoppeUndGibMillis();
        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_STATISTIKEN, zeitInMillis);
        ZeitStatistiken.zeichneStat(Konstanten.STAT_MIT_KOORDINATEN, String.valueOf(anlagenMitKoordinaten));
        ZeitStatistiken.zeichneStat(Konstanten.STAT_OHNE_BETREIBER, String.valueOf(anlagenOhneBetreiber));
    }

    private void zeigeTopWindparks(List<Windkraftanlage> alleAnlagen) {
        ZeitMessung timer = ZeitMessung.starte();
        List<WindparkEintrag> topWindparks = WindparkAnalysierer.holeTopWindparks(
            alleAnlagen, Konstanten.TOP_LIMIT);
        AusgabeManager.gebeAusFormat(Konstanten.TOP_PARKS, Konstanten.TOP_LIMIT);

        for (WindparkEintrag eintrag : topWindparks) {
            AusgabeManager.gebeAusMitTrennzeichen(eintrag.getName(), eintrag.getAnzahl());
        }

        int gesamtAnzahl = WindparkAnalysierer.zaehleWindparks(alleAnlagen).size();
        AusgabeManager.gebeAusFormat(Konstanten.GESAMT_PARKS, gesamtAnzahl);

        float zeitInMillis = timer.stoppeUndGibMillis();
        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_TOP_WINDPARKS, zeitInMillis);
        ZeitStatistiken.zeichneStat(Konstanten.STAT_ANZAHL_PARKS, String.valueOf(gesamtAnzahl));
    }

    private void zeigeBeispielAnlagen(List<Windkraftanlage> alleAnlagen) {
        ZeitMessung timer = ZeitMessung.starte();
        AusgabeManager.gebeAus(Konstanten.BEISPIEL_DATENSAETZE);

        int anzahlZeigen = Math.min(Konstanten.BEISPIEL_LIMIT, alleAnlagen.size());
        for (int i = 0; i < anzahlZeigen; i++) {
            AusgabeManager.gebeAus(alleAnlagen.get(i).toString());
        }

        float zeitInMillis = timer.stoppeUndGibMillis();
        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_BEISPIEL_DATENSAETZE, zeitInMillis);
        ZeitStatistiken.zeichneStat(Konstanten.STAT_BEISPIEL_AUSGEGEBEN, String.valueOf(anzahlZeigen));
    }
}
