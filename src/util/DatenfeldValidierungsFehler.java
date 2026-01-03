package util;

/**
 * Immutable Datenklasse für die Speicherung von Fehlerinformationen bei der
 * Validierung und Korrektur von Datenfeldern (Koordinaten, Baujahr, Gesamtleistung).
 * 
 * Pre: Alle Parameter müssen bei Konstruktion gesetzt werden
 * Post: Die Werte sind unveränderlich und können via Getter abgerufen werden
 */
public class DatenfeldValidierungsFehler {
    private final Double ursprünglichBreitengrad;
    private final Double ursprünglichLängengrad;
    private final Integer ursprünglichBaujahr;
    private final Double ursprünglichGesamtLeistungMW;

    /**
     * Konstruktor für DatenfeldValidierungsFehler.
     * 
     * Pre: Mindestens ein Parameter sollte nicht null sein für sinnvolle Fehlererfassung
     * Post: Das Objekt speichert alle Fehlerwerte zur späteren Verarbeitung
     *
     * @param ursprünglichBreitengrad ursprünglicher Breitengrad (kann null sein)
     * @param ursprünglichLängengrad ursprünglicher Längengrad (kann null sein)
     * @param ursprünglichBaujahr ursprüngliches Baujahr (kann null sein)
     * @param ursprünglichGesamtLeistungMW ursprüngliche Gesamtleistung in MW (kann null sein)
     */
    public DatenfeldValidierungsFehler(
            Double ursprünglichBreitengrad,
            Double ursprünglichLängengrad,
            Integer ursprünglichBaujahr,
            Double ursprünglichGesamtLeistungMW) {
        this.ursprünglichBreitengrad = ursprünglichBreitengrad;
        this.ursprünglichLängengrad = ursprünglichLängengrad;
        this.ursprünglichBaujahr = ursprünglichBaujahr;
        this.ursprünglichGesamtLeistungMW = ursprünglichGesamtLeistungMW;
    }

    /**
     * @return ursprünglich gemeldeter Breitengrad (kann null sein)
     */
    public Double getUrsprünglichBreitengrad() {
        return ursprünglichBreitengrad;
    }

    /**
     * @return ursprünglich gemeldeter Längengrad (kann null sein)
     */
    public Double gibUrsprünglichLängengrad() {
        return ursprünglichLängengrad;
    }

    /**
     * @return ursprünglich gemeldetes Baujahr (kann null sein)
     */
    public Integer gibUrsprünglichBaujahr() {
        return ursprünglichBaujahr;
    }

    /**
     * @return ursprünglich gemeldete Gesamtleistung in MW (kann null sein)
     */
    public Double gibUrsprünglichGesamtLeistungMW() {
        return ursprünglichGesamtLeistungMW;
    }
}
