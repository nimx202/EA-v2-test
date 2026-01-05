package view;

import controler.WindkraftanlageRepository;
import java.nio.file.Path;
import java.util.List;
import model.Windkraftanlage;
import util.AusgabeManager;
import util.Konstanten;
import util.KoordinatenKorrekturTracker;
import util.ZeitMessung;
import util.ZeitStatistiken;

/**
 * Kapselt den kompletten Datenimport inkl. Koordinatenkorrektur.
 *
 * Design-Prinzipien:
 * - Single Responsibility: Verantwortlich nur fuer Import und Korrektur
 * - KISS: lineare Abfolge ohne verzweigte Logik
 * - Modularisierung: Trennt Importdetails von der Ablaufsteuerung
 *
 * Pre: Repository und Tracker muessen initialisiert sein
 * Post: CSV eingelesen, Koordinaten korrigiert, Daten als Liste verfuegbar
 */
public class DatenImportManager {

    private final WindkraftanlageRepository datenSpeicher;
    private final KoordinatenKorrekturTracker korrekturTracker;

    /**
     * Erstellt den Manager mit benoetigten Abhaengigkeiten.
     *
     * Pre: datenSpeicher und korrekturTracker duerfen nicht null sein
     * Post: Felder gesetzt
     *
     * @param datenSpeicher Repository fuer Windkraftanlagen
     * @param korrekturTracker Tracker fuer Koordinatenkorrekturen
     */
    public DatenImportManager(WindkraftanlageRepository datenSpeicher,
                              KoordinatenKorrekturTracker korrekturTracker) {
        if (datenSpeicher == null || korrekturTracker == null) {
            throw new IllegalArgumentException(Konstanten.FEHLER_PREFIX);
        }
        this.datenSpeicher = datenSpeicher;
        this.korrekturTracker = korrekturTracker;
    }

    /**
     * Liest die CSV-Datei ein, korrigiert Koordinaten und liefert alle Anlagen.
     *
     * Pre: csvPfad existiert
     * Post: Daten sind im Repository geladen und als Liste verfuegbar
     *
     * @param csvPfad Pfad zur CSV-Datei
     * @return Liste aller Windkraftanlagen
     * @throws Exception wenn Einlesen scheitert
     */
    public List<Windkraftanlage> ladeDaten(Path csvPfad) throws Exception {
        ladeCsvDatei(csvPfad);
        korrigiereKoordinaten();
        return datenSpeicher.getAll();
    }

    private void ladeCsvDatei(Path csvPfad) throws Exception {
        /**
         * Lädt die CSV-Datei über das Repository und protokolliert Dauer und Anzahl.
         *
         * Pre: `csvPfad` muss auf eine existierende Datei zeigen.
         * Post: Daten sind im Repository geladen; Anzahl und Zeit wurden ausgegeben
         *       und in ZeitStatistiken erfasst.
         *
         * @param csvPfad Pfad zur CSV-Datei
         * @throws Exception wenn beim Laden ein Fehler auftritt
         */
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
        /**
         * Führt die Koordinatenkorrektur über das Repository aus und gibt Ergebnisse aus.
         *
         * Pre: Repository enthält bereits geladene Datensätze.
         * Post: Korrekturen (falls vorhanden) wurden durchgeführt und vom Tracker ausgegeben.
         */
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
}
