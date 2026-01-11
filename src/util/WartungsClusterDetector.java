package util;

import model.Graphknoten;
import model.Windkraftanlage;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility-Klasse zur Erkennung von isolierten Clustern in einem Wartungsgraphen.
 * Verwendet einfache Tiefensuche (DFS) zur Identifikation zusammenhaengender Komponenten.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Cluster-Erkennung
 * - KISS: Einfache Tiefensuche mit boolean-Array
 * - Modularisierung: Getrennte Utility-Klasse
 * 
 * Pre: Graph muss aufgebaut sein
 * Post: Liefert Liste von zusammenhaengenden Cluster-Listen
 */
public final class WartungsClusterDetector {

    /**
     * Privater Konstruktor verhindert Instanziierung der Utility-Klasse.
     */
    private WartungsClusterDetector() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Erkennt alle zusammenhaengenden Cluster im Graphen.
     * Jeder Cluster ist eine Liste von Anlagen, die untereinander erreichbar sind.
     * 
     * Pre: graph darf nicht null sein
     * Post: Rueckgabe ist Liste von Clustern (jeder Cluster ist Liste von Anlagen)
     * 
     * @param graph Der Windkraftanlagen-Graph
     * @return Liste von Clustern
     */
    public static List<List<Windkraftanlage>> erkenneClusters(WindkraftanlageGraph graph) {
        List<List<Windkraftanlage>> clusters = new ArrayList<>();
        
        if (graph == null) {
            return clusters;
        }
        
        List<Graphknoten> alleKnoten = graph.getKnoten();
        int anzahlKnoten = alleKnoten.size();
        
        if (anzahlKnoten == 0) {
            return clusters;
        }
        
        // Markiere alle Knoten als unbesucht
        boolean[] besucht = new boolean[anzahlKnoten];
        
        // Durchlaufe alle Knoten
        for (int i = 0; i < anzahlKnoten; i++) {
            // Wenn Knoten noch nicht besucht, starte neuen Cluster
            if (!besucht[i]) {
                List<Windkraftanlage> cluster = new ArrayList<>();
                
                // Tiefensuche vom aktuellen Knoten
                tiefensucheSammle(alleKnoten, i, besucht, cluster);
                
                // Fuege Cluster zur Ergebnisliste hinzu
                clusters.add(cluster);
            }
        }
        
        return clusters;
    }

    /**
     * Fuehrt Tiefensuche durch und sammelt alle erreichbaren Anlagen in einem Cluster.
     * 
     * Pre: alleKnoten, besucht und cluster nicht null; startIndex gueltig
     * Post: Alle vom Start erreichbaren Knoten sind in cluster und als besucht markiert
     * 
     * @param alleKnoten Liste aller Graphknoten
     * @param startIndex Index des Startknotens
     * @param besucht Array zum Markieren besuchter Knoten
     * @param cluster Liste zum Sammeln der Cluster-Anlagen
     */
    private static void tiefensucheSammle(List<Graphknoten> alleKnoten, int startIndex,
                                          boolean[] besucht, List<Windkraftanlage> cluster) {
        // Verwende eigene Stack-Simulation statt Rekursion (KISS und Overflow-sicher)
        List<Integer> zuBesuchen = new ArrayList<>();
        zuBesuchen.add(startIndex);
        
        while (!zuBesuchen.isEmpty()) {
            // Hole letztes Element (Stack-Verhalten)
            int letzterIndex = zuBesuchen.size() - 1;
            int aktuellerIndex = zuBesuchen.get(letzterIndex);
            zuBesuchen.remove(letzterIndex);
            
            // Ueberspringe wenn bereits besucht
            if (besucht[aktuellerIndex]) {
                continue;
            }
            
            // Markiere als besucht
            besucht[aktuellerIndex] = true;
            
            // Fuege Anlage zum Cluster hinzu
            Graphknoten knoten = alleKnoten.get(aktuellerIndex);
            cluster.add(knoten.getAnlage());
            
            // Fuege alle unbesuchten Nachbarn zur Stack-Liste hinzu
            List<Graphknoten> nachbarn = knoten.getNachbarn();
            for (int n = 0; n < nachbarn.size(); n++) {
                Graphknoten nachbar = nachbarn.get(n);
                
                // Finde Index des Nachbarn in alleKnoten
                int nachbarIndex = findeKnotenIndex(alleKnoten, nachbar);
                
                if (nachbarIndex >= 0 && !besucht[nachbarIndex]) {
                    zuBesuchen.add(nachbarIndex);
                }
            }
        }
    }

