package util;

import model.GeoKoordinaten;
import model.Windkraftanlage;

/**
 * Utility-Klasse zur Erzeugung von `Windkraftanlage`-Objekten aus CSV-Feldern.
 *
 * Design-Prinzipien:
 * - Single Responsibility: Nur die Mapping-Logik von CSV-Feldern zu Domain-Objekten
 * - KISS: Einfache, gut testbare Methode
 */
public final class WindkraftanlageErsteller {

    private WindkraftanlageErsteller() { }

    /**
     * Erstellt eine `Windkraftanlage` aus einem Array von CSV-Feldern.
     *
     * Pre: csvFelder darf nicht null sein und muss mindestens `Konstanten.ERWARTET_FELDANZAHL` Elemente enthalten
     * Post: Rückgabe: neues `Windkraftanlage`-Objekt oder null bei Fehler/inkonsistenten Feldern
     *
     * @param csvFelder Felder einer CSV-Zeile
     * @return `Windkraftanlage` oder null
     */
    public static Windkraftanlage erstelleAusFelder(String[] csvFelder) {
        if (csvFelder == null || csvFelder.length < Konstanten.ERWARTET_FELDANZAHL) {
            return null;
        }

        try {
            int feldIndex = 0;

            int objektId = FeldParser.parseGanzzahlSicher(csvFelder[feldIndex++]);
            String name = FeldParser.leerZuNull(CsvParser.bereinigesFeld(csvFelder[feldIndex++]));
            Integer baujahr = FeldParser.parseBaujahr(CsvParser.bereinigesFeld(csvFelder[feldIndex++]));
            Float gesamtLeistungMW = FeldParser.parseGleitkommaZahlNullbar(
                CsvParser.bereinigesFeld(csvFelder[feldIndex++]));
            Integer anzahl = FeldParser.parseGanzzahlNullbar(
                CsvParser.bereinigesFeld(csvFelder[feldIndex++]));
            String typ = FeldParser.leerZuNull(CsvParser.bereinigesFeld(csvFelder[feldIndex++]));
            String ort = FeldParser.leerZuNull(CsvParser.bereinigesFeld(csvFelder[feldIndex++]));
            String landkreis = FeldParser.leerZuNull(CsvParser.bereinigesFeld(csvFelder[feldIndex++]));
            Float breitengrad = FeldParser.parseGleitkommaZahlNullbar(
                CsvParser.bereinigesFeld(csvFelder[feldIndex++]));
            Float laengengrad = FeldParser.parseGleitkommaZahlNullbar(
                CsvParser.bereinigesFeld(csvFelder[feldIndex++]));
            String betreiber = FeldParser.leerZuNull(CsvParser.bereinigesFeld(csvFelder[feldIndex++]));
            String bemerkungen = FeldParser.leerZuNull(CsvParser.bereinigesFeld(csvFelder[feldIndex++]));

            GeoKoordinaten geoKoordinaten = new GeoKoordinaten(breitengrad, laengengrad);

            return new Windkraftanlage(objektId, name, baujahr, gesamtLeistungMW,
                anzahl, typ, ort, landkreis, geoKoordinaten,
                betreiber, bemerkungen);
        } catch (Exception fehler) {
            return null;
        }
    }
}
