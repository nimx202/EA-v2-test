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
        zeigeAnlagenOhneKoordinaten(alleAnlagen);
        zeigeErweiterteStatistiken(alleAnlagen);
        zeigeTopWindparks(alleAnlagen);
        zeigeSortierteAnlagen(alleAnlagen);
        zeigeBeispielAnlagen(alleAnlagen);
        ZeitStatistiken.druckeZusammenfassung();
        
        AusgabeManager.gebeGepufferteAusgabenAus();
    }

    /**
     * Zeigt alle Sortierungen an und misst die Laufzeiten.
     *
     * Pre: `alleAnlagen` darf nicht null sein.
     * Post: Drei verschiedene Sortierausgaben wurden erzeugt und jeweils
     *       Zeitstatistiken erfasst.
     *
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    private void zeigeSortierteAnlagen(List<Windkraftanlage> alleAnlagen) {
        AusgabeManager.gebeSektionAus(Konstanten.SORTIERUNGEN_UEBERSCHRIFT);
        zeigeSortierungOrtNameId(alleAnlagen);
        zeigeSortierungLeistungBaujahrName(alleAnlagen);
        zeigeSortierungLandkreisOrtName(alleAnlagen);
    }

    /**
     * Führt die Sortierung nach Ort, Name und ID aus und zeichnet die Laufzeit auf.
     *
     * Pre: `alleAnlagen` darf nicht null sein.
     * Post: Sortierte Teilliste wurde ausgegeben; Laufzeit in ZeitStatistiken erfasst.
     *
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    private void zeigeSortierungOrtNameId(List<Windkraftanlage> alleAnlagen) {
        ZeitMessung timer = ZeitMessung.starte();
        List<Windkraftanlage> sortiert = WindkraftanlagenSortierer.sortiereNachOrtNameId(alleAnlagen);
        float zeitInMillis = timer.stoppeUndGibMillis();

        AusgabeManager.gebeSektionAus(Konstanten.SORTIERUNG_ORT_NAME_ID_UEBERSCHRIFT);
        AusgabeManager.gebeKeyValue(Konstanten.AUSGABE_ANZAHL, sortiert.size());
        AusgabeManager.gebeKeyValue(Konstanten.AUSGABE_DAUER_MS, 
            String.format(Konstanten.FORMAT_DAUER_MS, zeitInMillis));
        AusgabeManager.gebeLeereZeileAus();

        int limit = Math.max(0, Math.min(Konstanten.SORTIER_AUSGABE_LIMIT, sortiert.size()));
        for (int i = 0; i < limit; i++) {
            AusgabeManager.gebeAus(sortiert.get(i).toString());
        }

        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_SORT_ORT_NAME_ID, zeitInMillis);
    }

    /**
     * Führt die Sortierung nach Leistung, Baujahr und Name aus und zeichnet die Laufzeit auf.
     *
     * Pre: `alleAnlagen` darf nicht null sein.
     * Post: Sortierte Teilliste wurde ausgegeben; Laufzeit in ZeitStatistiken erfasst.
     *
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    private void zeigeSortierungLeistungBaujahrName(List<Windkraftanlage> alleAnlagen) {
        ZeitMessung timer = ZeitMessung.starte();
        List<Windkraftanlage> sortiert = WindkraftanlagenSortierer.sortiereNachLeistungBaujahrName(alleAnlagen);
        float zeitInMillis = timer.stoppeUndGibMillis();

        AusgabeManager.gebeSektionAus(Konstanten.SORTIERUNG_LEISTUNG_BAUJAHR_NAME_UEBERSCHRIFT);
        AusgabeManager.gebeKeyValue(Konstanten.AUSGABE_ANZAHL, sortiert.size());
        AusgabeManager.gebeKeyValue(Konstanten.AUSGABE_DAUER_MS, 
            String.format(Konstanten.FORMAT_DAUER_MS, zeitInMillis));
        AusgabeManager.gebeLeereZeileAus();

        int limit = Math.max(0, Math.min(Konstanten.SORTIER_AUSGABE_LIMIT, sortiert.size()));
        for (int i = 0; i < limit; i++) {
            AusgabeManager.gebeAus(sortiert.get(i).toString());
        }

        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_SORT_LEISTUNG_BAUJAHR_NAME, zeitInMillis);
    }

    /**
     * Führt die Sortierung nach Landkreis, Ort und Name aus und zeichnet die Laufzeit auf.
     *
     * Pre: `alleAnlagen` darf nicht null sein.
     * Post: Sortierte Teilliste wurde ausgegeben; Laufzeit in ZeitStatistiken erfasst.
     *
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    private void zeigeSortierungLandkreisOrtName(List<Windkraftanlage> alleAnlagen) {
        ZeitMessung timer = ZeitMessung.starte();
        List<Windkraftanlage> sortiert = WindkraftanlagenSortierer.sortiereNachLandkreisOrtName(alleAnlagen);
        float zeitInMillis = timer.stoppeUndGibMillis();

        AusgabeManager.gebeSektionAus(Konstanten.SORTIERUNG_LANDKREIS_ORT_NAME_UEBERSCHRIFT);
        AusgabeManager.gebeKeyValue(Konstanten.AUSGABE_ANZAHL, sortiert.size());
        AusgabeManager.gebeKeyValue(Konstanten.AUSGABE_DAUER_MS, 
            String.format(Konstanten.FORMAT_DAUER_MS, zeitInMillis));
        AusgabeManager.gebeLeereZeileAus();

        int limit = Math.max(0, Math.min(Konstanten.SORTIER_AUSGABE_LIMIT, sortiert.size()));
        for (int i = 0; i < limit; i++) {
            AusgabeManager.gebeAus(sortiert.get(i).toString());
        }

        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_SORT_LANDKREIS_ORT_NAME, zeitInMillis);
    }

    /**
     * Zeigt Kernstatistiken über die gelesenen Anlagen an und protokolliert Laufzeiten.
     *
     * Pre: `alleAnlagen` darf nicht null sein.
     * Post: Anzahl mit Koordinaten und Anzahl ohne Betreiber wurden ausgegeben
     *       und in `ZeitStatistiken` vermerkt.
     *
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    private void zeigeStatistiken(List<Windkraftanlage> alleAnlagen) {
        ZeitMessung timer = ZeitMessung.starte();
        int anlagenMitKoordinaten = StatistikBerechner.zaehleAnlagenMitKoordinaten(alleAnlagen);
        int anlagenOhneBetreiber = StatistikBerechner.zaehleAnlagenOhneBetreiber(alleAnlagen);

        AusgabeManager.gebeKeyValue(Konstanten.STAT_MIT_KOORDINATEN, anlagenMitKoordinaten);
        AusgabeManager.gebeKeyValue(Konstanten.STAT_OHNE_BETREIBER, anlagenOhneBetreiber);

        float zeitInMillis = timer.stoppeUndGibMillis();
        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_STATISTIKEN, zeitInMillis);
        ZeitStatistiken.zeichneStat(Konstanten.STAT_MIT_KOORDINATEN, String.valueOf(anlagenMitKoordinaten));
        ZeitStatistiken.zeichneStat(Konstanten.STAT_OHNE_BETREIBER, String.valueOf(anlagenOhneBetreiber));
    }

    private void zeigeTopWindparks(List<Windkraftanlage> alleAnlagen) {
        /**
         * Zeigt die Top-Windparks und die Gesamtanzahl an.
         *
         * Pre: `alleAnlagen` darf nicht null sein.
         * Post: Top-N Windparks wurden ausgegeben und Laufzeitstatistiken erfasst.
         *
         * @param alleAnlagen Liste aller Windkraftanlagen
         */
        ZeitMessung timer = ZeitMessung.starte();
        List<WindparkEintrag> topWindparks = WindparkAnalysierer.holeTopWindparks(
            alleAnlagen, Konstanten.TOP_LIMIT);
        String ueberschrift = String.format(Konstanten.TOP_PARKS, Konstanten.TOP_LIMIT);
        int letztePosition = ueberschrift.length() - 1;
        if (letztePosition >= 0 && ueberschrift.charAt(letztePosition) == Konstanten.ZEILENUMBRUCH) {
            ueberschrift = ueberschrift.substring(0, letztePosition);
        }
        AusgabeManager.gebeSektionAus(ueberschrift);

        for (WindparkEintrag eintrag : topWindparks) {
            AusgabeManager.gebeKeyValue(eintrag.getName(), eintrag.getAnzahl());
        }

        int gesamtAnzahl = WindparkAnalysierer.zaehleWindparks(alleAnlagen).size();
        AusgabeManager.gebeKeyValue(Konstanten.STAT_ANZAHL_PARKS, gesamtAnzahl);

        float zeitInMillis = timer.stoppeUndGibMillis();
        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_TOP_WINDPARKS, zeitInMillis);
        ZeitStatistiken.zeichneStat(Konstanten.STAT_ANZAHL_PARKS, String.valueOf(gesamtAnzahl));
    }

    private void zeigeBeispielAnlagen(List<Windkraftanlage> alleAnlagen) {
        /**
         * Gibt eine kleine Menge der geladenen Anlagen als Beispiele aus.
         *
         * Pre: `alleAnlagen` darf nicht null sein.
         * Post: Bis zu `Konstanten.BEISPIEL_LIMIT` Anlagen wurden ausgegeben
         *       und die Laufzeit in `ZeitStatistiken` vermerkt.
         *
         * @param alleAnlagen Liste aller Windkraftanlagen
         */
        ZeitMessung timer = ZeitMessung.starte();
        AusgabeManager.gebeSektionAus(Konstanten.BEISPIEL_DATENSAETZE);

        int anzahlZeigen = Math.min(Konstanten.BEISPIEL_LIMIT, alleAnlagen.size());
        for (int i = 0; i < anzahlZeigen; i++) {
            AusgabeManager.gebeAus(alleAnlagen.get(i).toString());
        }

        float zeitInMillis = timer.stoppeUndGibMillis();
        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_BEISPIEL_DATENSAETZE, zeitInMillis);
        ZeitStatistiken.zeichneStat(Konstanten.STAT_BEISPIEL_AUSGEGEBEN, String.valueOf(anzahlZeigen));
    }

    /**
     * Zeigt erweiterte Statistiken: südlichste Anlage, Anlage mit höchster Leistung,
     * Anlage mit meisten Windrädern und Gesamtleistung aller Anlagen.
     *
     * Pre: `alleAnlagen` darf nicht null sein.
     * Post: Alle vier erweiterten Statistiken wurden ausgegeben mit Laufzeitmessung.
     *
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    private void zeigeErweiterteStatistiken(List<Windkraftanlage> alleAnlagen) {
        ZeitMessung timer = ZeitMessung.starte();
        AusgabeManager.gebeSektionAus(Konstanten.ERWEITERTE_STATISTIKEN_UEBERSCHRIFT);

        zeigeSuedlichsteAnlage(alleAnlagen);
        zeigeAnlageMitHoechsterLeistung(alleAnlagen);
        zeigeAnlageMitMeistenWindraedern(alleAnlagen);
        zeigeGesamtleistungAllerAnlagen(alleAnlagen);

        float zeitInMillis = timer.stoppeUndGibMillis();
        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_ERWEITERTE_STATISTIKEN, zeitInMillis);
    }

    /**
     * Zeigt die südlichste Anlage (kleinster Breitengrad).
     *
     * Pre: `alleAnlagen` darf nicht null sein.
     * Post: Südlichste Anlage wurde ausgegeben oder "keine Anlage gefunden".
     *
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    private void zeigeSuedlichsteAnlage(List<Windkraftanlage> alleAnlagen) {
        AusgabeManager.gebeSektionAus(Konstanten.STAT_SUEDLICHSTE_ANLAGE);
        Windkraftanlage suedlichste = StatistikBerechner.findeSuedlichsteAnlage(alleAnlagen);

        if (suedlichste == null) {
            AusgabeManager.gebeAus(Konstanten.KEINE_ANLAGE_GEFUNDEN);
            ZeitStatistiken.zeichneStat(Konstanten.STAT_SUEDLICHSTE_ANLAGE, Konstanten.ANZEIGE_UNBEKANNT);
        } else {
            AusgabeManager.gebeAusFormat(Konstanten.ANLAGE_DETAILS_FORMAT,
                suedlichste.getObjektId(),
                suedlichste.getName(),
                suedlichste.getOrt());
            AusgabeManager.gebeAusFormat(Konstanten.BREITENGRAD_FORMAT,
                suedlichste.getGeoKoordinaten().getBreitengrad());
            AusgabeManager.gebeAusFormat(Konstanten.LAENGENGRAD_FORMAT,
                suedlichste.getGeoKoordinaten().getLaengengrad());
            String wert = String.format(Konstanten.FORMAT_SUEDLICHSTE_ANLAGE_STAT,
                suedlichste.getObjektId(),
                suedlichste.getName(),
                suedlichste.getOrt(),
                suedlichste.getGeoKoordinaten().getBreitengrad());
            ZeitStatistiken.zeichneStat(Konstanten.STAT_SUEDLICHSTE_ANLAGE, wert);
        }
    }

    /**
     * Zeigt die Anlage mit der höchsten Gesamtleistung.
     *
     * Pre: `alleAnlagen` darf nicht null sein.
     * Post: Anlage mit höchster Leistung wurde ausgegeben oder "keine Anlage gefunden".
     *
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    private void zeigeAnlageMitHoechsterLeistung(List<Windkraftanlage> alleAnlagen) {
        AusgabeManager.gebeSektionAus(Konstanten.STAT_HOECHSTE_LEISTUNG);
        Windkraftanlage anlage = StatistikBerechner.findeAnlageMitHoechsterLeistung(alleAnlagen);

        if (anlage == null) {
            AusgabeManager.gebeAusFormat(Konstanten.KEINE_ANLAGE_GEFUNDEN);
            ZeitStatistiken.zeichneStat(Konstanten.STAT_HOECHSTE_LEISTUNG, Konstanten.ANZEIGE_UNBEKANNT);
        } else {
            AusgabeManager.gebeAusFormat(Konstanten.ANLAGE_DETAILS_FORMAT,
                anlage.getObjektId(),
                anlage.getName(),
                anlage.getOrt());
            AusgabeManager.gebeAusFormat(Konstanten.GESAMTLEISTUNG_FORMAT,
                anlage.getGesamtLeistungMW());
            String wert = String.format(Konstanten.FORMAT_HOECHSTE_LEISTUNG_STAT,
                anlage.getObjektId(),
                anlage.getName(),
                anlage.getOrt(),
                anlage.getGesamtLeistungMW());
            ZeitStatistiken.zeichneStat(Konstanten.STAT_HOECHSTE_LEISTUNG, wert);
        }
    }

    /**
     * Zeigt die Anlage mit den meisten Windrädern (Anzahl).
     *
     * Pre: `alleAnlagen` darf nicht null sein.
     * Post: Anlage mit meisten Windrädern wurde ausgegeben oder "keine Anlage gefunden".
     *
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    private void zeigeAnlageMitMeistenWindraedern(List<Windkraftanlage> alleAnlagen) {
        AusgabeManager.gebeSektionAus(Konstanten.STAT_MEISTE_WINDRAEDER);
        Windkraftanlage anlage = StatistikBerechner.findeAnlageMitMeistenWindraedern(alleAnlagen);

        if (anlage == null) {
            AusgabeManager.gebeAusFormat(Konstanten.KEINE_ANLAGE_GEFUNDEN);
            ZeitStatistiken.zeichneStat(Konstanten.STAT_MEISTE_WINDRAEDER, Konstanten.ANZEIGE_UNBEKANNT);
        } else {
            AusgabeManager.gebeAusFormat(Konstanten.ANLAGE_DETAILS_FORMAT,
                anlage.getObjektId(),
                anlage.getName(),
                anlage.getOrt());
            AusgabeManager.gebeAusFormat(Konstanten.ANZAHL_WINDRAEDER_FORMAT,
                anlage.getAnzahl());
            String wert = String.format(Konstanten.FORMAT_MEISTE_WINDRAEDER_STAT,
                anlage.getObjektId(),
                anlage.getName(),
                anlage.getOrt(),
                anlage.getAnzahl());
            ZeitStatistiken.zeichneStat(Konstanten.STAT_MEISTE_WINDRAEDER, wert);
        }
    }

    /**
     * Zeigt die Gesamtleistung aller Windkraftanlagen.
     *
     * Pre: `alleAnlagen` darf nicht null sein.
     * Post: Gesamtleistung wurde ausgegeben.
     *
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    private void zeigeGesamtleistungAllerAnlagen(List<Windkraftanlage> alleAnlagen) {
        float gesamtleistung = StatistikBerechner.berechneGesamtLeistung(alleAnlagen);
        AusgabeManager.gebeAusFormat(Konstanten.GESAMTLEISTUNG_ALLER_FORMAT, gesamtleistung);
        String wert = String.format(Konstanten.FORMAT_GESAMTLEISTUNG_STAT, gesamtleistung);
        ZeitStatistiken.zeichneStat(Konstanten.STAT_GESAMTLEISTUNG_ALLER, wert);
    }

    /**
     * Zeigt alle Datensaetze ohne Koordinaten zur Analyse an.
     * Gibt eine Ueberschrift, die Anzahl und einige Beispiel-Datensaetze aus.
     *
     * Pre: `alleAnlagen` darf nicht null sein.
     * Post: Ausgabe der Datensaetze ohne Koordinaten erfolgte.
     */
    private void zeigeAnlagenOhneKoordinaten(List<Windkraftanlage> alleAnlagen) {
        AusgabeManager.gebeSektionAus(Konstanten.DATENSAETZE_OHNE_KOORDINATEN_UEBERSCHRIFT);

        java.util.List<Windkraftanlage> ohneKoordinaten = StatistikBerechner.filterAnlagenOhneKoordinaten(alleAnlagen);
        AusgabeManager.gebeKeyValue(Konstanten.AUSGABE_ANZAHL, ohneKoordinaten.size());

        int anzahlZeigen = Math.min(Konstanten.BEISPIEL_LIMIT, ohneKoordinaten.size());
        for (int i = 0; i < anzahlZeigen; i++) {
            AusgabeManager.gebeAus(ohneKoordinaten.get(i).toString());
        }
    }
}

