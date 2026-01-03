package util;

/**
 * Utility-Klasse für Parsing und Konvertierung von Feldwerten aus CSV-Daten.
 * Behandelt null, leere Strings und Zahlkonvertierungen robust.
 *
 * Vertrag:
 * Pre: Methoden können mit null-Werten umgehen
 * Post: Rückgabewerte entsprechen der Dokumentation
 */
public class FeldParser {

    private FeldParser() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Konvertiert einen leeren oder Whitespace-String zu null.
     *
     * Pre: keine
     * Post: null wenn s null, leer oder nur Whitespace; sonst gekürzt String
     *
     * @param wert der zu prüfende String
     * @return null oder gekürzt String
     */
    public static String leerZuNull(String wert) {
        if (wert == null) {
            return null;
        }

        String gekürzt = wert.trim();
        return gekürzt.isEmpty() ? null : gekürzt;
    }

    /**
     * Parst einen String zu Integer mit Null-Handling.
     * Ungültige Werte werden als null zurückgegeben.
     *
     * Pre: keine
     * Post: null bei ungültiger oder leerer Eingabe; sonst Integer-Wert
     *
     * @param wert der zu parsende String
     * @return Integer oder null
     */
    public static Integer parseGanzzahlNullbar(String wert) {
        String gekürzt = leerZuNull(wert);
        if (gekürzt == null) {
            return null;
        }

        try {
            return Integer.valueOf(gekürzt);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Parst einen String zu int mit Default-Wert bei Fehler.
     * Diese Methode gibt immer einen int zurück (nie null).
     *
     * Pre: keine
     * Post: Integer-Wert oder UNGÜLTIGE_ID (-1) bei Fehler
     *
     * @param wert der zu parsende String
     * @return int-Wert oder -1 bei Fehler
     */
    public static int parseGanzzahlSicher(String wert) {
        Integer geparst = parseGanzzahlNullbar(wert);
        return geparst == null ? Konstanten.UNGÜLTIGE_ID : geparst;
    }

    /**
     * Parst einen String zu Double mit Null-Handling.
     * Ungültige Werte werden als null zurückgegeben.
     *
     * Pre: keine
     * Post: null bei ungültiger oder leerer Eingabe; sonst Double-Wert
     *
     * @param wert der zu parsende String
     * @return Double oder null
     */
    public static Double parseGleitkommaZahlNullbar(String wert) {
        String gekürzt = leerZuNull(wert);
        if (gekürzt == null) {
            return null;
        }

        try {
            return Double.valueOf(gekürzt);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Formatiert einen Wert für die Ausgabe.
     * null-Werte werden als "unbekannt" dargestellt.
     *
     * Pre: keine
     * Post: String-Darstellung, nie null
     *
     * @param wert der zu formatierende Wert
     * @return formatierter String
     */
    public static String formatiereFürAnzeige(Object wert) {
        return wert != null ? wert.toString() : Konstanten.ANZEIGE_UNBEKANNT;
    }
}
