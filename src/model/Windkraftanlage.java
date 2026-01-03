package model;

import util.FeldParser;

/**
 * Modellklasse für eine Windkraftanlage (Windenergieanlage).
 * Kapselt alle Daten einer einzelnen Windkraftanlage mit Gettern und Settern.
 *
 * Attribute können null sein wenn Daten nicht bekannt sind:
 * - baujahr: kann null sein
 * - gesamtLeistungMW: kann null sein
 * - anzahl: kann null sein (Anzahl der Anlagen)
 *
 * Vertrag:
 * Pre: keine besonderen Vorbedingungen
 * Post: Objekt ist vollständig initialisiert und verwendbar
 */
public class Windkraftanlage {

    private int objektId;
    private String name;
    private Integer baujahr;
    private Double gesamtLeistungMW;
    private Integer anzahl;
    private String typ;
    private String ort;
    private String landkreis;
    private Double breitengrad;
    private Double laengengrad;
    private String betreiber;
    private String bemerkungen;

    /**
     * Konstruktor ohne Argumente für Bean-Pattern.
     *
     * Post: Alle numerischen Felder haben Default-Wert 0/null
     */
    public Windkraftanlage() {
        // Standard-Konstruktor
    }

    /**
     * Vollständiger Konstruktor mit allen Attributen.
     *
     * Pre: objektId >= 0
     * Post: Alle Attribute sind gesetzt
     *
     * @param objektId eindeutige ID der Anlage
     * @param name Name der Anlage/des Windparks
     * @param baujahr Baujahr (kann null sein)
     * @param gesamtLeistungMW Gesamtleistung in MW (kann null sein)
     * @param anzahl Anzahl der Anlagen (kann null sein)
     * @param typ Anlagentyp
     * @param ort Ortschaft
     * @param landkreis Landkreis/Bezirk
     * @param breitengrad geografischer Breitengrad (kann null sein)
     * @param laengengrad geografischer Längengrad (kann null sein)
     * @param betreiber Betreiber der Anlage
     * @param bemerkungen weitere Bemerkungen
     */
    public Windkraftanlage(int objektId, String name, Integer baujahr, Double gesamtLeistungMW,
                           Integer anzahl, String typ, String ort, String landkreis,
                           Double breitengrad, Double laengengrad, String betreiber, String bemerkungen) {
        this.objektId = objektId;
        this.name = name;
        this.baujahr = baujahr;
        this.gesamtLeistungMW = gesamtLeistungMW;
        this.anzahl = anzahl;
        this.typ = typ;
        this.ort = ort;
        this.landkreis = landkreis;
        this.breitengrad = breitengrad;
        this.laengengrad = laengengrad;
        this.betreiber = betreiber;
        this.bemerkungen = bemerkungen;
    }

    public int getObjektId() {
        return objektId;
    }

    public void setObjektId(int objektId) {
        this.objektId = objektId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBaujahr() {
        return baujahr;
    }

    public void setBaujahr(Integer baujahr) {
        this.baujahr = baujahr;
    }

    public Double getGesamtLeistungMW() {
        return gesamtLeistungMW;
    }

    public void setGesamtLeistungMW(Double gesamtLeistungMW) {
        this.gesamtLeistungMW = gesamtLeistungMW;
    }

    public Integer getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(Integer anzahl) {
        this.anzahl = anzahl;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getLandkreis() {
        return landkreis;
    }

    public void setLandkreis(String landkreis) {
        this.landkreis = landkreis;
    }

    public Double getBreitengrad() {
        return breitengrad;
    }

    public void setBreitengrad(Double breitengrad) {
        this.breitengrad = breitengrad;
    }

    public Double getLaengengrad() {
        return laengengrad;
    }

    public void setLaengengrad(Double laengengrad) {
        this.laengengrad = laengengrad;
    }

    public String getBetreiber() {
        return betreiber;
    }

    public void setBetreiber(String betreiber) {
        this.betreiber = betreiber;
    }

    public String getBemerkungen() {
        return bemerkungen;
    }

    public void setBemerkungen(String bemerkungen) {
        this.bemerkungen = bemerkungen;
    }

    /**
     * Liefert eine aussagekräftige Textrepräsentation der Windkraftanlage.
     * Null-Werte werden als "unbekannt" dargestellt.
     *
     * Pre: keine
     * Post: Rückgabe nicht-null String mit allen Attributen
     *
     * @return formatierte Objektdarstellung
     */
    @Override
    public String toString() {
        return "Windkraftanlage{" +
                "objektId=" + objektId +
                ", name='" + name + '\'' +
                ", baujahr=" + FeldParser.formatiereFürAnzeige(baujahr) +
                ", gesamtLeistungMW=" + FeldParser.formatiereFürAnzeige(gesamtLeistungMW) +
                ", anzahl=" + FeldParser.formatiereFürAnzeige(anzahl) +
                ", typ='" + typ + '\'' +
                ", ort='" + ort + '\'' +
                ", landkreis='" + landkreis + '\'' +
                ", breitengrad=" + FeldParser.formatiereFürAnzeige(breitengrad) +
                ", laengengrad=" + FeldParser.formatiereFürAnzeige(laengengrad) +
                ", betreiber='" + betreiber + '\'' +
                ", bemerkungen='" + bemerkungen + '\'' +
                '}';
    }
}

