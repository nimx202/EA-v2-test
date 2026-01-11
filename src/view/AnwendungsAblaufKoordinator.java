package view;

import controler.WindkraftanlageRepository;
import java.nio.file.Path;
import java.util.List;
import model.Windkraftanlage;
import util.AusgabeManager;
import util.Konstanten;
import util.KoordinatenKorrekturTracker;

/**
 * Koordiniert den kompletten Ablauf der Anwendung und haelt die Main-Klasse schlank.
 *
 * Design-Prinzipien:
 * - Single Responsibility: Verantwortlich fuer Orchestrierung
 * - KISS: Nutzt einfache Methodenketten ohne komplexe Logik
 * - Modularisierung: Delegiert an CsvPfadPruefer, DatenImportManager und AnalyseAusgabeKoordinator
 *
 * Pre: Konstanten enthalten gueltige Pfade und Limits
 * Post: CSV geladen, Daten analysiert und Ausgaben erstellt
 */
public class AnwendungsAblaufKoordinator {

    private final CsvPfadPruefer csvPfadPruefer;
    private final DatenImportManager datenImportManager;
    private final AnalyseAusgabeKoordinator analyseAusgabeKoordinator;

    /**
    * Erstellt einen neuen Koordinator mit den benoetigten Abhaengigkeiten.
     *
     * Pre: Keine, Konstruktor legt Abhaengigkeiten an
     * Post: Repository und Tracker wurden initialisiert
     */
    public AnwendungsAblaufKoordinator() {
        WindkraftanlageRepository datenSpeicher = new WindkraftanlageRepository();
        KoordinatenKorrekturTracker tracker = new KoordinatenKorrekturTracker();
        this.csvPfadPruefer = new CsvPfadPruefer();
        this.datenImportManager = new DatenImportManager(datenSpeicher, tracker);
        this.analyseAusgabeKoordinator = new AnalyseAusgabeKoordinator();
    }

    /**
     * Startet den kompletten Anwendungsablauf vom Laden bis zur Ausgabe.
     *
     * Pre: CSV-Datei muss existieren
     * Post: Statistiken wurden ausgegeben oder Fehler gemeldet
     */
    public void run() {
        try {
            AusgabeManager.aktivierePufferung();
            
            Path csvPfad = csvPfadPruefer.ermittleCsvPfad();
            if (!csvPfadPruefer.pruefeCsvDatei(csvPfad)) {
                AusgabeManager.gebeGepufferteAusgabenAus();
                return;
            }
            List<Windkraftanlage> alleAnlagen = datenImportManager.ladeDaten(csvPfad);
            analyseAusgabeKoordinator.analysiereUndGebeAus(alleAnlagen);
        } catch (Exception fehler) {
            AusgabeManager.gebeFehlerAus(Konstanten.FEHLER_PREFIX + fehler.getMessage());
            fehler.printStackTrace();
        }
    }
}
