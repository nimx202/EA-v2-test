package util;

/**
 * Datenklasse zum Speichern von Informationen über fehlerhafte Daten.
 * Wird verwendet um Vorher-Nachher-Vergleich bei Korrekturen zu ermöglichen.
 * Speichert Fehler für Koordinaten, Baujahr und Gesamtleistung.
 *
 * Design: Immutable Value Object (alle Felder final)
 */
public class KoordinatenFehler {

    private final int objektId;
    private final String name;
    private final Double ursprünglicherBreitengrad;
    private final Double ursprünglicherLaengengrad;
    private final Integer ursprünglicherBaujahr;
    private final Double ursprünglicheGesamtLeistungMW;

    /**
     * Konstruktor für Fehlerinformation.
     *
     * Pre: objektId >= 0
     * Post: Alle Felder sind gesetzt
     *
     * @param objektId ID der Windkraftanlage
     * @param name Name der Anlage
     * @param ursprünglicherBreitengrad ursprünglicher Breitengrad (kann null sein)
     * @param ursprünglicherLaengengrad ursprünglicher Längengrad (kann null sein)
     * @param ursprünglicherBaujahr ursprüngliches Baujahr (kann null sein)
     * @param ursprünglicheGesamtLeistungMW ursprüngliche Gesamtleistung (kann null sein)
     */
    public KoordinatenFehler(int objektId, String name, Double ursprünglicherBreitengrad, 
                             Double ursprünglicherLaengengrad, Integer ursprünglicherBaujahr,
                             Double ursprünglicheGesamtLeistungMW) {
        this.objektId = objektId;
        this.name = name;
        this.ursprünglicherBreitengrad = ursprünglicherBreitengrad;
        this.ursprünglicherLaengengrad = ursprünglicherLaengengrad;
        this.ursprünglicherBaujahr = ursprünglicherBaujahr;
        this.ursprünglicheGesamtLeistungMW = ursprünglicheGesamtLeistungMW;
    }

    /**
     * @return Objekt-ID der betroffenen Anlage
     */
    public int getObjektId() {
        return objektId;
    }

    /**
     * @return Name der betroffenen Anlage
     */
    public String getName() {
        return name;
    }

    /**
     * @return ursprünglicher Breitengrad (kann null sein)
     */
    public Double getUrsprünglicherBreitengrad() {
        return ursprünglicherBreitengrad;
    }

    /**
     * @return ursprünglicher Längengrad (kann null sein)
     */
    public Double getUrsprünglicherLaengengrad() {
        return ursprünglicherLaengengrad;
    }

    /**
     * @return ursprüngliches Baujahr (kann null sein)
     */
    public Integer getUrsprünglicherBaujahr() {
        return ursprünglicherBaujahr;
    }

    /**
     * @return ursprünglich gemessene Gesamtleistung in MW (kann null sein)
     */
    public Double getUrsprünglicheGesamtLeistungMW() {
        return ursprünglicheGesamtLeistungMW;
    }
}
