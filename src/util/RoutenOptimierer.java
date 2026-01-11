package util;

import model.Windkraftanlage;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility-Klasse zur Optimierung von Wartungsrouten.
 * Implementiert Nearest-Neighbor als Startloesung und 2-Opt zur Verbesserung.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Routen-Optimierung
 * - KISS: Einfache for-Schleifen, keine komplexen Algorithmen
 * - Modularisierung: Getrennte Utility-Klasse fuer Routing
 * 
 * Pre: Anlagen muessen gueltige Koordinaten haben
 * Post: Liefert optimierte Route als Liste von Anlagen
 */
public final class RoutenOptimierer {

    /**
     * Privater Konstruktor verhindert Instanziierung der Utility-Klasse.
     */
    private RoutenOptimierer() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Erstellt eine optimierte Route durch alle Anlagen.
     * Verwendet Nearest-Neighbor als Startloesung und verbessert mit 2-Opt.
     * Die erste Anlage in der Liste wird als Startpunkt verwendet.
     * 
     * Pre: anlagen darf nicht null oder leer sein, alle Anlagen brauchen Koordinaten
     * Post: Rueckgabe ist optimierte Route beginnend bei erster Anlage
     * 
     * @param anlagen Liste der zu besuchenden Anlagen
     * @return Optimierte Route als Liste von Anlagen
     */
    public static List<Windkraftanlage> erstelleOptimierteRoute(List<Windkraftanlage> anlagen) {
        if (anlagen == null || anlagen.isEmpty()) {
            return new ArrayList<>();
        }
        
        if (anlagen.size() == 1) {
            List<Windkraftanlage> route = new ArrayList<>();
            route.add(anlagen.get(0));
            return route;
        }
        
        // Schritt 1: Nearest-Neighbor Route erstellen (Start bei erster Anlage)
        List<Windkraftanlage> route = erstelleNearestNeighborRoute(anlagen);
        
        // Schritt 2: Route mit 2-Opt verbessern
        route = verbessereRouteMit2Opt(route);
        
        return route;
    }

    /**
     * Erstellt eine Route mit dem Nearest-Neighbor Algorithmus.
     * Beginnt bei der ersten Anlage und waehlt immer die naechste unbesuchte Anlage.
     * 
     * Pre: anlagen darf nicht null oder leer sein
     * Post: Rueckgabe ist Nearest-Neighbor Route
     * 
     * @param anlagen Liste der zu besuchenden Anlagen
     * @return Nearest-Neighbor Route als Liste
     */
    public static List<Windkraftanlage> erstelleNearestNeighborRoute(List<Windkraftanlage> anlagen) {
        List<Windkraftanlage> route = new ArrayList<>();
        boolean[] besucht = new boolean[anlagen.size()];
        
        // Starte bei erster Anlage (Index 0)
        int aktuellerIndex = 0;
        route.add(anlagen.get(aktuellerIndex));
        besucht[aktuellerIndex] = true;
        
        // Besuche alle weiteren Anlagen
        for (int schritt = 1; schritt < anlagen.size(); schritt++) {
            Windkraftanlage aktuelle = anlagen.get(aktuellerIndex);
            
            // Finde naechste unbesuchte Anlage
            int naechsterIndex = -1;
            float minDistanz = Float.MAX_VALUE;
            
            for (int i = 0; i < anlagen.size(); i++) {
                if (besucht[i]) {
                    continue;
                }
                
                Windkraftanlage kandidat = anlagen.get(i);
                float distanz = berechneDistanz(aktuelle, kandidat);
                
                if (distanz < minDistanz) {
                    minDistanz = distanz;
                    naechsterIndex = i;
                }
            }
            
            // Fuege naechste Anlage zur Route hinzu
            if (naechsterIndex >= 0) {
                route.add(anlagen.get(naechsterIndex));
                besucht[naechsterIndex] = true;
                aktuellerIndex = naechsterIndex;
            }
        }
        
        return route;
    }

    /**
     * Verbessert eine Route mit dem 2-Opt Algorithmus.
     * Tauscht Kanten um Kreuzungen zu entfernen und die Gesamtdistanz zu verkuerzen.
     * 
     * Pre: route darf nicht null sein
     * Post: Rueckgabe ist verbesserte Route (gleiche Anlagen, bessere Reihenfolge)
     * 
     * @param route Die zu verbessernde Route
     * @return Verbesserte Route
     */
    public static List<Windkraftanlage> verbessereRouteMit2Opt(List<Windkraftanlage> route) {
        if (route == null || route.size() < 4) {
            return route;
        }
        
        // Kopiere Route fuer Bearbeitung
        List<Windkraftanlage> besteRoute = new ArrayList<>(route);
        int routenGroesse = besteRoute.size();
        
        boolean verbessert = true;
        
        // Wiederhole bis keine Verbesserung mehr moeglich
        while (verbessert) {
            verbessert = false;
            
            // Pruefe alle moeglichen 2-Opt Tausche
            for (int i = 0; i < routenGroesse - 2; i++) {
                for (int j = i + 2; j < routenGroesse; j++) {
                    // Berechne aktuelle Distanz der zwei Kanten
                    float aktuelleDistanz = berechneKantenDistanz(besteRoute, i, j);
                    
                    // Berechne neue Distanz nach 2-Opt Tausch
                    float neueDistanz = berechneNeueKantenDistanz(besteRoute, i, j);
                    
                    // Wenn neue Distanz kuerzer, fuehre Tausch durch
                    if (neueDistanz < aktuelleDistanz) {
                        besteRoute = fuehre2OptTauschDurch(besteRoute, i, j);
                        verbessert = true;
                    }
                }
            }
        }
        
        return besteRoute;
    }

