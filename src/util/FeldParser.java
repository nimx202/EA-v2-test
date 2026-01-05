package util;

/**
 * Einfache Klasse zum Umwandeln von Text in Zahlen.
 * Wandelt CSV-Felder in Integer und Float um.
 *
 * Pre: Methoden können mit null-Werten umgehen
 * Post: Gibt konvertierte Werte oder null zurück
 */
public class FeldParser {

    private FeldParser() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Wandelt leeren Text in null um.
     *
     * @param textWert Der zu prüfende Text
     * @return null wenn leer, sonst den Text ohne Leerzeichen
     */
    public static String leerZuNull(String textWert) {
        if (textWert == null) {
            return null;
        }

        String textOhneLeerzeichen = textWert.trim();
        if (textOhneLeerzeichen.isEmpty()) {
            return null;
        }
        
        return textOhneLeerzeichen;
    }

    /**
     * Wandelt Text in eine Ganzzahl um.
     * Gibt null zurück wenn Umwandlung nicht möglich.
     *
     * @param textWert Der zu wandelnde Text
     * @return Ganzzahl oder null
     */
    public static Integer parseGanzzahlNullbar(String textWert) {
        String bereinigterText = bereinigeZahlText(textWert);
        if (bereinigterText == null) {
            return null;
        }

        try {
            return Integer.parseInt(bereinigterText);
        } catch (NumberFormatException fehler) {
            return null;
        }
    }

    /**
     * Wandelt Text in eine Ganzzahl um.
     * Gibt -1 zurück wenn Umwandlung nicht möglich.
     *
     * @param textWert Der zu wandelnde Text
     * @return Ganzzahl oder -1
     */
    public static int parseGanzzahlSicher(String textWert) {
        Integer ergebnis = parseGanzzahlNullbar(textWert);
        if (ergebnis == null) {
            return -1;
        }
        return ergebnis;
    }

    /**
     * Wandelt Text in eine Kommazahl um.
     * Gibt null zurück wenn Umwandlung nicht möglich.
     *
     * @param textWert Der zu wandelnde Text
     * @return Kommazahl oder null
     */
    public static Float parseGleitkommaZahlNullbar(String textWert) {
        String bereinigterText = bereinigeZahlText(textWert);
        if (bereinigterText == null) {
            return null;
        }

        try {
            return Float.parseFloat(bereinigterText);
        } catch (NumberFormatException fehler) {
            return null;
        }
    }

    /**
     * Bereitet einen Zahlentext für die Umwandlung vor.
     * Entfernt Leerzeichen und ersetzt Komma durch Punkt.
     *
     * @param textWert Der zu bereinigende Text
     * @return Bereinigter Text oder null
     */
    private static String bereinigeZahlText(String textWert) {
        if (textWert == null) {
            return null;
        }

        String bereinigterText = textWert.trim();
        if (bereinigterText.isEmpty()) {
            return null;
        }

        // Ersetze Komma durch Punkt (deutsches Format)
        bereinigterText = bereinigterText.replace(',', '.');
        
        return bereinigterText;
    }

    /**
     * Formatiert einen Wert für die Anzeige.
     * null-Werte werden als "unbekannt" dargestellt.
     *
     * Pre: keine
     * Post: Rückgabe: Textdarstellung des Wertes
     *
     * @param wert der zu formatierende Wert
     * @return Text-Darstellung
     */
    public static String formatiereFuerAnzeige(Object wert) {
        if (wert == null) {
            return Konstanten.ANZEIGE_UNBEKANNT;
        }
        return wert.toString();
    }
}
