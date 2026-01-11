package util;

import model.Windkraftanlage;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility-Klasse zur Berechnung der Wartungszeit fuer eine Route.
 * Beruecksichtigt Arbeitszeit (8:00-16:00), Wartungsdauer (2h/Anlage) und Transportzeit (16:00-18:00).
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Zeitberechnung fuer Wartung
 * - KISS: Einfache Division und Schleifen
 * - Modularisierung: Getrennte Utility-Klasse
 * 
 * Pre: Route muss gueltige Anlagen enthalten
 * Post: Liefert Wartungsdauer in Tagen und Details
 */
public final class WartungsZeitBerechner {

    /**
     * Privater Konstruktor verhindert Instanziierung der Utility-Klasse.
     */
    private WartungsZeitBerechner() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Berechnet die Anzahl der benoeligten Wartungstage fuer eine Route.
     * 
     * Arbeitsmodell:
     * - 8:00 - 16:00: Wartung (8 Stunden = 4 Anlagen bei 2h/Anlage)
     * - 16:00 - 18:00: Transport zur naechsten Anlage (max 120 km bei 60 km/h)
     * 
     * Pre: route darf nicht null sein
     * Post: Rueckgabe ist Anzahl der benoeligten Tage (mindestens 1 bei nicht-leerer Route)
     * 
     * @param route Die Wartungsroute
     * @return Anzahl der benoeligten Wartungstage
     */
    public static int berechneWartungstage(List<Windkraftanlage> route) {
        if (route == null || route.isEmpty()) {
            return 0;
        }
        
        int anzahlAnlagen = route.size();
        
        // Berechne Basis-Tage (Anlagen / Anlagen pro Tag, aufgerundet)
        return (anzahlAnlagen + Konstanten.ANLAGEN_PRO_TAG - 1) / Konstanten.ANLAGEN_PRO_TAG;
    }

    /**
     * Erstellt einen detaillierten Arbeitsplan fuer eine Route.
     * Jeder Tag enthaelt die IDs der zu wartenden Anlagen.
     * 
     * Pre: route darf nicht null sein
     * Post: Rueckgabe ist Liste von Tages-Listen mit Anlagen-IDs
     * 
     * @param route Die Wartungsroute
     * @return Liste von Arbeitstagen mit Anlagen-IDs pro Tag
     */
    public static List<List<Integer>> erstelleArbeitsplan(List<Windkraftanlage> route) {
        List<List<Integer>> arbeitsplan = new ArrayList<>();
        
        if (route == null || route.isEmpty()) {
            return arbeitsplan;
        }
        
        int anlagenIndex = 0;
        
        // Verarbeite alle Anlagen
        while (anlagenIndex < route.size()) {
            List<Integer> tagesAnlagen = new ArrayList<>();
            
            // Fuege bis zu ANLAGEN_PRO_TAG Anlagen zum aktuellen Tag hinzu
            int anlagenAmTag = 0;
            while (anlagenIndex < route.size() && anlagenAmTag < Konstanten.ANLAGEN_PRO_TAG) {
                Windkraftanlage anlage = route.get(anlagenIndex);
                tagesAnlagen.add(anlage.getObjektId());
                anlagenIndex++;
                anlagenAmTag++;
            }
            
            arbeitsplan.add(tagesAnlagen);
        }
        
        return arbeitsplan;
    }

    /**
     * Prueft Transportdistanzen und gibt Warnungen aus wenn Limit ueberschritten.
     * 
     * Pre: route darf nicht null sein
     * Post: Gibt Warnungen fuer Strecken > MAX_TRANSPORT_DISTANZ_KM aus
     * 
     * @param route Die Wartungsroute
     * @return Liste von Warnungs-Strings (leer wenn keine Warnungen)
     */
    public static List<String> pruefeTransportDistanzen(List<Windkraftanlage> route) {
        List<String> warnungen = new ArrayList<>();
        
        if (route == null || route.size() < 2) {
            return warnungen;
        }
        
        // Pruefe jede Strecke zwischen aufeinanderfolgenden Anlagen
        for (int i = 0; i < route.size() - 1; i++) {
            Windkraftanlage von = route.get(i);
            Windkraftanlage nach = route.get(i + 1);
            
            float distanz = RoutenOptimierer.berechneDistanz(von, nach);
            
            if (distanz > Konstanten.MAX_TRANSPORT_DISTANZ_KM) {
                String warnung = String.format(Konstanten.WARTUNG_DISTANZ_WARNUNG,
                    von.getObjektId(), nach.getObjektId(), distanz, Konstanten.MAX_TRANSPORT_DISTANZ_KM);
                warnungen.add(warnung);
            }
        }
        
        return warnungen;
    }

