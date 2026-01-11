package util;

import model.Graphknoten;
import model.Windkraftanlage;

import java.util.ArrayList;
import java.util.List;

/**
 * Ungerichteter Graph zur Verwaltung von Windkraftanlagen-Beziehungen.
 * Implementiert mittels Adjazenzliste für effizienten Speicherverbrauch bei dünn besetzten Graphen.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Graph-Verwaltung und Kantenbildung
 * - KISS: Einfache ArrayList für Knoten, einfache Schleifen
 * - Modularization: Getrennte Klasse für Graph-Logik
 * 
 * Pre: -
 * Post: Graph verwaltet Knoten und Kanten basierend auf Distanz
 */
public class WindkraftanlageGraph {

    /** Liste aller Knoten im Graphen */
    private final List<Graphknoten> knoten;

    /** Maximale Distanz in km für Kantenbildung */
    private final float maxDistanzKm;

    /**
     * Erstellt einen neuen leeren Graphen.
     * 
     * Pre: maxDistanzKm > 0
     * Post: Leerer Graph ist erstellt
     * 
     * @param maxDistanzKm Maximale Distanz zwischen Anlagen für Kantenbildung
     */
    public WindkraftanlageGraph(float maxDistanzKm) {
        if (maxDistanzKm <= 0) {
            throw new IllegalArgumentException("Maximale Distanz muss positiv sein");
        }
        this.knoten = new ArrayList<>();
        this.maxDistanzKm = maxDistanzKm;
    }

    /**
     * Baut den Graphen aus einer Liste von Windkraftanlagen auf.
     * Erstellt für jede Anlage mit Koordinaten einen Knoten.
     * Verbindet Knoten mit Kanten, wenn Distanz <= maxDistanzKm.
     * 
     * Pre: anlagen darf nicht null sein
     * Post: Graph enthält Knoten und Kanten für alle Anlagen mit Koordinaten
     * 
     * @param anlagen Liste aller Windkraftanlagen
     */
    public void baueGraphAuf(List<Windkraftanlage> anlagen) {
        if (anlagen == null) {
            throw new IllegalArgumentException("Anlagen-Liste darf nicht null sein");
        }

        // Schritt 1: Erstelle Knoten für alle Anlagen mit Koordinaten
        erstelleKnoten(anlagen);

        // Schritt 2: Erstelle Kanten basierend auf Distanz
        erstelleKanten();
    }

    /**
     * Erstellt für jede Anlage mit gültigen Koordinaten einen Knoten.
     * 
     * Pre: anlagen darf nicht null sein
     * Post: knoten-Liste enthält Graphknoten für Anlagen mit Koordinaten
     * 
     * @param anlagen Liste aller Windkraftanlagen
     */
    private void erstelleKnoten(List<Windkraftanlage> anlagen) {
        for (int i = 0; i < anlagen.size(); i++) {
            Windkraftanlage anlage = anlagen.get(i);
            
            // Nur Anlagen mit Koordinaten werden als Knoten aufgenommen
            if (hatGueltigeKoordinaten(anlage)) {
                Graphknoten knoten = new Graphknoten(anlage);
                this.knoten.add(knoten);
            }
        }
    }

    /**
     * Prüft ob eine Anlage gültige Koordinaten hat.
     * 
     * Pre: anlage darf nicht null sein
     * Post: Rückgabe true wenn Breitengrad und Längengrad nicht null
     * 
     * @param anlage Die zu prüfende Anlage
     * @return true wenn Koordinaten vorhanden, sonst false
     */
    private boolean hatGueltigeKoordinaten(Windkraftanlage anlage) {
        return anlage.getBreitengrad() != null && anlage.getLaengengrad() != null;
    }

    /**
     * Erstellt Kanten zwischen allen Knotenpaaren, deren Distanz <= maxDistanzKm ist.
     * Verwendet einfache Doppelschleife für alle Paare (KISS-Prinzip).
     * 
     * Pre: knoten-Liste ist aufgebaut
     * Post: Alle Knoten haben Nachbarn im Distanzbereich
     */
    private void erstelleKanten() {
        int anzahlKnoten = knoten.size();

        // Prüfe jedes Knotenpaar
        for (int i = 0; i < anzahlKnoten; i++) {
            Graphknoten knoten1 = knoten.get(i);
            
            for (int j = i + 1; j < anzahlKnoten; j++) {
                Graphknoten knoten2 = knoten.get(j);
                
                // Berechne Distanz zwischen den Anlagen
                float distanz = berechneDistanz(knoten1, knoten2);
                
                // Füge Kante hinzu wenn Distanz im Bereich
                if (distanz <= maxDistanzKm) {
                    knoten1.fuegeNachbarHinzu(knoten2);
                    knoten2.fuegeNachbarHinzu(knoten1); // Ungerichteter Graph
                }
            }
        }
    }

    /**
     * Berechnet die Distanz zwischen zwei Graphknoten mittels Haversine-Formel.
     * 
     * Pre: Beide Knoten haben gültige Koordinaten
     * Post: Rückgabe ist Distanz in km
     * 
     * @param knoten1 Erster Knoten
     * @param knoten2 Zweiter Knoten
     * @return Distanz in Kilometern
     */
    private float berechneDistanz(Graphknoten knoten1, Graphknoten knoten2) {
        Windkraftanlage anlage1 = knoten1.getAnlage();
        Windkraftanlage anlage2 = knoten2.getAnlage();

        float breitengrad1 = anlage1.getBreitengrad() != null ? anlage1.getBreitengrad().floatValue() : 0f;
        float laengengrad1 = anlage1.getLaengengrad() != null ? anlage1.getLaengengrad().floatValue() : 0f;
        float breitengrad2 = anlage2.getBreitengrad() != null ? anlage2.getBreitengrad().floatValue() : 0f;
        float laengengrad2 = anlage2.getLaengengrad() != null ? anlage2.getLaengengrad().floatValue() : 0f;

        return DistanzBerechner.berechneDistanzKm(breitengrad1, laengengrad1,
                                                   breitengrad2, laengengrad2);
    }

    /**
     * Gibt alle Knoten des Graphen zurück.
     * 
     * Pre: -
     * Post: Rückgabe ist Liste aller Knoten
     * 
     * @return Liste aller Graphknoten
     */
    public List<Graphknoten> getKnoten() {
        return knoten;
    }

    /**
     * Gibt die Anzahl der Knoten im Graphen zurück.
     * 
     * Pre: -
     * Post: Rückgabe ist Anzahl der Knoten
     * 
     * @return Anzahl der Knoten
     */
    public int getAnzahlKnoten() {
        return knoten.size();
    }

    /**
     * Gibt die Anzahl der Kanten im Graphen zurück.
     * Jede Kante wird einmal gezählt (ungerichteter Graph).
     * 
     * Pre: -
     * Post: Rückgabe ist Anzahl der Kanten
     * 
     * @return Anzahl der Kanten
     */
    public int getAnzahlKanten() {
        int summeNachbarn = 0;
        
        for (int i = 0; i < knoten.size(); i++) {
            Graphknoten k = knoten.get(i);
            summeNachbarn = summeNachbarn + k.getAnzahlNachbarn();
        }
        
        // Jede Kante wird bei beiden Knoten gezählt, daher durch 2 teilen
        return summeNachbarn / 2;
    }

    /**
     * Gibt die maximale Distanz für Kantenbildung zurück.
     * 
     * Pre: -
     * Post: Rückgabe ist maximale Distanz in km
     * 
     * @return Maximale Distanz in km
     */
    public float getMaxDistanzKm() {
        return maxDistanzKm;
    }
}
