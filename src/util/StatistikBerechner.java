package util;

import model.Windkraftanlage;
import java.util.List;

/**
 * Utility-Klasse für die Berechnung von Statistiken über Windkraftanlagen.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Statistik-Berechnungen
 * - KISS: Einfache Schleifen statt komplexe Stream-Operationen
 * - Wiederverwendbarkeit: Statische Methoden ohne Zustand
 * 
 * Verantwortlichkeiten:
 * - Zählen von Anlagen mit bestimmten Eigenschaften
 * - Berechnen von Summen und Durchschnitten
 * 
 * Pre: Eingabelisten sind nicht null
 * Post: Liefert berechnete Statistik-Werte
 */
public final class StatistikBerechner {

    /**
     * Privater Konstruktor verhindert Instanziierung.
     * Dies ist eine Utility-Klasse mit nur statischen Methoden.
     */
    private StatistikBerechner() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Zählt Anlagen die Koordinaten haben.
     * Eine Anlage hat Koordinaten wenn sowohl Breitengrad als auch Längengrad gesetzt sind.
     * 
     * Pre: anlagen nicht null
     * Post: Rückgabe: Anzahl der Anlagen mit Koordinaten
     * 
     * @param anlagen Liste aller Windkraftanlagen
     * @return Anzahl Anlagen mit Koordinaten
     */
    public static int zaehleAnlagenMitKoordinaten(List<Windkraftanlage> anlagen) {
        int anzahl = 0;

        for (Windkraftanlage anlage : anlagen) {
            boolean hatBreitengrad = anlage.getBreitengrad() != null;
            boolean hatLaengengrad = anlage.getLaengengrad() != null;

            if (hatBreitengrad && hatLaengengrad) {
                anzahl++;
            }
        }

        return anzahl;
    }

    /**
     * Zählt Anlagen ohne Betreiber-Angabe.
     * Eine Anlage hat keinen Betreiber wenn das Feld null ist.
     * 
     * Pre: anlagen nicht null
     * Post: Rückgabe: Anzahl der Anlagen ohne Betreiber
     * 
     * @param anlagen Liste aller Windkraftanlagen
     * @return Anzahl Anlagen ohne Betreiber
     */
    public static int zaehleAnlagenOhneBetreiber(List<Windkraftanlage> anlagen) {
        int anzahl = 0;
        
        for (Windkraftanlage anlage : anlagen) {
            if (anlage.getBetreiber() == null) {
                anzahl++;
            }
        }
        
        return anzahl;
    }

    /**
     * Berechnet die Gesamtanzahl aller Anlagen.
     * Dies ist die Summe aller Anzahl-Felder der Anlagen.
     * 
     * Pre: anlagen nicht null
     * Post: Rückgabe: Summe aller Anzahl-Werte
     * 
     * @param anlagen Liste aller Windkraftanlagen
     * @return Gesamtanzahl aller Einzel-Anlagen
     */
    public static int berechneGesamtanzahl(List<Windkraftanlage> anlagen) {
        int gesamt = 0;
        
        for (Windkraftanlage anlage : anlagen) {
            Integer anzahl = anlage.getAnzahl();
            if (anzahl != null) {
                gesamt = gesamt + anzahl;
            }
        }
        
        return gesamt;
    }

    /**
     * Berechnet die Gesamt-Leistung aller Anlagen in MW.
     * Dies ist die Summe aller Leistungs-Felder der Anlagen.
     * 
     * Pre: anlagen nicht null
     * Post: Rückgabe: Summe aller Leistungs-Werte in MW
     * 
     * @param anlagen Liste aller Windkraftanlagen
     * @return Gesamt-Leistung in Megawatt
     */
    public static double berechneGesamtLeistung(List<Windkraftanlage> anlagen) {
        double gesamt = 0.0;
        
        for (Windkraftanlage anlage : anlagen) {
            Double leistung = anlage.getGesamtLeistungMW();
            if (leistung != null) {
                gesamt = gesamt + leistung;
            }
        }
        
        return gesamt;
    }
}
