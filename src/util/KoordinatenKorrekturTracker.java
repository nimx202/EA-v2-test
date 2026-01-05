package util;

import model.Windkraftanlage;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasse zum Verfolgen von Koordinaten-Korrekturen.
 * Speichert Informationen über korrigierte Anlagen (Vorher/Nachher).
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Tracking von Korrekturen
 * - KISS: Einfache Liste mit Korrektur-Einträgen
 * - Keine statischen Felder: Jede Instanz verwaltet eigene Korrekturen
 * 
 * Pre: keine
 * Post: Korrekturen können aufgezeichnet und ausgegeben werden
 */
public class KoordinatenKorrekturTracker {

    private final List<KorrekturEintrag> korrekturen;

    /**
     * Erstellt einen neuen Korrektur-Tracker.
     * 
     * Pre: keine
     * Post: Leerer Tracker erstellt
     */
    public KoordinatenKorrekturTracker() {
        this.korrekturen = new ArrayList<>();
    }

    /**
     * Zeichnet eine Koordinaten-Korrektur auf.
     * 
     * Pre: anlage nicht null
     * Post: Korrektur wurde zur Liste hinzugefügt
     * 
    * @param anlage Die korrigierte Anlage
    * @param alterBreitengrad Alter Breitengrad (vor Korrektur)
    * @param alterLaengengrad Alter Längengrad (vor Korrektur)
    * @param neuerBreitengrad Neuer Breitengrad (nach Korrektur)
    * @param neuerLaengengrad Neuer Längengrad (nach Korrektur)
     */
    public void zeichneKorrekturAuf(Windkraftanlage anlage, 
                                     Float alterBreitengrad, Float alterLaengengrad,
                                     Float neuerBreitengrad, Float neuerLaengengrad) {
        KorrekturEintrag eintrag = new KorrekturEintrag(
            anlage.getObjektId(),
            anlage.getName(),
            alterBreitengrad,
            alterLaengengrad,
            neuerBreitengrad,
            neuerLaengengrad
        );
        korrekturen.add(eintrag);
    }

    /**
     * Gibt die Anzahl der aufgezeichneten Korrekturen zurück.
     * 
     * Pre: keine
     * Post: Rückgabe: Anzahl der Korrekturen
     * 
     * @return Anzahl der Korrekturen
     */
    public int getAnzahlKorrekturen() {
        return korrekturen.size();
    }

    /**
     * Gibt die aufgezeichneten Korrekturen aus (Standard: bis zu BEISPIEL_LIMIT).
     * Zeigt Vorher/Nachher Werte für jede Korrektur. Wenn mehr Korrekturen vorhanden
     * sind, wird nur eine Auswahl ausgegeben und die Anzahl der ausgegebenen
     * Beispiel-Datensätze wird am Ende angezeigt.
     *
     * Pre: keine
     * Post: Bis zu 'limit' Korrekturen wurden ausgegeben
     */
    public void gebeKorrekturenAus() {
        gebeKorrekturenAus(Konstanten.korrekturAusgabeLimit);
    }

    /**
     * Gibt die aufgezeichneten Korrekturen aus (bis zu 'limit' Einträge).
     *
     * Pre: limit >= 0
     * Post: Bis zu 'limit' Korrekturen wurden ausgegeben
     *
     * @param limit Maximale Anzahl der auszugebenden Korrekturen
     */
    public void gebeKorrekturenAus(int limit) {
        if (limit < 0) {
            limit = 0; // defensive: negative Limits behandeln
        }

        if (korrekturen.isEmpty()) {
            return;
        }

        AusgabeManager.gebeAus(Konstanten.KORREKTUR_UEBERSCHRIFT);
        AusgabeManager.gebeAusFormat(Konstanten.KORREKTUR_ANZAHL, korrekturen.size());
        AusgabeManager.gebeLeereZeileAus();

        int toPrint = Math.min(limit, korrekturen.size());
        for (int i = 0; i < toPrint; i++) {
            gebeEinzelneKorrekturAus(korrekturen.get(i));
        }

        AusgabeManager.gebeLeereZeileAus();
        AusgabeManager.gebeAusFormat(Konstanten.KORREKTUR_BEISPIEL_AUSGABE, toPrint);
    }

    /**
     * Setzt das Standard-Limit, das beim Aufruf von {@link #gebeKorrekturenAus()} verwendet wird.
     *
     * Pre: limit >= 0
     * Post: Konstanten.korrekturAusgabeLimit ist auf den neuen Wert gesetzt
     *
     * @param limit Neues Standard-Limit für Ausgabe
     */
    public static void setDefaultAusgabeLimit(int limit) {
        if (limit < 0) {
            return; // ignorieren - keine Änderung bei negativen Werten
        }
        Konstanten.korrekturAusgabeLimit = limit;
    }

    /**
     * Gibt eine einzelne Korrektur aus.
     * 
     * Pre: eintrag nicht null
     * Post: Korrektur ausgegeben
     * 
     * @param eintrag Der auszugebende Korrektur-Eintrag
     */
    private void gebeEinzelneKorrekturAus(KorrekturEintrag eintrag) {
        String vorherBreite = formatiereDoppelWert(eintrag.alterBreitengrad);
        String vorherLaenge = formatiereDoppelWert(eintrag.alterLaengengrad);
        String nachherBreite = formatiereDoppelWert(eintrag.neuerBreitengrad);
        String nachherLaenge = formatiereDoppelWert(eintrag.neuerLaengengrad);

        AusgabeManager.gebeAusFormat(
            Konstanten.KORREKTUR_EINZEILER_FORMAT,
            eintrag.objektId,
            eintrag.name,
            vorherBreite,
            vorherLaenge,
            nachherBreite,
            nachherLaenge
        );
    }

    /**
    * Formatiert einen Float-Wert für die Ausgabe.
     * 
     * Pre: keine
     * Post: Rückgabe: formatierter String
     * 
     * @param wert Der zu formatierende Wert
     * @return Formatierter String
     */
    private String formatiereDoppelWert(Float wert) {
        if (wert == null) {
            return Konstanten.ANZEIGE_UNBEKANNT;
        }
        return String.format(Konstanten.KOORDINATEN_FORMAT, wert);
    }

    /**
     * Interne Klasse für einen Korrektur-Eintrag.
     * Speichert alle Informationen zu einer Koordinaten-Korrektur.
     */
    private static class KorrekturEintrag {
        final int objektId;
        final String name;
        final Float alterBreitengrad;
        final Float alterLaengengrad;
        final Float neuerBreitengrad;
        final Float neuerLaengengrad;

        KorrekturEintrag(int objektId, String name,
                        Float alterBreitengrad, Float alterLaengengrad,
                        Float neuerBreitengrad, Float neuerLaengengrad) {
            this.objektId = objektId;
            this.name = name;
            this.alterBreitengrad = alterBreitengrad;
            this.alterLaengengrad = alterLaengengrad;
            this.neuerBreitengrad = neuerBreitengrad;
            this.neuerLaengengrad = neuerLaengengrad;
        }
    }
}
