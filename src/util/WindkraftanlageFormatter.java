package util;

import model.Windkraftanlage;

/**
 * Utility to format a Windkraftanlage into a string using Konstanten and FeldParser.
 * Utility class (non-instantiable) to keep formatting logic out of the model.
 */
public final class WindkraftanlageFormatter {

    private WindkraftanlageFormatter() { }

    /**
     * Formatiert die gegebene Windkraftanlage als String.
     *
     * Pre: parameter `anlage` darf nicht null sein
     * Post: Rückgabe ist nie null
     *
     * @param anlage Windkraftanlage-Objekt
     * @return formatierter String der Anlage
     */
    public static String format(Windkraftanlage anlage) {
        StringBuilder b = new StringBuilder();

        b.append(Konstanten.TOSTRING_PREFIX);
        appendSimple(b, Konstanten.FELD_OBJEKT_ID, Integer.toString(anlage.getObjektId()));
        appendQuoted(b, Konstanten.FELD_NAME, anlage.getName());
        appendFormatted(b, Konstanten.FELD_BAUJAHR, anlage.getBaujahr());
        appendFormatted(b, Konstanten.FELD_GESAMT_LEISTUNG_MW, anlage.getGesamtLeistungMW());
        appendFormatted(b, Konstanten.FELD_ANZAHL, anlage.getAnzahl());
        appendQuoted(b, Konstanten.FELD_TYP, anlage.getTyp());
        appendQuoted(b, Konstanten.FELD_ORT, anlage.getOrt());
        appendQuoted(b, Konstanten.FELD_LANDKREIS, anlage.getLandkreis());
        appendFormatted(b, Konstanten.FELD_BREITENGRAD, anlage.getBreitengrad());
        appendFormatted(b, Konstanten.FELD_LAENGENGRAD, anlage.getLaengengrad());
        appendQuoted(b, Konstanten.FELD_BETREIBER, anlage.getBetreiber());
        appendQuoted(b, Konstanten.FELD_BEMERKUNGEN, anlage.getBemerkungen());

        b.append(Konstanten.TOSTRING_SUFFIX);
        return b.toString();
    }

    private static void appendSimple(StringBuilder b, String key, String value) {
        b.append(key).append(Konstanten.TOSTRING_WERTTRENNER).append(value);
        b.append(Konstanten.TOSTRING_FELDTRENNER);
    }

    private static void appendQuoted(StringBuilder b, String key, String value) {
        b.append(key).append(Konstanten.TOSTRING_WERTTRENNER)
         .append(Konstanten.TOSTRING_QUOTE).append(value).append(Konstanten.TOSTRING_QUOTE);
        b.append(Konstanten.TOSTRING_FELDTRENNER);
    }

    private static void appendFormatted(StringBuilder b, String key, Object value) {
        b.append(key).append(Konstanten.TOSTRING_WERTTRENNER)
         .append(FeldParser.formatiereFuerAnzeige(value));
        b.append(Konstanten.TOSTRING_FELDTRENNER);
    }
}
