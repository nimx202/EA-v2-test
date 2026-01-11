package util;

import model.Windkraftanlage;

/**
 * Utility-Klasse zum Extrahieren des Herstellers aus dem Typ-Feld einer Windkraftanlage.
 * Der Hersteller ist typischerweise das erste Wort im Typ-Feld (z.B. "Vestas V47-660kW" -> "Vestas").
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Hersteller-Extraktion
 * - KISS: Einfaches String-Splitting am Leerzeichen
 * - Modularisierung: Getrennte Utility-Klasse
 * 
 * Pre: -
 * Post: Liefert Herstellernamen oder Platzhalter
 */
public final class HerstellerExtraktor {

    /**
     * Privater Konstruktor verhindert Instanziierung der Utility-Klasse.
     */
    private HerstellerExtraktor() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Extrahiert den Herstellernamen aus dem Typ-Feld einer Windkraftanlage.
     * Der Hersteller ist das erste Wort vor dem ersten Leerzeichen.
     * 
     * Beispiele:
     * - "Vestas V47-660kW (9×)" -> "Vestas"
     * - "Enercon E-82" -> "Enercon"
     * - "REpower MM92" -> "REpower"
     * - null oder "" -> UNBEKANNTER_HERSTELLER
     * 
     * Pre: anlage darf nicht null sein
     * Post: Rueckgabe ist Herstellername oder UNBEKANNTER_HERSTELLER
     * 
     * @param anlage Die Windkraftanlage
     * @return Herstellername oder Konstanten.UNBEKANNTER_HERSTELLER
     */
    public static String extrahiereHersteller(Windkraftanlage anlage) {
        if (anlage == null) {
            return Konstanten.UNBEKANNTER_HERSTELLER;
        }
        
        String typ = anlage.getTyp();
        return extrahiereHerstellerAusTyp(typ);
    }

    /**
     * Extrahiert den Herstellernamen aus einem Typ-String.
     * Der Hersteller ist das erste Wort vor dem ersten Leerzeichen.
     * 
     * Pre: -
     * Post: Rueckgabe ist Herstellername oder UNBEKANNTER_HERSTELLER
     * 
     * @param typ Der Typ-String (kann null sein)
     * @return Herstellername oder Konstanten.UNBEKANNTER_HERSTELLER
     */
    public static String extrahiereHerstellerAusTyp(String typ) {
        // Pruefe auf null oder leeren String
        if (typ == null) {
            return Konstanten.UNBEKANNTER_HERSTELLER;
        }
        
        String bereinigterTyp = typ.trim();
        if (bereinigterTyp.isEmpty()) {
            return Konstanten.UNBEKANNTER_HERSTELLER;
        }
        
        // Finde erstes Leerzeichen
        int leerzeichenIndex = bereinigterTyp.indexOf(Konstanten.LEERZEICHEN);
        
        // Wenn kein Leerzeichen vorhanden, ist der ganze String der Hersteller
        if (leerzeichenIndex < 0) {
            return bereinigterTyp;
        }
        
        // Extrahiere erstes Wort
        String hersteller = bereinigterTyp.substring(0, leerzeichenIndex);
        
        // Falls erstes Wort leer, gib Platzhalter zurueck
        if (hersteller.isEmpty()) {
            return Konstanten.UNBEKANNTER_HERSTELLER;
        }
        
        return hersteller;
    }
}
