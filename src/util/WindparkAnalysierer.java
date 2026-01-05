package util;

import model.Windkraftanlage;
import model.WindparkEintrag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility-Klasse für die Analyse von Windpark-Daten.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Windpark-Analysen
 * - KISS: Einfache Schleifen und Sortierung (kein Stream-API)
 * - Modularisierung: Trennung von Zählung und Sortierung
 * 
 * Verantwortlichkeiten:
 * - Zählen wie oft jeder Windpark vorkommt
 * - Sortieren der Windparks nach Häufigkeit
 * - Bereitstellen der Top N Windparks
 * 
 * Pre: Eingabelisten sind nicht null
 * Post: Liefert analysierte Windpark-Daten
 */
public final class WindparkAnalysierer {

    /**
     * Privater Konstruktor verhindert Instanziierung.
     * Dies ist eine Utility-Klasse mit nur statischen Methoden.
     */
    private WindparkAnalysierer() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Zählt wie oft jeder Windpark in der Liste vorkommt.
     * Windparks werden nach Namen gruppiert.
     * 
     * Pre: anlagen nicht null
     * Post: Rückgabe: Map mit Windpark-Namen und Anzahl
     * 
     * @param anlagen Liste aller Windkraftanlagen
     * @return Map mit Windpark-Namen als Schlüssel und Anzahl als Wert
     */
    public static Map<String, Integer> zaehleWindparks(List<Windkraftanlage> anlagen) {
        Map<String, Integer> windparkZaehler = new HashMap<>();
        
        for (Windkraftanlage anlage : anlagen) {
            String windparkName = anlage.getName();
            
            // Setze Platzhalter fuer unbekannte Namen
            if (windparkName == null) {
                windparkName = Konstanten.UNBEKANNTER_ORT;
            }
            
            // Hole aktuelle Anzahl oder 0 wenn noch nicht vorhanden
            Integer aktuelleAnzahl = windparkZaehler.get(windparkName);
            if (aktuelleAnzahl == null) {
                aktuelleAnzahl = 0;
            }
            
            // Erhoehe Zaehler um 1
            windparkZaehler.put(windparkName, aktuelleAnzahl + 1);
        }
        
        return windparkZaehler;
    }

    /**
     * Sortiert Windparks nach Anzahl absteigend.
     * Verwendet einfache Bubble-Sort Implementierung (KISS-Prinzip).
     * 
    * Pre: windparkZaehler nicht null
    * Post: Rückgabe: Sortierte Liste von WindparkEintrag (höchste Anzahl zuerst)
    * 
    * @param windparkZaehler Map mit Windpark-Namen und Anzahl
     * @return Sortierte Liste von WindparkEintrag-Objekten
     */
    public static List<WindparkEintrag> sortiereNachAnzahl(Map<String, Integer> windparkZaehler) {
        List<WindparkEintrag> liste = new ArrayList<>();
        
        // Konvertiere Map zu Liste
        for (Map.Entry<String, Integer> eintrag : windparkZaehler.entrySet()) {
            String name = eintrag.getKey();
            int anzahl = eintrag.getValue();
            liste.add(new WindparkEintrag(name, anzahl));
        }
        
        // Einfache Bubble-Sort Sortierung (absteigend nach Anzahl)
        int listenGroesse = liste.size();
        for (int i = 0; i < listenGroesse - 1; i++) {
            for (int j = i + 1; j < listenGroesse; j++) {
                WindparkEintrag eintragI = liste.get(i);
                WindparkEintrag eintragJ = liste.get(j);
                
                // Tausche wenn j größere Anzahl hat als i
                if (eintragJ.getAnzahl() > eintragI.getAnzahl()) {
                    liste.set(i, eintragJ);
                    liste.set(j, eintragI);
                }
            }
        }
        
        return liste;
    }

    /**
     * Gibt die Top N Windparks nach Anzahl zurück.
     * 
     * Pre: anlagen nicht null; topAnzahl >= 0
     * Post: Rückgabe: Liste mit maximal topAnzahl Einträgen
     * 
     * @param anlagen Liste aller Windkraftanlagen
     * @param topAnzahl Anzahl der gewünschten Top-Einträge
     * @return Liste der Top N Windparks
     */
    public static List<WindparkEintrag> holeTopWindparks(List<Windkraftanlage> anlagen, int topAnzahl) {
        // Zähle alle Windparks
        Map<String, Integer> windparkZaehler = zaehleWindparks(anlagen);
        
        // Sortiere nach Anzahl
        List<WindparkEintrag> sortierteListe = sortiereNachAnzahl(windparkZaehler);
        
        // Schneide auf gewünschte Anzahl zu
        int tatsaechlicheAnzahl = Math.min(topAnzahl, sortierteListe.size());
        List<WindparkEintrag> topListe = new ArrayList<>();

        for (int i = 0; i < tatsaechlicheAnzahl; i++) {
            topListe.add(sortierteListe.get(i));
        }
        
        return topListe;
    }
}
