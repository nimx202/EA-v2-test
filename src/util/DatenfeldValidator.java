package util;

/**
 * Validator für Datenfeldvalidierung (Koordinaten, Baujahr, Gesamtleistung).
 * Stateless Utility-Klasse zur Überprüfung der Gültigkeit verschiedener Datenfelder.
 * 
 * Pre: Eingabewerte können null oder beliebige numerische Werte sein
 * Post: Methoden geben true zurück, wenn Werte im zulässigen Bereich liegen
 */
public class DatenfeldValidator {
    
    /**
     * Private Konstruktor - Utility-Klasse, keine Instanzen erlaubt.
     */
    private DatenfeldValidator() {
        // Utility-Klasse
    }

    // ========== KOORDINATEN-VALIDIERUNG ==========

    /**
     * Findet fehlerhafte Breitengrade.
     * 
     * Pre: breitengrad kann null oder beliebiger Double-Wert sein
     * Post: Gibt true zurück, wenn Breitengrad ungültig ist ({@literal <} 47.0 oder {@literal >} 55.0)
     *
     * @param breitengrad der zu prüfende Breitengrad
     * @return true, wenn ungültig
     */
    public static boolean findeFehlerhaftenBreitengrad(Double breitengrad) {
        if (breitengrad == null) {
            return false;
        }
        return !istBreitengradGültig(breitengrad);
    }

    /**
     * Prüft ob Breitengrad gültig ist.
     * 
     * Pre: breitengrad kann null sein
     * Post: Gibt true zurück, wenn {@literal 47.0 <= breitengrad <= 55.0}
     *
     * @param breitengrad der zu prüfende Breitengrad
     * @return true, wenn gültig
     */
    public static boolean istBreitengradGültig(Double breitengrad) {
        if (breitengrad == null) {
            return true; // null ist erlaubt
        }
        return breitengrad >= Konstanten.MIN_BREITENGRAD_DE && breitengrad <= Konstanten.MAX_BREITENGRAD_DE;
    }

    /**
     * Findet fehlerhafte Längengrade.
     * 
     * Pre: längengrad kann null oder beliebiger Double-Wert sein
     * Post: Gibt true zurück, wenn Längengrad ungültig ist ({@literal <} 5.0 oder {@literal >} 16.0)
     *
     * @param längengrad der zu prüfende Längengrad
     * @return true, wenn ungültig
     */
    public static boolean findeFehlerhaftenLängengrad(Double längengrad) {
        if (längengrad == null) {
            return false;
        }
        return !istLängengradGültig(längengrad);
    }

    /**
     * Prüft ob Längengrad gültig ist.
     * 
     * Pre: längengrad kann null sein
     * Post: Gibt true zurück, wenn {@literal 5.0 <= längengrad <= 16.0}
     *
     * @param längengrad der zu prüfende Längengrad
     * @return true, wenn gültig
     */
    public static boolean istLängengradGültig(Double längengrad) {
        if (längengrad == null) {
            return true; // null ist erlaubt
        }
        return längengrad >= Konstanten.MIN_LÄNGENGRAD_DE && längengrad <= Konstanten.MAX_LÄNGENGRAD_DE;
    }

    // ========== BAUJAHR-VALIDIERUNG ==========

    /**
     * Findet fehlerhafte Baujahre.
     * 
     * Pre: baujahr kann null oder beliebiger Integer-Wert sein
     * Post: Gibt true zurück, wenn Baujahr ungültig ist ({@literal <} 1980 oder {@literal >} 2026)
     *
     * @param baujahr das zu prüfende Baujahr
     * @return true, wenn ungültig
     */
    public static boolean findeFehlerhaftesBaujahr(Integer baujahr) {
        if (baujahr == null) {
            return false;
        }
        return !istBaujahrGültig(baujahr);
    }

    /**
     * Prüft ob Baujahr gültig ist.
     * 
     * Pre: baujahr kann null sein
     * Post: Gibt true zurück, wenn {@literal Konstanten.MIN_BAUJAHR <= baujahr <= Konstanten.MAX_BAUJAHR}
     *
     * @param baujahr das zu prüfende Baujahr
     * @return true, wenn gültig
     */
    public static boolean istBaujahrGültig(Integer baujahr) {
        if (baujahr == null) {
            return true; // null ist erlaubt
        }
        return baujahr >= Konstanten.MIN_BAUJAHR && baujahr <= Konstanten.MAX_BAUJAHR;
    }

    // ========== GESAMTLEISTUNG-VALIDIERUNG ==========

    /**
     * Findet fehlerhafte Gesamtleistungen.
     * 
     * Pre: gesamtLeistung kann null oder beliebiger Double-Wert sein
     * Post: Gibt true zurück, wenn Leistung ungültig ist ({@literal <} 0.0 oder {@literal >} 200.0)
     *
     * @param gesamtLeistung die zu prüfende Leistung
     * @return true, wenn ungültig
     */
    public static boolean findeFehlerhafteGesamtLeistung(Double gesamtLeistung) {
        if (gesamtLeistung == null) {
            return false;
        }
        return !istGesamtLeistungGültig(gesamtLeistung);
    }

    /**
     * Prüft ob Gesamtleistung gültig ist.
     * 
     * Pre: gesamtLeistung kann null sein
     * Post: Gibt true zurück, wenn {@literal 0.0 <= gesamtLeistung <= 200.0}
     *
     * @param gesamtLeistung die zu prüfende Leistung
     * @return true, wenn gültig
     */
    public static boolean istGesamtLeistungGültig(Double gesamtLeistung) {
        if (gesamtLeistung == null) {
            return true; // null ist erlaubt
        }
        return gesamtLeistung >= Konstanten.MIN_GESAMTLEISTUNG_MW
                && gesamtLeistung <= Konstanten.MAX_GESAMTLEISTUNG_MW;
    }
}
