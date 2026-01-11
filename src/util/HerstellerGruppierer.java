package util;

import model.Windkraftanlage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility-Klasse zum Gruppieren von Windkraftanlagen nach Hersteller.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Hersteller-Gruppierung
 * - KISS: Einfache HashMap und for-Schleifen (kein Stream-API)
 * - Modularisierung: Getrennte Utility-Klasse
 * 
 * Pre: Eingabelisten sind nicht null
 * Post: Liefert gruppierte und sortierte Hersteller-Daten
 */
public final class HerstellerGruppierer {

    /**
     * Privater Konstruktor verhindert Instanziierung der Utility-Klasse.
     */
    private HerstellerGruppierer() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Gruppiert Windkraftanlagen nach Hersteller.
     * Nur Anlagen mit gueltigen Koordinaten werden beruecksichtigt.
     * 
     * Pre: anlagen darf nicht null sein
     * Post: Rueckgabe ist Map mit Hersteller als Schluessel und Liste der Anlagen als Wert
     * 
     * @param anlagen Liste aller Windkraftanlagen
     * @return Map mit Hersteller -> Liste von Anlagen
     */
    public static Map<String, List<Windkraftanlage>> gruppiereNachHersteller(List<Windkraftanlage> anlagen) {
        Map<String, List<Windkraftanlage>> herstellerGruppen = new HashMap<>();
        
        for (int i = 0; i < anlagen.size(); i++) {
            Windkraftanlage anlage = anlagen.get(i);
            
            // Nur Anlagen mit gueltigen Koordinaten beruecksichtigen
            if (!hatGueltigeKoordinaten(anlage)) {
                continue;
            }
            
            // Extrahiere Hersteller
            String hersteller = HerstellerExtraktor.extrahiereHersteller(anlage);
            
            // Hole oder erstelle Liste fuer diesen Hersteller
            List<Windkraftanlage> herstellerAnlagen = herstellerGruppen.get(hersteller);
            if (herstellerAnlagen == null) {
                herstellerAnlagen = new ArrayList<>();
                herstellerGruppen.put(hersteller, herstellerAnlagen);
            }
            
            // Fuege Anlage zur Liste hinzu
            herstellerAnlagen.add(anlage);
        }
        
        return herstellerGruppen;
    }

    /**
     * Zaehlt die Anzahl der Anlagen pro Hersteller.
     * 
     * Pre: anlagen darf nicht null sein
     * Post: Rueckgabe ist Map mit Hersteller als Schluessel und Anzahl als Wert
     * 
     * @param anlagen Liste aller Windkraftanlagen
     * @return Map mit Hersteller -> Anzahl
     */
    public static Map<String, Integer> zaehleAnlagenProHersteller(List<Windkraftanlage> anlagen) {
        Map<String, Integer> herstellerZaehler = new HashMap<>();
        
        for (int i = 0; i < anlagen.size(); i++) {
            Windkraftanlage anlage = anlagen.get(i);
            
            // Nur Anlagen mit gueltigen Koordinaten zaehlen
            if (!hatGueltigeKoordinaten(anlage)) {
                continue;
            }
            
            String hersteller = HerstellerExtraktor.extrahiereHersteller(anlage);
            
            Integer aktuelleAnzahl = herstellerZaehler.get(hersteller);
            if (aktuelleAnzahl == null) {
                aktuelleAnzahl = 0;
            }
            herstellerZaehler.put(hersteller, aktuelleAnzahl + 1);
        }
        
        return herstellerZaehler;
    }

    /**
     * Sortiert Hersteller nach Anzahl absteigend und gibt Top N zurueck.
     * Verwendet einfache Bubble-Sort Implementierung (KISS-Prinzip).
     * 
     * Pre: herstellerZaehler und anzahl >= 0
     * Post: Rueckgabe ist Liste der Top N Hersteller-Namen (absteigend sortiert)
     * 
     * @param herstellerZaehler Map mit Hersteller -> Anzahl
     * @param anzahl Anzahl der gewuenschten Top-Hersteller
     * @return Liste der Top N Hersteller-Namen
     */
    public static List<String> holeTopHersteller(Map<String, Integer> herstellerZaehler, int anzahl) {
        // Konvertiere Map zu Listen fuer einfache Sortierung
        List<String> herstellerNamen = new ArrayList<>();
        List<Integer> herstellerAnzahlen = new ArrayList<>();
        
        for (Map.Entry<String, Integer> eintrag : herstellerZaehler.entrySet()) {
            herstellerNamen.add(eintrag.getKey());
            herstellerAnzahlen.add(eintrag.getValue());
        }
        
        // Bubble-Sort: Sortiere absteigend nach Anzahl
        int listenGroesse = herstellerNamen.size();
        for (int i = 0; i < listenGroesse - 1; i++) {
            for (int j = i + 1; j < listenGroesse; j++) {
                // Tausche wenn j groessere Anzahl hat als i
                if (herstellerAnzahlen.get(j) > herstellerAnzahlen.get(i)) {
                    // Tausche Namen
                    String tempName = herstellerNamen.get(i);
                    herstellerNamen.set(i, herstellerNamen.get(j));
                    herstellerNamen.set(j, tempName);
                    
                    // Tausche Anzahlen
                    Integer tempAnzahl = herstellerAnzahlen.get(i);
                    herstellerAnzahlen.set(i, herstellerAnzahlen.get(j));
                    herstellerAnzahlen.set(j, tempAnzahl);
                }
            }
        }
        
        // Schneide auf gewuenschte Anzahl zu
        int tatsaechlicheAnzahl = Math.min(anzahl, listenGroesse);
        List<String> topHersteller = new ArrayList<>();
        
        for (int i = 0; i < tatsaechlicheAnzahl; i++) {
            topHersteller.add(herstellerNamen.get(i));
        }
        
        return topHersteller;
    }

    /**
     * Prueft ob eine Anlage gueltige Koordinaten hat.
     * 
     * Pre: anlage darf nicht null sein
     * Post: Rueckgabe true wenn Breitengrad und Laengengrad nicht null
     * 
     * @param anlage Die zu pruefende Anlage
     * @return true wenn Koordinaten vorhanden, sonst false
     */
    private static boolean hatGueltigeKoordinaten(Windkraftanlage anlage) {
        if (anlage == null) {
            return false;
        }
        return anlage.getBreitengrad() != null && anlage.getLaengengrad() != null;
    }
}
