package util;

import model.Windkraftanlage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility-Klasse zur Validierung von geografischen Koordinaten.
 * Prüft ob Koordinaten im gültigen Bereich für Deutschland liegen.
 *
 * Design: Stateless Utility-Klasse (keine Instanzen)
 */
public final class KoordinatenValidator {

    /**
     * Privater Konstruktor verhindert Instanziierung der Utility-Klasse.
     */
    private KoordinatenValidator() {
        // Keine Instanzen möglich
    }

    /**
     * Findet alle Windkraftanlagen mit fehlerhaften Koordinaten.
     * 
     * Fehlerhafte Koordinaten liegen außerhalb der gültigen Bereiche für Deutschland:
     * - Breitengrad: 47.0 bis 55.0
     * - Längengrad: 5.0 bis 16.0
     *
     * Vorgehensweise:
     * 1. Filter alle Anlagen mit Koordinaten (nicht null)
     * 2. Prüfe ob Koordinaten im gültigen Bereich liegen
     * 3. Sammle fehlerhafte Anlagen in Liste
     *
     * Pre: anlagen nicht null
     * Post: Rückgabe Liste mit fehlerhaften Anlagen
     *
     * @param anlagen Liste aller Windkraftanlagen
     * @return Liste der Anlagen mit fehlerhaften Koordinaten
     */
    public static List<Windkraftanlage> findeFehlerhafte(List<Windkraftanlage> anlagen) {
        return anlagen.stream()
                .filter(anlage -> anlage.getBreitengrad() != null || anlage.getLaengengrad() != null)
                .filter(anlage -> !sindKoordinatenGültig(anlage.getBreitengrad(), anlage.getLaengengrad()))
                .collect(Collectors.toList());
    }

    /**
     * Prüft ob Koordinaten im gültigen Bereich für Deutschland liegen.
     *
     * Gültige Bereiche:
     * - Breitengrad: 47.0 bis 55.0 (Nord-Süd-Ausdehnung)
     * - Längengrad: 5.0 bis 16.0 (West-Ost-Ausdehnung)
     *
     * Pre: keine
     * Post: Rückgabe true wenn beide Koordinaten gültig oder beide null
     *
     * @param breitengrad Breitengrad (kann null sein)
     * @param laengengrad Längengrad (kann null sein)
     * @return true wenn Koordinaten gültig sind
     */
    public static boolean sindKoordinatenGültig(Double breitengrad, Double laengengrad) {
        // Beide null ist gültig (keine Koordinaten vorhanden)
        if (breitengrad == null && laengengrad == null) {
            return true;
        }

        // Wenn nur eine Koordinate fehlt, ist der Datensatz fehlerhaft
        if (breitengrad == null || laengengrad == null) {
            return false;
        }

        // Prüfe ob Koordinaten im gültigen Bereich liegen
        boolean breitengradGültig = breitengrad >= Konstanten.MIN_BREITENGRAD_DE
                && breitengrad <= Konstanten.MAX_BREITENGRAD_DE;
        boolean laengengradGültig = laengengrad >= Konstanten.MIN_LÄNGENGRAD_DE
                && laengengrad <= Konstanten.MAX_LÄNGENGRAD_DE;

        return breitengradGültig && laengengradGültig;
    }

    /**
     * Zählt die Anzahl fehlerhafter Koordinaten in einer Liste von Anlagen.
     *
     * Pre: anlagen nicht null
     * Post: Rückgabe Anzahl >= 0
     *
     * @param anlagen Liste aller Windkraftanlagen
     * @return Anzahl Anlagen mit fehlerhaften Koordinaten
     */
    public static int zähleFehlerhafte(List<Windkraftanlage> anlagen) {
        return findeFehlerhafte(anlagen).size();
    }
    /**
     * Findet alle Windkraftanlagen mit fehlerhaftem Baujahr.
     * 
     * Fehlerhaftes Baujahr liegt außerhalb des gültigen Bereichs:
     * - Minimum: 1980 (erste Windkraftanlagen in Deutschland)
     * - Maximum: 2026 (aktuelles Jahr)
     *
     * Vorgehensweise:
     * 1. Filter alle Anlagen mit Baujahr (nicht null)
     * 2. Prüfe ob Baujahr im gültigen Bereich liegt
     * 3. Sammle fehlerhafte Anlagen in Liste
     *
     * Pre: anlagen nicht null
     * Post: Rückgabe Liste mit fehlerhaften Anlagen
     *
     * @param anlagen Liste aller Windkraftanlagen
     * @return Liste der Anlagen mit fehlerhaftem Baujahr
     */
    public static List<Windkraftanlage> findeFehlerhafesBaujahr(List<Windkraftanlage> anlagen) {
        return anlagen.stream()
                .filter(anlage -> anlage.getBaujahr() != null)
                .filter(anlage -> !istBaujahrGültig(anlage.getBaujahr()))
                .collect(Collectors.toList());
    }

    /**
     * Prüft ob ein Baujahr im gültigen Bereich liegt.
     *
     * Gültiger Bereich: 1980 bis 2026
     *
     * Pre: keine
     * Post: Rückgabe true wenn Baujahr gültig oder null
     *
     * @param baujahr Baujahr (kann null sein)
     * @return true wenn Baujahr gültig ist
     */
    public static boolean istBaujahrGültig(Integer baujahr) {
        if (baujahr == null) {
            return true; // null ist gültig (keine Angabe)
        }
        return baujahr >= Konstanten.MIN_BAUJAHR && baujahr <= Konstanten.MAX_BAUJAHR;
    }

    /**
     * Findet alle Windkraftanlagen mit fehlerhafter Gesamtleistung.
     * 
     * Fehlerhafte Gesamtleistung liegt außerhalb des gültigen Bereichs:
     * - Minimum: 0.0 MW (keine negativen Werte)
     * - Maximum: 200.0 MW (realistischer Maximalwert)
     *
     * Vorgehensweise:
     * 1. Filter alle Anlagen mit Gesamtleistung (nicht null)
     * 2. Prüfe ob Gesamtleistung im gültigen Bereich liegt
     * 3. Sammle fehlerhafte Anlagen in Liste
     *
     * Pre: anlagen nicht null
     * Post: Rückgabe Liste mit fehlerhaften Anlagen
     *
     * @param anlagen Liste aller Windkraftanlagen
     * @return Liste der Anlagen mit fehlerhafter Gesamtleistung
     */
    public static List<Windkraftanlage> findeFehlerhafeGesamtLeistung(List<Windkraftanlage> anlagen) {
        return anlagen.stream()
                .filter(anlage -> anlage.getGesamtLeistungMW() != null)
                .filter(anlage -> !istGesamtLeistungGültig(anlage.getGesamtLeistungMW()))
                .collect(Collectors.toList());
    }

    /**
     * Prüft ob eine Gesamtleistung im gültigen Bereich liegt.
     *
     * Gültiger Bereich: 0.0 bis 200.0 MW
     *
     * Pre: keine
     * Post: Rückgabe true wenn Gesamtleistung gültig oder null
     *
     * @param gesamtLeistungMW Gesamtleistung in MW (kann null sein)
     * @return true wenn Gesamtleistung gültig ist
     */
    public static boolean istGesamtLeistungGültig(Double gesamtLeistungMW) {
        if (gesamtLeistungMW == null) {
            return true; // null ist gültig (keine Angabe)
        }
        return gesamtLeistungMW >= Konstanten.MIN_GESAMTLEISTUNG_MW 
                && gesamtLeistungMW <= Konstanten.MAX_GESAMTLEISTUNG_MW;
    }
}
