package util;

/**
 * Corrector für Datenfeldkorrektur (Koordinaten, Baujahr, Gesamtleistung).
 * Stateless Utility-Klasse zur Behebung erkannter Fehler in verschiedenen Datenfeldern.
 * 
 * Pre: Eingabewerte können null oder beliebige numerische Werte sein
 * Post: Methoden geben korrigierte Werte zurück oder null, wenn nicht korrigierbar
 */
public class DatenfeldKorrektor {
    
    /**
     * Private Konstruktor - Utility-Klasse, keine Instanzen erlaubt.
     */
    private DatenfeldKorrektor() {
        // Utility-Klasse
    }

    // ========== KOORDINATEN-KORREKTUR ==========

    /**
     * Korrigiert fehlerhafte Breitengrade (fehlendes Komma).
     * 
     * Pre: breitengrad ist außerhalb des zulässigen Bereichs
     * Post: Gibt korrigierten Breitengrad zurück oder null, wenn nicht korrigierbar
     *
     * @param breitengrad der zu korrigierende Breitengrad
     * @return korrigierter Breitengrad oder null
     */
    public static Double korrigiereFehlerhaftenBreitengrad(Double breitengrad) {
        if (breitengrad == null || breitengrad == 0.0) {
            return null;
        }

        Double korrigiert = korrigiereBreitengrad(breitengrad);
        
        if (DatenfeldValidator.istBreitengradGültig(korrigiert)) {
            return korrigiert;
        }
        return null;
    }

    /**
     * Hilfsmethode zur Korrektur von Breitengrad durch Division durch 1000.
     * 
     * Pre: breitengrad ist nicht null
     * Post: Gibt breitengrad / 1000 zurück
     */
    private static Double korrigiereBreitengrad(Double breitengrad) {
        return breitengrad / Konstanten.BREITENGRAD_FEHLERFAKTOR;
    }

    /**
     * Korrigiert fehlerhafte Längengrade (fehlendes Komma).
     * 
     * Pre: längengrad ist außerhalb des zulässigen Bereichs
     * Post: Gibt korrigierten Längengrad zurück oder null, wenn nicht korrigierbar
     *
     * @param längengrad der zu korrigierende Längengrad
     * @return korrigierter Längengrad oder null
     */
    public static Double korrigiereFehlerhaftenLängengrad(Double längengrad) {
        if (längengrad == null || längengrad == 0.0) {
            return null;
        }

        Double korrigiert = korrigiereLängengrad(längengrad);
        
        if (DatenfeldValidator.istLängengradGültig(korrigiert)) {
            return korrigiert;
        }
        return null;
    }

    /**
     * Hilfsmethode zur Korrektur von Längengrad durch Division durch 1000.
     * 
     * Pre: längengrad ist nicht null
     * Post: Gibt längengrad / 1000 zurück
     */
    private static Double korrigiereLängengrad(Double längengrad) {
        return längengrad / Konstanten.LÄNGENGRAD_FEHLERFAKTOR;
    }

    // ========== BAUJAHR-KORREKTUR ==========

    /**
     * Korrigiert fehlerhafte Baujahre.
     * 
     * Pre: baujahr ist außerhalb des zulässigen Bereichs
     * Post: Gibt korrigiertes Baujahr zurück oder null, wenn nicht korrigierbar
     *
     * @param baujahr das zu korrigierende Jahr
     * @return korrigiertes Baujahr oder null
     */
    public static Integer korrigiereFehlerhaftesBaujahr(Integer baujahr) {
        if (baujahr == null) {
            return null;
        }

        // Falls zu groß: teile durch Fehlerfaktor
        if (baujahr > Konstanten.MAX_BAUJAHR) {
            Integer korrigiert = korrigiereBaujahr(baujahr);
            if (DatenfeldValidator.istBaujahrGültig(korrigiert)) {
                return korrigiert;
            }
        }

        // Falls zu klein: multipliziere mit Fehlerfaktor
        if (baujahr < Konstanten.MIN_BAUJAHR) {
            Integer korrigiert = baujahr * Konstanten.BAUJAHR_FEHLERFAKTOR;
            if (DatenfeldValidator.istBaujahrGültig(korrigiert)) {
                return korrigiert;
            }
        }

        return null;
    }

    /**
     * Hilfsmethode zur Korrektur von Baujahr durch Division durch Fehlerfaktor.
     * 
     * Pre: baujahr ist nicht null
     * Post: Gibt baujahr / 10 zurück
     */
    private static Integer korrigiereBaujahr(Integer baujahr) {
        return baujahr / Konstanten.BAUJAHR_FEHLERFAKTOR;
    }

    // ========== GESAMTLEISTUNG-KORREKTUR ==========

    /**
     * Korrigiert fehlerhafte Gesamtleistungen.
     * 
     * Pre: gesamtLeistung ist außerhalb des zulässigen Bereichs
     * Post: Gibt korrigierte Leistung zurück oder null, wenn nicht korrigierbar
     *
     * @param gesamtLeistung die zu korrigierende Gesamtleistung
     * @return korrigierte Leistung oder null
     */
    public static Double korrigiereFehlerhafteGesamtLeistung(Double gesamtLeistung) {
        if (gesamtLeistung == null || gesamtLeistung == 0.0) {
            return null;
        }

        // Falls zu groß: teile durch Fehlerfaktor
        if (gesamtLeistung > Konstanten.MAX_GESAMTLEISTUNG_MW) {
            Double korrigiert = korrigiereGesamtLeistung(gesamtLeistung);
            if (DatenfeldValidator.istGesamtLeistungGültig(korrigiert)) {
                return korrigiert;
            }
        }

        return null;
    }

    /**
     * Hilfsmethode zur Korrektur von Gesamtleistung durch Division durch Fehlerfaktor.
     * 
     * Pre: gesamtLeistung ist nicht null
     * Post: Gibt gesamtLeistung / 100 zurück
     */
    private static Double korrigiereGesamtLeistung(Double gesamtLeistung) {
        return gesamtLeistung / Konstanten.GESAMTLEISTUNG_FEHLERFAKTOR;
    }
}
