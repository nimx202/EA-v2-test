package util;

/**
 * Utility zum Erzeugen konsistenter, strukturierter Ausgabetexte.
 * KISS: Einfache, kleine Hilfsmethoden zum Zusammenbauen von Strings.
 */
public final class AusgabeFormatter {

    private AusgabeFormatter() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Erzeugt eine formatierte Überschrift mit Trennlinien.
     *
     * @param titel Überschrift-Text
     * @return Mehrzeiliger formatierter String
     */
    public static String ueberschrift(String titel) {
        int laenge = Math.max(titel.length(), Konstanten.UEBERSCHRIFT_MIN_LAENGE);
        String strich = Konstanten.TRENNSTRICH_ZEICHEN.repeat(Math.max(0, laenge));
        StringBuilder sb = new StringBuilder();
        sb.append(strich).append(System.lineSeparator());
        sb.append(titel).append(System.lineSeparator());
        sb.append(strich);
        return sb.toString();
    }

    /**
     * Erzeugt eine Zeile im Format "key: value". Wandelt null zu Anzeige-Text um.
     *
     * @param key Schlüssel/Text
     * @param value Wert (kann null sein)
     * @return Formatierter Einzeiler
     */
    public static String schluesselWert(String key, Object value) {
        String wert = (value == null) ? Konstanten.ANZEIGE_UNBEKANNT : value.toString();
        return key + Konstanten.AUSGABE_TRENNZEICHEN + wert;
    }

    /**
     * Erzeugt eine einfache Tabellenzeile mit Spaltentrennzeichen.
     * KISS: Trennt mittels Komma und Leerzeichen.
     *
     * @param spalten Werte der Spalten
     * @return Zeile mit Spalten getrennt
     */
    public static String tabellenZeile(String... spalten) {
        if (spalten == null || spalten.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < spalten.length; i++) {
            if (i > 0) sb.append(Konstanten.PARK_ANZAHL_TRENNER);
            sb.append((spalten[i] == null) ? Konstanten.ANZEIGE_UNBEKANNT : spalten[i]);
        }
        return sb.toString();
    }
}
