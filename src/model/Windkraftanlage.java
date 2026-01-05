package model;

import util.FeldParser;
import util.Konstanten;

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
<<<<<<< HEAD
    private Integer baujahr;
    private Float gesamtLeistungMW;
=======
    private String baujahr;
    private Float gesamtLeistungMW;
    private Integer anzahl;
    private String typ;
    private String ort;
    private String landkreis;
    private GeoKoordinaten geoKoordinaten;
    private String betreiber;
    private String bemerkungen;

    /**
     * Konstruktor ohne Argumente für Bean-Pattern.
     *
     * Post: Alle numerischen Felder haben Default-Wert 0/null
     */
    public Windkraftanlage() {
        this.geoKoordinaten = new GeoKoordinaten();
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
    * @param geoKoordinaten Objekt mit Breitengrad/Längengrad (kann null sein)
     * @param betreiber Betreiber der Anlage
     * @param bemerkungen weitere Bemerkungen
     */
    public Windkraftanlage(int objektId, String name, String baujahr, Float gesamtLeistungMW,
                           Integer anzahl, String typ, String ort, String landkreis,
                           GeoKoordinaten geoKoordinaten, String betreiber, String bemerkungen) {
        this.objektId = objektId;
        this.name = name;
        this.baujahr = baujahr;
        this.gesamtLeistungMW = gesamtLeistungMW;
        this.anzahl = anzahl;
        this.typ = typ;
        this.ort = ort;
        this.landkreis = landkreis;
        if (geoKoordinaten == null) {
            this.geoKoordinaten = new GeoKoordinaten();
        } else {
            this.geoKoordinaten = geoKoordinaten;
        }
        this.betreiber = betreiber;
        this.bemerkungen = bemerkungen;
    }

    /**
     * @return Kapselung der geografischen Koordinaten
     */
    public GeoKoordinaten getGeoKoordinaten() {
        return geoKoordinaten;
    }

    /**
     * @param geoKoordinaten Kapselung der geografischen Koordinaten
     */
    public void setGeoKoordinaten(GeoKoordinaten geoKoordinaten) {
        if (geoKoordinaten == null) {
            this.geoKoordinaten = new GeoKoordinaten();
        } else {
            this.geoKoordinaten = geoKoordinaten;
        }
    }

    /**
     * @return eindeutige Objekt-ID
     */
    public int getObjektId() {
        return objektId;
    }

    /**
     * @param objektId eindeutige Objekt-ID
     */
    public void setObjektId(int objektId) {
        this.objektId = objektId;
    }

    /**
     * @return Name der Anlage
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Name der Anlage
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Baujahr (kann null sein)
     */
    public String getBaujahr() {
        return baujahr;
    }

    /**
     * @param baujahr Baujahr (kann null sein)
     */
    public void setBaujahr(String baujahr) {
        this.baujahr = baujahr;
    }

    /**
     * @return Gesamtleistung in MW (kann null sein)
     */
    public Float getGesamtLeistungMW() {
        return gesamtLeistungMW;
    }

    /**
     * @param gesamtLeistungMW Gesamtleistung in MW (kann null sein)
     */
    public void setGesamtLeistungMW(Float gesamtLeistungMW) {
        this.gesamtLeistungMW = gesamtLeistungMW;
    }

    /**
     * @return Anzahl der Einheiten (kann null sein)
     */
    public Integer getAnzahl() {
        return anzahl;
    }

    /**
     * @param anzahl Anzahl der Einheiten (kann null sein)
     */
    public void setAnzahl(Integer anzahl) {
        this.anzahl = anzahl;
    }

    /**
     * @return Typ der Anlage
     */
    public String getTyp() {
        return typ;
    }

    /**
     * @param typ Typ der Anlage
     */
    public void setTyp(String typ) {
        this.typ = typ;
    }

    /**
     * @return Ortschaft der Anlage
     */
    public String getOrt() {
        return ort;
    }

    /**
     * @param ort Ortschaft der Anlage
     */
    public void setOrt(String ort) {
        this.ort = ort;
    }

    /**
     * @return Landkreis/Bezirk
     */
    public String getLandkreis() {
        return landkreis;
    }

    /**
     * @param landkreis Landkreis/Bezirk
     */
    public void setLandkreis(String landkreis) {
        this.landkreis = landkreis;
    }

    /**
     * @return Breitengrad (kann null sein)
     */
    public Float getBreitengrad() {
        if (geoKoordinaten == null) {
            return null;
        }
        return geoKoordinaten.getBreitengrad();
    }

    /**
     * @param breitengrad Breitengrad (kann null sein)
     */
    public void setBreitengrad(Float breitengrad) {
        stelleGeoKoordinatenSicher().setBreitengrad(breitengrad);
    }

    /**
     * @return Längengrad (kann null sein)
     */
    public Float getLaengengrad() {
        if (geoKoordinaten == null) {
            return null;
        }
        return geoKoordinaten.getLaengengrad();
    }

    /**
     * @param laengengrad Längengrad (kann null sein)
     */
    public void setLaengengrad(Float laengengrad) {
        stelleGeoKoordinatenSicher().setLaengengrad(laengengrad);
    }

    /**
     * Prüft, ob Koordinaten vollständig gesetzt sind.
     *
     * Pre: keine
     * Post: Rückgabe true nur bei vorhandenen Koordinatenwerten
     *
     * @return true wenn Breitengrad und Längengrad gesetzt sind
     */
    public boolean hatKoordinaten() {
        if (geoKoordinaten == null) {
            return false;
        }
        return geoKoordinaten.hatBeideKoordinaten();
    }

    /**
     * Stellt sicher, dass eine GeoKoordinaten-Instanz vorhanden ist.
     *
     * @return nie null
     */
    private GeoKoordinaten stelleGeoKoordinatenSicher() {
        if (geoKoordinaten == null) {
            geoKoordinaten = new GeoKoordinaten();
        }
        return geoKoordinaten;
    }

    /**
     * @return Betreiber
     */
    public String getBetreiber() {
        return betreiber;
    }

    /**
     * @param betreiber Betreiber
     */
    public void setBetreiber(String betreiber) {
        this.betreiber = betreiber;
    }

    /**
     * @return Bemerkungen
     */
    public String getBemerkungen() {
        return bemerkungen;
    }

    /**
     * @param bemerkungen Bemerkungen
     */
    public void setBemerkungen(String bemerkungen) {
        this.bemerkungen = bemerkungen;
    }

    /**
     * Liefert eine aussagekräftige Textrepräsentation der Windkraftanlage.
     * Null-Werte werden als "unbekannt" dargestellt.
     * Verwendet Konstanten für alle Textbausteine.
     *
     * Pre: keine
     * Post: Rückgabe nicht-null String mit allen Attributen
     *
     * @return formatierte Objektdarstellung
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        
        builder.append(Konstanten.TOSTRING_PREFIX);
        builder.append(Konstanten.FELD_OBJEKT_ID).append(Konstanten.TOSTRING_WERTTRENNER).append(objektId);
        builder.append(Konstanten.TOSTRING_FELDTRENNER);
        builder.append(Konstanten.FELD_NAME).append(Konstanten.TOSTRING_WERTTRENNER)
               .append(Konstanten.TOSTRING_QUOTE).append(name).append(Konstanten.TOSTRING_QUOTE);
        builder.append(Konstanten.TOSTRING_FELDTRENNER);
        builder.append(Konstanten.FELD_BAUJAHR).append(Konstanten.TOSTRING_WERTTRENNER)
               .append(FeldParser.formatiereFuerAnzeige(baujahr));
        builder.append(Konstanten.TOSTRING_FELDTRENNER);
        builder.append(Konstanten.FELD_GESAMT_LEISTUNG_MW).append(Konstanten.TOSTRING_WERTTRENNER)
               .append(FeldParser.formatiereFuerAnzeige(gesamtLeistungMW));
        builder.append(Konstanten.TOSTRING_FELDTRENNER);
        builder.append(Konstanten.FELD_ANZAHL).append(Konstanten.TOSTRING_WERTTRENNER)
               .append(FeldParser.formatiereFuerAnzeige(anzahl));
        builder.append(Konstanten.TOSTRING_FELDTRENNER);
        builder.append(Konstanten.FELD_TYP).append(Konstanten.TOSTRING_WERTTRENNER)
               .append(Konstanten.TOSTRING_QUOTE).append(typ).append(Konstanten.TOSTRING_QUOTE);
        builder.append(Konstanten.TOSTRING_FELDTRENNER);
        builder.append(Konstanten.FELD_ORT).append(Konstanten.TOSTRING_WERTTRENNER)
               .append(Konstanten.TOSTRING_QUOTE).append(ort).append(Konstanten.TOSTRING_QUOTE);
        builder.append(Konstanten.TOSTRING_FELDTRENNER);
        builder.append(Konstanten.FELD_LANDKREIS).append(Konstanten.TOSTRING_WERTTRENNER)
               .append(Konstanten.TOSTRING_QUOTE).append(landkreis).append(Konstanten.TOSTRING_QUOTE);
        builder.append(Konstanten.TOSTRING_FELDTRENNER);
        builder.append(Konstanten.FELD_BREITENGRAD).append(Konstanten.TOSTRING_WERTTRENNER)
<<<<<<< HEAD
               .append(FeldParser.formatiereFuerAnzeige(getBreitengrad()));
        builder.append(Konstanten.TOSTRING_FELDTRENNER);
        builder.append(Konstanten.FELD_LAENGENGRAD).append(Konstanten.TOSTRING_WERTTRENNER)
               .append(FeldParser.formatiereFuerAnzeige(getLaengengrad()));
=======
               .append(FeldParser.formatiereFuerAnzeige(breitengrad));
        builder.append(Konstanten.TOSTRING_FELDTRENNER);
        builder.append(Konstanten.FELD_LAENGENGRAD).append(Konstanten.TOSTRING_WERTTRENNER)
               .append(FeldParser.formatiereFuerAnzeige(laengengrad));
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
        builder.append(Konstanten.TOSTRING_FELDTRENNER);
        builder.append(Konstanten.FELD_BETREIBER).append(Konstanten.TOSTRING_WERTTRENNER)
               .append(Konstanten.TOSTRING_QUOTE).append(betreiber).append(Konstanten.TOSTRING_QUOTE);
        builder.append(Konstanten.TOSTRING_FELDTRENNER);
        builder.append(Konstanten.FELD_BEMERKUNGEN).append(Konstanten.TOSTRING_WERTTRENNER)
               .append(Konstanten.TOSTRING_QUOTE).append(bemerkungen).append(Konstanten.TOSTRING_QUOTE);
        builder.append(Konstanten.TOSTRING_SUFFIX);
        
        return builder.toString();
    }
}