    /**
     * Berechnet die Distanz der zwei aktuellen Kanten bei Index i und j.
     * Kanten: (i, i+1) und (j, j+1 mod n)
     * 
     * Pre: route nicht null, 0 <= i < j < route.size()
     * Post: Rueckgabe ist Summe der zwei Kantendistanzen
     * 
     * @param route Die Route
     * @param i Index der ersten Kante
     * @param j Index der zweiten Kante
     * @return Summe der Kantendistanzen
     */
    private static float berechneKantenDistanz(List<Windkraftanlage> route, int i, int j) {
        int n = route.size();
        int jPlus1 = (j + 1) % n;
        
        // Falls j der letzte Index ist, gibt es keine Kante (j, j+1)
        if (j == n - 1) {
            // Nur Kante (i, i+1)
            return berechneDistanz(route.get(i), route.get(i + 1));
        }
        
        // Distanz Kante (i, i+1) + Kante (j, j+1)
        float distanz1 = berechneDistanz(route.get(i), route.get(i + 1));
        float distanz2 = berechneDistanz(route.get(j), route.get(jPlus1));
        
        return distanz1 + distanz2;
    }

    /**
     * Berechnet die Distanz der neuen Kanten nach einem 2-Opt Tausch.
     * Neue Kanten: (i, j) und (i+1, j+1 mod n)
     * 
     * Pre: route nicht null, 0 <= i < j < route.size()
     * Post: Rueckgabe ist Summe der zwei neuen Kantendistanzen
     * 
     * @param route Die Route
     * @param i Index der ersten Kante
     * @param j Index der zweiten Kante
     * @return Summe der neuen Kantendistanzen
     */
    private static float berechneNeueKantenDistanz(List<Windkraftanlage> route, int i, int j) {
        int n = route.size();
        int jPlus1 = (j + 1) % n;
        
        // Falls j der letzte Index ist
        if (j == n - 1) {
            return berechneDistanz(route.get(i), route.get(j));
        }
        
        // Distanz neue Kanten: (i, j) und (i+1, j+1)
        float distanz1 = berechneDistanz(route.get(i), route.get(j));
        float distanz2 = berechneDistanz(route.get(i + 1), route.get(jPlus1));
        
        return distanz1 + distanz2;
    }

    /**
     * Fuehrt einen 2-Opt Tausch durch: Kehrt das Segment zwischen i+1 und j um.
     * 
     * Pre: route nicht null, 0 <= i < j < route.size()
     * Post: Rueckgabe ist neue Route mit umgekehrtem Segment
     * 
     * @param route Die Route
     * @param i Startindex (exklusiv)
     * @param j Endindex (inklusiv)
     * @return Neue Route nach 2-Opt Tausch
     */
    private static List<Windkraftanlage> fuehre2OptTauschDurch(List<Windkraftanlage> route, int i, int j) {
        List<Windkraftanlage> neueRoute = new ArrayList<>();
        
        // Kopiere Elemente 0 bis i (inklusive)
        for (int k = 0; k <= i; k++) {
            neueRoute.add(route.get(k));
        }
        
        // Fuege Elemente i+1 bis j in umgekehrter Reihenfolge hinzu
        for (int k = j; k > i; k--) {
            neueRoute.add(route.get(k));
        }
        
        // Kopiere Elemente j+1 bis Ende
        for (int k = j + 1; k < route.size(); k++) {
            neueRoute.add(route.get(k));
        }
        
        return neueRoute;
    }

    /**
     * Berechnet die Gesamtdistanz einer Route.
     * 
     * Pre: route darf nicht null sein
     * Post: Rueckgabe ist Summe aller Kantendistanzen
     * 
     * @param route Die Route
     * @return Gesamtdistanz in km
     */
    public static float berechneGesamtDistanz(List<Windkraftanlage> route) {
        if (route == null || route.size() < 2) {
            return 0.0f;
        }
        
        float gesamtDistanz = 0.0f;
        
        for (int i = 0; i < route.size() - 1; i++) {
            gesamtDistanz += berechneDistanz(route.get(i), route.get(i + 1));
        }
        
        return gesamtDistanz;
    }

    /**
     * Berechnet die Distanz zwischen zwei Anlagen.
     * 
     * Pre: beide Anlagen muessen gueltige Koordinaten haben
     * Post: Rueckgabe ist Distanz in km
     * 
     * @param anlage1 Erste Anlage
     * @param anlage2 Zweite Anlage
     * @return Distanz in km
     */
    public static float berechneDistanz(Windkraftanlage anlage1, Windkraftanlage anlage2) {
        Float lat1 = anlage1.getBreitengrad();
        Float lon1 = anlage1.getLaengengrad();
        Float lat2 = anlage2.getBreitengrad();
        Float lon2 = anlage2.getLaengengrad();
        
        if (lat1 == null || lon1 == null || lat2 == null || lon2 == null) {
            return Float.MAX_VALUE;
        }
        
        return DistanzBerechner.berechneDistanzKm(lat1, lon1, lat2, lon2);
    }
}
