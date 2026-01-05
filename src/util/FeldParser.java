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
<<<<<<< HEAD
     * Wandelt Baujahr-Einträge in eine Ganzzahl um.
     * Werte mit Spannennotation (z.B. 2023-2024) werden auf das erste Jahr reduziert.
     *
     * Pre: keine
     * Post: Rueckgabe: erstes Jahr als Integer oder null bei Fehlern
     *
     * @param textWert Rohtext aus der CSV-Spalte
     * @return Ganzzahl oder null
     */
    public static Integer parseBaujahr(String textWert) {
        String bereinigterText = leerZuNull(textWert);
        if (bereinigterText == null) {
            return null;
        }

        String erstesJahr = ermittleErstesBaujahr(bereinigterText);
        return parseGanzzahlNullbar(erstesJahr);
    }

    /**
=======
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
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
<<<<<<< HEAD
     * Schneidet ggf. vorhandene Baujahr-Spannen hinter dem Trenner ab.
     *
     * @param textMitSpanne Baujahr mit optionaler Spanne
     * @return Erstes Jahr der Angabe
     */
    private static String ermittleErstesBaujahr(String textMitSpanne) {
        int trennerIndex = textMitSpanne.indexOf(Konstanten.BAUJAHR_SPANNEN_TRENNER);
        if (trennerIndex >= 0) {
            return textMitSpanne.substring(0, trennerIndex).trim();
        }
        return textMitSpanne;
    }

    /**
=======
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
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
