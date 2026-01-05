package view;

import java.util.List;
import model.Windkraftanlage;
import model.WindparkEintrag;
import util.AusgabeManager;
import util.Konstanten;
import util.StatistikBerechner;
import util.WindkraftanlagenSortierer;
import util.WindparkAnalysierer;
import util.ZeitMessung;
import util.ZeitStatistiken;

/**
 * Delegiert alle Analyse- und Ausgabeschritte an spezialisierte Methoden.
 *
 * Design-Prinzipien:
 * - Single Responsibility: Nur Analyse und Ausgabe
 * - KISS: Sequenzielles Abarbeiten ohne verschachtelte Logik
 * - Modularisierung: Hält Anwendungsablauf schlank
 *
 * Pre: Listen muessen initialisiert sein
 * Post: Statistiken, Top-Windparks, Beispiele und Zusammenfassung ausgegeben
 */
public class AnalyseAusgabeKoordinator {

    /**
     * Fuehrt alle Analyse- und Ausgabeschritte aus.
     *
     * Pre: alleAnlagen darf nicht null sein
     * Post: Saemtliche Ausgaben erfolgt
     *
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    public void analysiereUndGebeAus(List<Windkraftanlage> alleAnlagen) {
        zeigeStatistiken(alleAnlagen);
        zeigeTopWindparks(alleAnlagen);
        zeigeSortierteAnlagen(alleAnlagen);
        zeigeBeispielAnlagen(alleAnlagen);
        ZeitStatistiken.druckeZusammenfassung();
    }

    private void zeigeSortierteAnlagen(List<Windkraftanlage> alleAnlagen) {
        AusgabeManager.gebeUeberschriftAus(Konstanten.SORTIERUNGEN_UEBERSCHRIFT);
        zeigeSortierungOrtNameId(alleAnlagen);
        zeigeSortierungLeistungBaujahrName(alleAnlagen);
        zeigeSortierungLandkreisOrtName(alleAnlagen);
    }

    private void zeigeSortierungOrtNameId(List<Windkraftanlage> alleAnlagen) {
        AusgabeManager.gebeUeberschriftAus(Konstanten.SORTIERUNG_ORT_NAME_ID_UEBERSCHRIFT);
        ZeitMessung timer = ZeitMessung.starte();
        List<Windkraftanlage> sortiert = WindkraftanlagenSortierer.sortiereNachOrtNameId(alleAnlagen);
        float zeitInMillis = timer.stoppeUndGibMillis();
        AusgabeManager.gebeAusFormat(Konstanten.SORTIERUNG_ZEIT_FORMAT, zeitInMillis);
        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_SORT_ORT_NAME_ID, zeitInMillis);

        int limit = Math.min(Konstanten.SORTIER_AUSGABE_LIMIT, sortiert.size());
        AusgabeManager.gebeAusFormat(Konstanten.SORTIERUNG_ZEIGE_ERSTE_FORMAT, limit);
        for (int i = 0; i < limit; i++) {
            AusgabeManager.gebeAus(sortiert.get(i).toString());
        }
        AusgabeManager.gebeLeereZeileAus();
    }

    private void zeigeSortierungLeistungBaujahrName(List<Windkraftanlage> alleAnlagen) {
        AusgabeManager.gebeUeberschriftAus(Konstanten.SORTIERUNG_LEISTUNG_BAUJAHR_NAME_UEBERSCHRIFT);
        ZeitMessung timer = ZeitMessung.starte();
        List<Windkraftanlage> sortiert = WindkraftanlagenSortierer.sortiereNachLeistungBaujahrName(alleAnlagen);
        float zeitInMillis = timer.stoppeUndGibMillis();
        AusgabeManager.gebeAusFormat(Konstanten.SORTIERUNG_ZEIT_FORMAT, zeitInMillis);
        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_SORT_LEISTUNG_BAUJAHR_NAME, zeitInMillis);

        int limit = Math.min(Konstanten.SORTIER_AUSGABE_LIMIT, sortiert.size());
        AusgabeManager.gebeAusFormat(Konstanten.SORTIERUNG_ZEIGE_ERSTE_FORMAT, limit);
        for (int i = 0; i < limit; i++) {
            AusgabeManager.gebeAus(sortiert.get(i).toString());
        }
        AusgabeManager.gebeLeereZeileAus();
    }

    private void zeigeSortierungLandkreisOrtName(List<Windkraftanlage> alleAnlagen) {
        AusgabeManager.gebeUeberschriftAus(Konstanten.SORTIERUNG_LANDKREIS_ORT_NAME_UEBERSCHRIFT);
        ZeitMessung timer = ZeitMessung.starte();
        List<Windkraftanlage> sortiert = WindkraftanlagenSortierer.sortiereNachLandkreisOrtName(alleAnlagen);
        float zeitInMillis = timer.stoppeUndGibMillis();
        AusgabeManager.gebeAusFormat(Konstanten.SORTIERUNG_ZEIT_FORMAT, zeitInMillis);
        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_SORT_LANDKREIS_ORT_NAME, zeitInMillis);

        int limit = Math.min(Konstanten.SORTIER_AUSGABE_LIMIT, sortiert.size());
        AusgabeManager.gebeAusFormat(Konstanten.SORTIERUNG_ZEIGE_ERSTE_FORMAT, limit);
        for (int i = 0; i < limit; i++) {
            AusgabeManager.gebeAus(sortiert.get(i).toString());
        }
        AusgabeManager.gebeLeereZeileAus();
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