    /**
     * Findet den Index eines Knotens in der Knotenliste.
     * 
     * Pre: alleKnoten und gesuchterKnoten nicht null
     * Post: Rueckgabe ist Index oder -1 wenn nicht gefunden
     * 
     * @param alleKnoten Liste aller Knoten
     * @param gesuchterKnoten Der zu findende Knoten
     * @return Index des Knotens oder -1
     */
    private static int findeKnotenIndex(List<Graphknoten> alleKnoten, Graphknoten gesuchterKnoten) {
        for (int i = 0; i < alleKnoten.size(); i++) {
            if (alleKnoten.get(i) == gesuchterKnoten) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Erkennt Cluster direkt aus einer Liste von Anlagen ohne vorgebauten Graphen.
     * Baut intern einen Graphen mit dem Wartungs-Transportlimit auf.
     * 
     * Pre: anlagen darf nicht null sein
     * Post: Rueckgabe ist Liste von Clustern
     * 
     * @param anlagen Liste der Windkraftanlagen
     * @return Liste von Clustern
     */
    public static List<List<Windkraftanlage>> erkenneClustersAusAnlagen(List<Windkraftanlage> anlagen) {
        if (anlagen == null || anlagen.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Baue Graphen mit Wartungs-Transportlimit
        WindkraftanlageGraph graph = new WindkraftanlageGraph(Konstanten.WARTUNG_GRAPH_MAX_DISTANZ_KM);
        graph.baueGraphAuf(anlagen);
        
        // Erkenne Cluster
        return erkenneClusters(graph);
    }

    /**
     * Prueft ob mehrere Cluster vorhanden sind (d.h. isolierte Bereiche existieren).
     * 
     * Pre: clusters nicht null
     * Post: Rueckgabe true wenn mehr als ein Cluster
     * 
     * @param clusters Liste der Cluster
     * @return true wenn isolierte Bereiche existieren
     */
    public static boolean hatIsolierteBereiche(List<List<Windkraftanlage>> clusters) {
        return clusters != null && clusters.size() > 1;
    }

    /**
     * Berechnet die minimale Distanz zwischen zwei Clustern.
     * 
     * Pre: cluster1 und cluster2 nicht null und nicht leer
     * Post: Rueckgabe ist minimale Distanz in km
     * 
     * @param cluster1 Erster Cluster
     * @param cluster2 Zweiter Cluster
     * @return Minimale Distanz zwischen den Clustern in km
     */
    public static float berechneClusterDistanz(List<Windkraftanlage> cluster1, 
                                                List<Windkraftanlage> cluster2) {
        if (cluster1 == null || cluster2 == null || 
            cluster1.isEmpty() || cluster2.isEmpty()) {
            return Float.MAX_VALUE;
        }
        
        float minDistanz = Float.MAX_VALUE;
        
        for (int i = 0; i < cluster1.size(); i++) {
            Windkraftanlage anlage1 = cluster1.get(i);
            
            for (int j = 0; j < cluster2.size(); j++) {
                Windkraftanlage anlage2 = cluster2.get(j);
                
                float distanz = RoutenOptimierer.berechneDistanz(anlage1, anlage2);
                
                if (distanz < minDistanz) {
                    minDistanz = distanz;
                }
            }
        }
        
        return minDistanz;
    }
}
