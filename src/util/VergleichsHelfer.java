package util;

/**
 * Hilfsklasse fuer null-sichere Vergleichsoperationen.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Vergleichslogik
 * - KISS: Einfache if-else Logik, keine komplexen Algorithmen
 * - Modularization: Ausgelagert aus WindkraftanlagenSortierer
 * 
 * Pre: Keine - null-Werte werden explizit behandelt
 * Post: Rueckgabe folgt Comparable-Konvention (-1, 0, 1)
 */
public final class VergleichsHelfer {
    
    /**
     * Privater Konstruktor verhindert Instanziierung.
     */
    private VergleichsHelfer() {
        // Utility-Klasse, keine Instanzen
    }
    
    /**
     * Vergleicht zwei Strings null-sicher und case-insensitive.
     * 
     * Pre: Keine (null-Werte erlaubt)
     * Post: Null-Werte werden ans Ende sortiert
     * Post: Rueckgabe: -1 (erster kleiner), 0 (gleich), 1 (erster groesser)
     *       (Der Vergleichswert wird normalisiert auf -1/0/1 via signum.)
     * 
     * @param erster erster String (kann null sein)
     * @param zweiter zweiter String (kann null sein)
     * @return Vergleichsergebnis (-1/0/1)
     */
    public static int vergleicheStringNullSicher(String erster, String zweiter) {
        // Beide null -> gleich
        if (erster == null && zweiter == null) {
            return 0;
        }
        // Erster ist null -> soll ans Ende (als groesser betrachtet)
        if (erster == null) {
            return 1;
        }
        // Zweiter ist null -> erster kommt vor dem zweiten
        if (zweiter == null) {
            return -1;
        }
        // Beide nicht null: case-insensitive Vergleich und Ergebnis normalisieren auf -1/0/1
        int cmp = erster.compareToIgnoreCase(zweiter);
        return Integer.signum(cmp);
    }
    
    /**
     * Vergleicht zwei Integer null-sicher aufsteigend.
     * 
     * Pre: Keine (null-Werte erlaubt)
     * Post: Null-Werte werden ans Ende sortiert
     * Post: Aufsteigende Sortierung (kleiner zuerst)
     * 
     * @param erster erster Integer (kann null sein)
     * @param zweiter zweiter Integer (kann null sein)
     * @return Vergleichsergebnis
     */
    public static int vergleicheIntegerNullSicher(Integer erster, Integer zweiter) {
        // Beide null -> gleich
        if (erster == null && zweiter == null) {
            return 0;
        }
        // Erster null -> wird ans Ende sortiert
        if (erster == null) {
            return 1;
        }
        // Zweiter null -> erster kommt vor dem zweiten
        if (zweiter == null) {
            return -1;
        }
        // Beide nicht null: numerischer Vergleich aufsteigend
        if (erster < zweiter) {
            return -1;
        }
        if (erster > zweiter) {
            return 1;
        }
        return 0;
    }
    
    /**
     * Vergleicht zwei Float null-sicher ABSTEIGEND.
     * 
     * Pre: Keine (null-Werte erlaubt)
     * Post: Null-Werte werden ans Ende sortiert
     * Post: Absteigende Sortierung (groesser zuerst)
     * 
     * @param erster erster Float (kann null sein)
     * @param zweiter zweiter Float (kann null sein)
     * @return Vergleichsergebnis
     */
    public static int vergleicheFloatNullSicherAbsteigend(Float erster, Float zweiter) {
        // Beide null -> gleich
        if (erster == null && zweiter == null) {
            return 0;
        }
        // Erster null -> ans Ende
        if (erster == null) {
            return 1;
        }
        // Zweiter null -> erster kommt vor dem zweiten
        if (zweiter == null) {
            return -1;
        }
        // Beide nicht null: absteigender Vergleich (groessere Werte zuerst)
        if (erster > zweiter) {
            return -1;
        }
        if (erster < zweiter) {
            return 1;
        }
        return 0;
    }
    
    /**
     * Vergleicht zwei primitive int-Werte aufsteigend.
     * 
     * Pre: Keine
     * Post: Aufsteigende Sortierung
     * 
     * @param erster erster int-Wert
     * @param zweiter zweiter int-Wert
     * @return Vergleichsergebnis
     */
    public static int vergleicheInt(int erster, int zweiter) {
        // Einfacher numerischer Vergleich für primitive ints (aufsteigend)
        if (erster < zweiter) {
            return -1;
        }
        if (erster > zweiter) {
            return 1;
        }
        return 0;
    }
}
