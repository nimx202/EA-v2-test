package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert einen Knoten im Windkraftanlagen-Graphen.
 * Jeder Knoten enthält eine Windkraftanlage und eine Liste seiner Nachbarn.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Verwaltet nur Knoten-Daten und Nachbarschaftsbeziehungen
 * - KISS: Einfache ArrayList für Nachbarn
 * - OOP: Private Felder mit Getter/Setter
 * 
 * Pre: Anlage darf nicht null sein
 * Post: Knoten verwaltet eine Anlage und ihre Nachbarn
 */
public class Graphknoten {

    /** Die Windkraftanlage, die dieser Knoten repräsentiert */
    private final Windkraftanlage anlage;

    /** Liste der Nachbarknoten (Adjazenzliste) */
    private final List<Graphknoten> nachbarn;

    /**
     * Erstellt einen neuen Graphknoten für eine Windkraftanlage.
     * 
     * Pre: anlage darf nicht null sein
     * Post: Knoten ist erstellt mit leerer Nachbarliste
     * 
     * @param anlage Die Windkraftanlage für diesen Knoten
     */
    public Graphknoten(Windkraftanlage anlage) {
        if (anlage == null) {
            throw new IllegalArgumentException("Anlage darf nicht null sein");
        }
        this.anlage = anlage;
        this.nachbarn = new ArrayList<>();
    }

    /**
     * Gibt die Windkraftanlage dieses Knotens zurück.
     * 
     * Pre: -
     * Post: Rückgabe ist die Anlage des Knotens
     * 
     * @return Die Windkraftanlage
     */
    public Windkraftanlage getAnlage() {
        return anlage;
    }

    /**
     * Gibt die Liste der Nachbarknoten zurück.
     * 
     * Pre: -
     * Post: Rückgabe ist die (möglicherweise leere) Liste der Nachbarn
     * 
     * @return Liste der Nachbarknoten
     */
    public List<Graphknoten> getNachbarn() {
        return nachbarn;
    }

    /**
     * Fügt einen Nachbarknoten hinzu.
     * 
     * Pre: nachbar darf nicht null sein
     * Post: nachbar ist zur Nachbarliste hinzugefügt
     * 
     * @param nachbar Der hinzuzufügende Nachbarknoten
     */
    public void fuegeNachbarHinzu(Graphknoten nachbar) {
        if (nachbar == null) {
            throw new IllegalArgumentException("Nachbar darf nicht null sein");
        }
        if (!nachbarn.contains(nachbar)) {
            nachbarn.add(nachbar);
        }
    }

    /**
     * Gibt die Anzahl der Nachbarn zurück.
     * 
     * Pre: -
     * Post: Rückgabe ist die Anzahl der Nachbarn
     * 
     * @return Anzahl der Nachbarn
     */
    public int getAnzahlNachbarn() {
        return nachbarn.size();
    }
}
