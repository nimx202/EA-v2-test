package util;

/**
 * Utility-Klasse für die Validierung und Korrektur von Koordinaten.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Koordinaten-Validierung und -Korrektur
 * - KISS: Einfache if-Bedingungen statt komplexer Logik
 * - Keine Abhängigkeiten: Arbeitet nur mit Float-Werten
 * 
 * Verantwortlichkeiten:
 * - Prüfen ob Koordinaten im gültigen Bereich für Deutschland liegen
 * - Korrigieren von fehlerhaften Koordinaten (z.B. Faktor 1000 Fehler)
 * 
 * Gültige Bereiche für Deutschland:
 * - Breitengrad: 47° bis 55° Nord
 * - Längengrad: 5° bis 16° Ost
 * 
 * Pre: Methoden können null-Werte verarbeiten
 * Post: Liefert Validierungs-Ergebnisse oder korrigierte Werte
 */
public final class KoordinatenValidierer {

    /**
     * Privater Konstruktor verhindert Instanziierung.
     * Dies ist eine Utility-Klasse mit nur statischen Methoden.
     */
    private KoordinatenValidierer() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Prüft ob der Breitengrad für Deutschland gültig ist.
     * null-Werte werden als gültig betrachtet (unbekannte Koordinate).
     * 
     * Pre: keine
     * Post: Rückgabe: true wenn gültig oder null, false sonst
     * 
     * @param breitengrad Der zu prüfende Breitengrad
     * @return true wenn gültig oder null, false sonst
     */
    public static boolean istBreitengradGueltig(Float breitengrad) {
        if (breitengrad == null) {
            return true;
        }
        
        
        return breitengrad >= Konstanten.MIN_BREITENGRAD_DE 
                    && breitengrad <= Konstanten.MAX_BREITENGRAD_DE;
    }

    /**
     * Prüft ob der Längengrad für Deutschland gültig ist.
     * null-Werte werden als gültig betrachtet (unbekannte Koordinate).
     * 
     * Pre: keine
     * Post: Rückgabe: true wenn gültig oder null, false sonst
     * 
    * @param laengengrad Der zu prüfende Laengengrad
     * @return true wenn gültig oder null, false sonst
     */
    public static boolean istLaengengradGueltig(Float laengengrad) {
        if (laengengrad == null) {
            return true;
        }
        
        
        return laengengrad >= Konstanten.MIN_LAENGENGRAD_DE 
                    && laengengrad <= Konstanten.MAX_LAENGENGRAD_DE;
    }

    /**
     * Korrigiert einen fehlerhaften Breitengrad.
     * Teilt den Wert durch 1000 wenn das Ergebnis im gültigen Bereich liegt.
     * Typischer Fehler: 48815 statt 48.815
     * 
     * Pre: keine
     * Post: Rückgabe: korrigierter Wert oder null wenn nicht korrigierbar
     * 
     * @param breitengrad Der zu korrigierende Breitengrad
     * @return Korrigierter Breitengrad oder null
     */
    public static Float korrigiereBreitengrad(Float breitengrad) {
        if (breitengrad == null) {
            return null;
        }
        
        if (breitengrad == 0.0f) {
            return null;
        }
        
        // Teile durch 1000
        float korrigierterWert = breitengrad / Konstanten.BREITENGRAD_FEHLERFAKTOR;
        
        // Pruefe ob korrigierter Wert gueltig ist
        if (istBreitengradGueltig(korrigierterWert)) {
            return korrigierterWert;
        }
        
        return null;
    }

    /**
     * Korrigiert einen fehlerhaften Längengrad.
     * Teilt den Wert durch 1000 wenn das Ergebnis im gültigen Bereich liegt.
     * Typischer Fehler: 9123 statt 9.123
     * 
     * Pre: keine
     * Post: Rückgabe: korrigierter Wert oder null wenn nicht korrigierbar
     * 
    * @param laengengrad Der zu korrigierende Laengengrad
     * @return Korrigierter Längengrad oder null
     */
    public static Float korrigiereLaengengrad(Float laengengrad) {
        if (laengengrad == null) {
            return null;
        }
        
        if (laengengrad == 0.0f) {
            return null;
        }
        
        // Teile durch 1000
        float korrigierterWert = laengengrad / Konstanten.LAENGENGRAD_FEHLERFAKTOR;
        
        // Pruefe ob korrigierter Wert gueltig ist
        if (istLaengengradGueltig(korrigierterWert)) {
            return korrigierterWert;
        }
        
        return null;
    }
}
