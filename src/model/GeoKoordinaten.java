package model;

/**
 * Wertobjekt zur Kapselung geographischer Koordinaten.
 *
 * Design-Prinzipien:
 * - Single Responsibility: Verwaltet ausschließlich Breitengrad und Laengengrad
 * - KISS: Einfache Getter/Setter ohne zusätzliche Logik
 * - Modularisierung: Separates Objekt, das von Windkraftanlage genutzt wird
 *
 * Pre: keine besonderen Vorbedingungen
 * Post: Objekt verwaltet Koordinaten-Werte und erlaubt Null für unbekannte Daten
 */
public class GeoKoordinaten {

    private Float breitengrad;
    private Float laengengrad;

    /**
     * Erstellt Koordinaten ohne gesetzte Werte (beide null).
     *
     * Pre: keine
     * Post: beide Koordinaten sind null
     */
    public GeoKoordinaten() {
        // Standard-Konstruktor
    }

    /**
     * Erstellt Koordinaten mit vorgegebenen Werten.
     *
     * Pre: keine
     * Post: übergebene Werte sind gesetzt (dürfen null sein)
     *
     * @param breitengrad Breitengrad (kann null sein)
     * @param laengengrad Laengengrad (kann null sein)
     */
    public GeoKoordinaten(Float breitengrad, Float laengengrad) {
        this.breitengrad = breitengrad;
        this.laengengrad = laengengrad;
    }

    /**
     * Liefert den Breitengrad.
     *
     * Pre: keine
     * Post: Rückgabe kann null sein
     *
     * @return Breitengrad oder null
     */
    public Float getBreitengrad() {
        return breitengrad;
    }

    /**
     * Setzt den Breitengrad.
     *
     * Pre: keine
     * Post: Feld breitengrad entspricht dem übergebenen Wert
     *
     * @param breitengrad neuer Breitengrad (kann null sein)
     */
    public void setBreitengrad(Float breitengrad) {
        this.breitengrad = breitengrad;
    }

    /**
     * Liefert den Laengengrad.
     *
     * Pre: keine
     * Post: Rückgabe kann null sein
     *
     * @return Laengengrad oder null
     */
    public Float getLaengengrad() {
        return laengengrad;
    }

    /**
     * Setzt den Laengengrad.
     *
     * Pre: keine
     * Post: Feld laengengrad entspricht dem übergebenen Wert
     *
     * @param laengengrad neuer Laengengrad (kann null sein)
     */
    public void setLaengengrad(Float laengengrad) {
        this.laengengrad = laengengrad;
    }

    /**
     * Prüft, ob sowohl Breitengrad als auch Laengengrad gesetzt sind.
     *
     * Pre: keine
     * Post: Rückgabe true nur wenn beide Felder nicht null sind
     *
     * @return true falls beide Koordinaten verfügbar sind
     */
    public boolean hatBeideKoordinaten() {
        return breitengrad != null && laengengrad != null;
    }
}
