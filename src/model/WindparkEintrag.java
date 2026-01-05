package model;

/**
 * Modellklasse für einen Windpark-Eintrag mit Anzahl.
 * Speichert den Namen eines Windparks und wie oft er vorkommt.
 * 
 * Verwendung: Zum Sortieren und Anzeigen von Top-Windparks nach Häufigkeit.
 * 
 * Pre: name nicht null
 * Post: Objekt ist vollständig initialisiert und verwendbar
 */
public class WindparkEintrag {

    private String name;
    private int anzahl;

    /**
     * Erstellt einen neuen Windpark-Eintrag.
     * 
     * Pre: name nicht null; anzahl >= 0
     * Post: Objekt mit name und anzahl erstellt
     * 
     * @param name Name des Windparks
     * @param anzahl Anzahl der Vorkommen
     */
    public WindparkEintrag(String name, int anzahl) {
        this.name = name;
        this.anzahl = anzahl;
    }

    /**
     * Gibt den Namen des Windparks zurück.
     * 
     * Pre: keine
     * Post: Name wird zurückgegeben
     * 
     * @return Name des Windparks
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen des Windparks.
     * 
     * Pre: name nicht null
     * Post: Name wurde gesetzt
     * 
     * @param name Neuer Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt die Anzahl der Vorkommen zurück.
     * 
     * Pre: keine
     * Post: Anzahl wird zurückgegeben
     * 
     * @return Anzahl der Vorkommen
     */
    public int getAnzahl() {
        return anzahl;
    }

    /**
     * Setzt die Anzahl der Vorkommen.
     * 
     * Pre: anzahl >= 0
     * Post: Anzahl wurde gesetzt
     * 
     * @param anzahl Neue Anzahl
     */
    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }
}
