package util;

/**
 * Utility-Klasse für die Validierung und Korrektur von Koordinaten.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Koordinaten-Validierung und -Korrektur
 * - KISS: Einfache if-Bedingungen statt komplexer Logik
<<<<<<< HEAD
 * - Keine Abhängigkeiten: Arbeitet nur mit Float-Werten
=======
 * - Keine Abhängigkeiten: Arbeitet nur mit Double-Werten
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
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
<<<<<<< HEAD
    public static boolean istBreitengradGueltig(Float breitengrad) {
=======
    public static boolean istBreitengradGueltig(Double breitengrad) {
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
        if (breitengrad == null) {
            return true;
        }
        
        boolean istImBereich = breitengrad >= Konstanten.MIN_BREITENGRAD_DE 
                    && breitengrad <= Konstanten.MAX_BREITENGRAD_DE;
        
        return istImBereich;
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
<<<<<<< HEAD
    public static boolean istLaengengradGueltig(Float laengengrad) {
=======
    public static boolean istLaengengradGueltig(Double laengengrad) {
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
        if (laengengrad == null) {
            return true;
        }
        
        boolean istImBereich = laengengrad >= Konstanten.MIN_LAENGENGRAD_DE 
                    && laengengrad <= Konstanten.MAX_LAENGENGRAD_DE;
        
        return istImBereich;
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
<<<<<<< HEAD
    public static Float korrigiereBreitengrad(Float breitengrad) {
=======
    public static Double korrigiereBreitengrad(Double breitengrad) {
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
        if (breitengrad == null) {
            return null;
        }
        
<<<<<<< HEAD
        if (breitengrad == 0.0f) {
=======
        if (breitengrad == 0.0) {
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
            return null;
        }
        
        // Teile durch 1000
<<<<<<< HEAD
        float korrigierterWert = breitengrad / Konstanten.BREITENGRAD_FEHLERFAKTOR;
=======
        double korrigierterWert = breitengrad / Konstanten.BREITENGRAD_FEHLERFAKTOR;
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
        
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
<<<<<<< HEAD
    public static Float korrigiereLaengengrad(Float laengengrad) {
=======
    public static Double korrigiereLaengengrad(Double laengengrad) {
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
        if (laengengrad == null) {
            return null;
        }
        
<<<<<<< HEAD
        if (laengengrad == 0.0f) {
=======
        if (laengengrad == 0.0) {
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
            return null;
        }
        
        // Teile durch 1000
<<<<<<< HEAD
        float korrigierterWert = laengengrad / Konstanten.LAENGENGRAD_FEHLERFAKTOR;
=======
        double korrigierterWert = laengengrad / Konstanten.LAENGENGRAD_FEHLERFAKTOR;
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
        
        // Pruefe ob korrigierter Wert gueltig ist
        if (istLaengengradGueltig(korrigierterWert)) {
            return korrigierterWert;
        }
        
        return null;
    }
}
