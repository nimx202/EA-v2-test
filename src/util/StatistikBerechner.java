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
            if (anlage.hatKoordinaten()) {
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
    public static float berechneGesamtLeistung(List<Windkraftanlage> anlagen) {
        float gesamt = 0.0f;
        
        for (Windkraftanlage anlage : anlagen) {
            Float leistung = anlage.getGesamtLeistungMW();
            if (leistung != null) {
                gesamt = gesamt + leistung;
            }
        }
        
        return gesamt;
    }

    /**
     * Findet die südlichste Windkraftanlage (kleinster Breitengrad).
     * Durchläuft alle Anlagen und ermittelt die mit dem kleinsten Breitengrad.
     * 
     * Pre: anlagen nicht null
     * Post: Rückgabe: Anlage mit kleinstem Breitengrad oder null wenn keine gefunden
     * 
     * @param anlagen Liste aller Windkraftanlagen
     * @return Südlichste Anlage oder null
     */
    public static Windkraftanlage findeSuedlichsteAnlage(List<Windkraftanlage> anlagen) {
        Windkraftanlage suedlichste = null;
        float kleinsterBreitengrad = Float.MAX_VALUE;
        
        for (Windkraftanlage anlage : anlagen) {
            if (anlage.hatKoordinaten()) {
                float breitengrad = anlage.getGeoKoordinaten().getBreitengrad();
                if (breitengrad < kleinsterBreitengrad) {
                    kleinsterBreitengrad = breitengrad;
                    suedlichste = anlage;
                }
            }
        }
        
        return suedlichste;
    }

    /**
     * Findet die Windkraftanlage mit der höchsten Gesamtleistung.
     * Durchläuft alle Anlagen und ermittelt die mit der größten Leistung in MW.
     * 
     * Pre: anlagen nicht null
     * Post: Rückgabe: Anlage mit höchster Leistung oder null wenn keine gefunden
     * 
     * @param anlagen Liste aller Windkraftanlagen
     * @return Anlage mit höchster Leistung oder null
     */
    public static Windkraftanlage findeAnlageMitHoechsterLeistung(List<Windkraftanlage> anlagen) {
        Windkraftanlage anlageMitHoechsterLeistung = null;
        float hoechsteLeistung = 0.0f;
        
        for (Windkraftanlage anlage : anlagen) {
            Float leistung = anlage.getGesamtLeistungMW();
            if (leistung != null && leistung > hoechsteLeistung) {
                hoechsteLeistung = leistung;
                anlageMitHoechsterLeistung = anlage;
            }
        }
        
        return anlageMitHoechsterLeistung;
    }

    /**
     * Findet die Windkraftanlage mit den meisten Windrädern (Anzahl).
     * Durchläuft alle Anlagen und ermittelt die mit der größten Anzahl.
     * 
     * Pre: anlagen nicht null
     * Post: Rückgabe: Anlage mit meisten Windrädern oder null wenn keine gefunden
     * 
     * @param anlagen Liste aller Windkraftanlagen
     * @return Anlage mit meisten Windrädern oder null
     */
    public static Windkraftanlage findeAnlageMitMeistenWindraedern(List<Windkraftanlage> anlagen) {
        Windkraftanlage anlageMitMeistenWindraedern = null;
        int meistenWindraeder = 0;
        
        for (Windkraftanlage anlage : anlagen) {
            Integer anzahl = anlage.getAnzahl();
            if (anzahl != null && anzahl > meistenWindraeder) {
                meistenWindraeder = anzahl;
                anlageMitMeistenWindraedern = anlage;
            }
        }
        
        return anlageMitMeistenWindraedern;
    }

    /**
     * Liefert eine Liste aller Anlagen ohne Koordinaten (Breiten-/Längengrad null oder nicht gesetzt).
     *
     * Pre: anlagen nicht null
     * Post: Rückgabe: Liste der Anlagen ohne Koordinaten (möglicherweise leer)
     *
     * @param anlagen Liste aller Windkraftanlagen
     * @return Liste der Anlagen ohne Koordinaten
     */
    public static java.util.List<Windkraftanlage> filterAnlagenOhneKoordinaten(List<Windkraftanlage> anlagen) {
        java.util.List<Windkraftanlage> ergebnis = new java.util.ArrayList<>();

        for (Windkraftanlage anlage : anlagen) {
            if (!anlage.hatKoordinaten()) {
                ergebnis.add(anlage);
            }
        }

        return ergebnis;
    }
}