    /**
     * Berechnet die zusaetzlichen Tage fuer Transportstrecken die das Limit ueberschreiten.
     * Bei Strecken > 120km wird ein zusaetzlicher Reisetag benoetigt.
     * 
     * Pre: route darf nicht null sein
     * Post: Rueckgabe ist Anzahl zusaetzlicher Reisetage
     * 
     * @param route Die Wartungsroute
     * @return Anzahl zusaetzlicher Tage wegen langer Transportstrecken
     */
    public static int berechneZusaetzlicheReisetage(List<Windkraftanlage> route) {
        if (route == null || route.size() < 2) {
            return 0;
        }
        
        int zusaetzlicheTage = 0;
        
        // Pruefe Uebergaenge zwischen Tagesgruppen
        // Transport findet am Ende eines Arbeitstages statt (nach 4 Anlagen)
        for (int i = Konstanten.ANLAGEN_PRO_TAG - 1; i < route.size() - 1; i += Konstanten.ANLAGEN_PRO_TAG) {
            // i ist die letzte Anlage eines Tages, i+1 ist die erste des naechsten Tages
            if (i + 1 < route.size()) {
                Windkraftanlage von = route.get(i);
                Windkraftanlage nach = route.get(i + 1);
                
                float distanz = RoutenOptimierer.berechneDistanz(von, nach);
                
                // Wenn Distanz > Transportlimit, brauchen wir einen extra Reisetag
                if (distanz > Konstanten.MAX_TRANSPORT_DISTANZ_KM) {
                    // Berechne wie viele extra Stunden benoetigt werden
                    float extraKm = distanz - Konstanten.MAX_TRANSPORT_DISTANZ_KM;
                    float extraStunden = extraKm / Konstanten.TRANSPORT_GESCHWINDIGKEIT_KMH;
                    
                    // Jede angefangene 8 Stunden (Arbeitstag) ist ein Reisetag
                    int extraTage = (int) Math.ceil(extraStunden / Konstanten.ARBEITSSTUNDEN_PRO_TAG);
                    zusaetzlicheTage += extraTage;
                }
            }
        }
        
        return zusaetzlicheTage;
    }

    /**
     * Berechnet die Gesamtdauer inklusive Reisetage.
     * 
     * Pre: route darf nicht null sein
     * Post: Rueckgabe ist Gesamtanzahl Tage (Wartung + Reise)
     * 
     * @param route Die Wartungsroute
     * @return Gesamtanzahl benoetiger Tage
     */
    public static int berechneGesamtdauer(List<Windkraftanlage> route) {
        int wartungstage = berechneWartungstage(route);
        int reisetage = berechneZusaetzlicheReisetage(route);
        
        return wartungstage + reisetage;
    }

    /**
     * Formatiert einen Arbeitsplan-Tag als String mit Anlagen-IDs.
     * 
     * Pre: anlagenIds nicht null
     * Post: Rueckgabe ist formatierter String mit IDs
     * 
     * @param anlagenIds Liste der Anlagen-IDs fuer diesen Tag
     * @return Formatierter String "ID 1, ID 2, ..."
     */
    public static String formatiereTagesAnlagen(List<Integer> anlagenIds) {
        if (anlagenIds == null || anlagenIds.isEmpty()) {
            return Konstanten.LEERER_WERT;
        }
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < anlagenIds.size(); i++) {
            if (i > 0) {
                sb.append(Konstanten.ANLAGEN_ID_TRENNER);
            }
            sb.append(Konstanten.ANLAGEN_ID_PREFIX);
            sb.append(anlagenIds.get(i));
        }
        
        return sb.toString();
    }
}
