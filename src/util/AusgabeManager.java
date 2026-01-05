package util;

/**
 * Zentrale Ausgabe-Verwaltung für die gesamte Anwendung.
 * Verantwortlich für formatierte Konsolenausgabe mit Trennzeichen.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Ausgabe-Logik
 * - Wiederverwendbarkeit: Alle Ausgaben erfolgen über diese Klasse
 * - Konsistenz: Einheitliche Formatierung
 * 
 * Verwendung:
 * - AusgabeManager.gebeAus(text) - einfache Ausgabe
 * - AusgabeManager.gebeAusFormat(format, args) - formatierte Ausgabe
 * - AusgabeManager.gebeAusMitTrennzeichen(key, value) - mit Trennzeichen
 */
public final class AusgabeManager {

    /**
     * Privater Konstruktor verhindert Instanziierung der Utility-Klasse.
     */
    private AusgabeManager() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Gibt einen Text auf der Standardausgabe aus.
     * 
     * Pre: text nicht null
     * Post: Text wurde auf stdout ausgegeben
     * 
     * @param text Der auszugebende Text
     */
    public static void gebeAus(String text) {
        System.out.println(text);
    }

    /**
     * Gibt einen formatierten Text auf der Standardausgabe aus.
     * 
     * Pre: format nicht null
     * Post: Formatierter Text wurde auf stdout ausgegeben
     * 
     * @param format Format-String (wie in printf)
     * @param args Argumente für die Formatierung
     */
    public static void gebeAusFormat(String format, Object... args) {
        System.out.printf(format, args);
    }

    /**
     * Gibt einen Schlüssel-Wert-Paar mit Trennzeichen aus.
     * Format: "schlüssel: wert"
     * 
     * Pre: schlüssel und wert nicht null
     * Post: Formatierter Schlüssel-Wert-Paar auf stdout ausgegeben
     * 
    * @param schluessel Der Schlüssel
     * @param wert Der Wert
     */
    public static void gebeAusMitTrennzeichen(String schluessel, Object wert) {
        System.out.println(AusgabeFormatter.schluesselWert(schluessel, wert));
    }

    /**
     * Gibt einen Text auf die Fehlerausgabe aus.
     * 
     * Pre: text nicht null
     * Post: Text wurde auf stderr ausgegeben
     * 
     * @param text Der auszugebende Fehlertext
     */
    public static void gebeFehlerAus(String text) {
        System.err.println(text);
    }

    /**
     * Gibt eine leere Zeile aus.
     * 
     * Pre: keine
     * Post: Leere Zeile wurde auf stdout ausgegeben
     */
    public static void gebeLeereZeileAus() {
        System.out.println();
    }

    /**
     * Gibt einen Trennstrich mit gegebener Länge aus.
     * 
     * Pre: länge >= 0
     * Post: Trennstrich wurde auf stdout ausgegeben
     * 
    * @param laenge Länge des Trennstrichs
     */
    public static void gebeTrennstrichAus(int laenge) {
        System.out.println(Konstanten.TRENNSTRICH_ZEICHEN.repeat(Math.max(0, laenge)));
    }

    /**
     * Gibt eine Überschrift mit Trennzeichen aus.
     * Format:
     * ===========================
     * Überschrift
     * ===========================
     * 
     * Pre: überschrift nicht null
     * Post: Formatierte Überschrift auf stdout ausgegeben
     * 
    * @param ueberschrift Der Text der Überschrift
     */
    public static void gebeUeberschriftAus(String ueberschrift) {
        gebeAus(AusgabeFormatter.ueberschrift(ueberschrift));
    }

    /**
     * Gibt eine Sektion/Überschrift mit einheitlicher Formatierung aus.
     *
     * @param titel Sektionstitel
     */
    public static void gebeSektionAus(String titel) {
        gebeAus(AusgabeFormatter.ueberschrift(titel));
    }

    /**
     * Gibt ein Schlüssel-Wert-Paar in einheitlichem Stil aus.
     *
     * @param schluessel Schlüssel
     * @param wert Wert (kann null sein)
     */
    public static void gebeKeyValue(String schluessel, Object wert) {
        gebeAus(AusgabeFormatter.schluesselWert(schluessel, wert));
    }
}
