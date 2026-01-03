package util;

import model.Windkraftanlage;

import java.util.List;

/**
 * Utility-Klasse zur Korrektur fehlerhafter geografischer Koordinaten.
 * Wendet automatische Korrekturen für typische Fehler an (z.B. fehlendes Dezimalkomma).
 *
 * Design: Stateless Utility-Klasse (keine Instanzen)
 */
public final class KoordinatenKorrektor {

    /**
     * Privater Konstruktor verhindert Instanziierung der Utility-Klasse.
     */
    private KoordinatenKorrektor() {
        // Keine Instanzen möglich
    }

    /**
     * Korrigiert fehlerhafte Koordinaten in allen Datensätzen.
     *
     * Korrekturstrategie:
     * 1. Wenn Breitengrad zu groß (z.B. 48815 statt 48.815):
     *    - Prüfe ob Division durch 1000 in gültigen Bereich führt
     *    - Korrigiere durch Division (fehlendes Komma)
     * 2. Analoge Korrektur für Längengrad
     * 3. Falls Koordinate außerhalb gültigen Bereichs liegt, setze auf null
     *
     * Begründung:
     * - Häufigster Fehler: Fehlendes Dezimalkomma in CSV (z.B. 48815 statt 48.815)
     * - Division durch 1000 korrigiert diesen Fehler
     * - Falls Korrektur nicht möglich: Koordinate wird entfernt (besser als falscher Wert)
     *
     * Pre: anlagen nicht null
     * Post: Alle fehlerhaften Koordinaten wurden korrigiert oder entfernt
     *
     * @param anlagen Liste aller Windkraftanlagen
     * @return Anzahl korrigierter Datensätze
     */
    public static int korrigiereFehlerhafte(List<Windkraftanlage> anlagen) {
        int korrigiert = 0;

        for (Windkraftanlage anlage : anlagen) {
            boolean wurdeKorrigiert = false;

            // Korrigiere Breitengrad
            if (anlage.getBreitengrad() != null) {
                double breitengrad = anlage.getBreitengrad();

                // Prüfe ob Breitengrad außerhalb gültigen Bereichs
                if (breitengrad < Konstanten.MIN_BREITENGRAD_DE || breitengrad > Konstanten.MAX_BREITENGRAD_DE) {
                    // Versuche Korrektur durch Division (fehlendes Komma)
                    double korrigierterWert = breitengrad / Konstanten.BREITENGRAD_FEHLERFAKTOR;

                    if (korrigierterWert >= Konstanten.MIN_BREITENGRAD_DE
                            && korrigierterWert <= Konstanten.MAX_BREITENGRAD_DE) {
                        anlage.setBreitengrad(korrigierterWert);
                        wurdeKorrigiert = true;
                    } else {
                        // Korrektur nicht möglich -> setze auf null
                        anlage.setBreitengrad(null);
                        wurdeKorrigiert = true;
                    }
                }
            }

            // Korrigiere Längengrad
            if (anlage.getLaengengrad() != null) {
                double laengengrad = anlage.getLaengengrad();

                // Prüfe ob Längengrad außerhalb gültigen Bereichs
                if (laengengrad < Konstanten.MIN_LÄNGENGRAD_DE || laengengrad > Konstanten.MAX_LÄNGENGRAD_DE) {
                    // Versuche Korrektur durch Division (fehlendes Komma)
                    double korrigierterWert = laengengrad / Konstanten.LÄNGENGRAD_FEHLERFAKTOR;

                    if (korrigierterWert >= Konstanten.MIN_LÄNGENGRAD_DE
                            && korrigierterWert <= Konstanten.MAX_LÄNGENGRAD_DE) {
                        anlage.setLaengengrad(korrigierterWert);
                        wurdeKorrigiert = true;
                    } else {
                        // Korrektur nicht möglich -> setze auf null
                        anlage.setLaengengrad(null);
                        wurdeKorrigiert = true;
                    }
                }
            }

            if (wurdeKorrigiert) {
                korrigiert++;
            }
        }

        return korrigiert;
    }

    /**
     * Korrigiert eine einzelne Koordinate durch Division wenn nötig.
     *
     * Pre: wert nicht null
     * Post: Rückgabe korrigierter Wert oder null wenn Korrektur nicht möglich
     *
     * @param wert ursprünglicher Koordinatenwert
     * @param min minimaler gültiger Wert
     * @param max maximaler gültiger Wert
     * @param faktor Korrekturfaktor (normalerweise 1000)
     * @return korrigierter Wert oder null
     */
    public static Double korrigiereKoordinate(double wert, double min, double max, double faktor) {
        // Bereits gültig
        if (wert >= min && wert <= max) {
            return wert;
        }

        // Versuche Korrektur
        double korrigiert = wert / faktor;
        if (korrigiert >= min && korrigiert <= max) {
            return korrigiert;
        }

        // Korrektur nicht möglich
        return null;
    }

    /**
     * Korrigiert fehlerhafte Baujahre in allen Datensätzen.
     *
     * Korrekturstrategie:
     * 1. Wenn Baujahr zu groß (z.B. 20022 statt 2002):
     *    - Prüfe ob Division durch 10 in gültigen Bereich führt
     *    - Korrigiere durch Division (überzählige Ziffer)
     * 2. Wenn Baujahr zu klein (z.B. 202 statt 2020):
     *    - Prüfe ob Multiplikation mit 10 in gültigen Bereich führt
     * 3. Falls Korrektur nicht möglich: Baujahr wird entfernt (null)
     *
     * Begründung:
     * - Häufigster Fehler: Tippfehler mit überzähliger oder fehlender Ziffer
     * - Division/Multiplikation mit 10 korrigiert diese Fehler
     * - Falls Korrektur nicht möglich: Baujahr wird entfernt
     *
     * Pre: anlagen nicht null
     * Post: Alle fehlerhaften Baujahre wurden korrigiert oder entfernt
     *
     * @param anlagen Liste aller Windkraftanlagen
     * @return Anzahl korrigierter Datensätze
     */
    public static int korrigiereFehlerhafesBaujahr(List<Windkraftanlage> anlagen) {
        int korrigiert = 0;

        for (Windkraftanlage anlage : anlagen) {
            if (anlage.getBaujahr() != null) {
                int baujahr = anlage.getBaujahr();

                // Prüfe ob Baujahr außerhalb gültigen Bereichs
                if (baujahr < Konstanten.MIN_BAUJAHR || baujahr > Konstanten.MAX_BAUJAHR) {
                    Integer korrigierterWert = korrigiereBaujahr(baujahr);
                    anlage.setBaujahr(korrigierterWert);
                    korrigiert++;
                }
            }
        }

        return korrigiert;
    }

    /**
     * Korrigiert ein einzelnes Baujahr.
     *
     * Pre: keine
     * Post: Rückgabe korrigierter Wert oder null
     *
     * @param baujahr Baujahr
     * @return Korrigiertes Baujahr oder null wenn Korrektur nicht möglich
     */
    public static Integer korrigiereBaujahr(int baujahr) {
        // Prüfe ob bereits gültig
        if (baujahr >= Konstanten.MIN_BAUJAHR && baujahr <= Konstanten.MAX_BAUJAHR) {
            return baujahr;
        }

        // Versuche Korrektur durch Division (überzählige Ziffer)
        int korrigiert = baujahr / Konstanten.BAUJAHR_FEHLERFAKTOR;
        if (korrigiert >= Konstanten.MIN_BAUJAHR && korrigiert <= Konstanten.MAX_BAUJAHR) {
            return korrigiert;
        }

        // Versuche Korrektur durch Multiplikation (fehlende Ziffer)
        korrigiert = baujahr * Konstanten.BAUJAHR_FEHLERFAKTOR;
        if (korrigiert >= Konstanten.MIN_BAUJAHR && korrigiert <= Konstanten.MAX_BAUJAHR) {
            return korrigiert;
        }

        // Korrektur nicht möglich
        return null;
    }

    /**
     * Korrigiert fehlerhafte Gesamtleistungen in allen Datensätzen.
     *
     * Korrekturstrategie:
     * 1. Wenn Gesamtleistung zu groß (z.B. 594 statt 5.94):
     *    - Prüfe ob Division durch 100 in gültigen Bereich führt
     *    - Korrigiere durch Division (fehlendes Komma)
     * 2. Falls Korrektur nicht möglich: Gesamtleistung wird entfernt (null)
     *
     * Begründung:
     * - Häufigster Fehler: Fehlendes Dezimalkomma (z.B. 594 statt 5.94)
     * - Division durch 100 korrigiert diesen Fehler
     * - Falls Korrektur nicht möglich: Gesamtleistung wird entfernt
     *
     * Pre: anlagen nicht null
     * Post: Alle fehlerhaften Gesamtleistungen wurden korrigiert oder entfernt
     *
     * @param anlagen Liste aller Windkraftanlagen
     * @return Anzahl korrigierter Datensätze
     */
    public static int korrigiereFehlerhafeGesamtLeistung(List<Windkraftanlage> anlagen) {
        int korrigiert = 0;

        for (Windkraftanlage anlage : anlagen) {
            if (anlage.getGesamtLeistungMW() != null) {
                double gesamtLeistung = anlage.getGesamtLeistungMW();

                // Prüfe ob Gesamtleistung außerhalb gültigen Bereichs
                if (gesamtLeistung < Konstanten.MIN_GESAMTLEISTUNG_MW 
                        || gesamtLeistung > Konstanten.MAX_GESAMTLEISTUNG_MW) {
                    Double korrigierterWert = korrigiereGesamtLeistung(gesamtLeistung);
                    anlage.setGesamtLeistungMW(korrigierterWert);
                    korrigiert++;
                }
            }
        }

        return korrigiert;
    }

    /**
     * Korrigiert eine einzelne Gesamtleistung.
     *
     * Pre: keine
     * Post: Rückgabe korrigierter Wert oder null
     *
     * @param gesamtLeistung Gesamtleistung in MW
     * @return Korrigierte Gesamtleistung oder null wenn Korrektur nicht möglich
     */
    public static Double korrigiereGesamtLeistung(double gesamtLeistung) {
        // Prüfe ob bereits gültig
        if (gesamtLeistung >= Konstanten.MIN_GESAMTLEISTUNG_MW 
                && gesamtLeistung <= Konstanten.MAX_GESAMTLEISTUNG_MW) {
            return gesamtLeistung;
        }

        // Versuche Korrektur durch Division (fehlendes Komma)
        double korrigiert = gesamtLeistung / Konstanten.GESAMTLEISTUNG_FEHLERFAKTOR;
        if (korrigiert >= Konstanten.MIN_GESAMTLEISTUNG_MW 
                && korrigiert <= Konstanten.MAX_GESAMTLEISTUNG_MW) {
            return korrigiert;
        }

        // Korrektur nicht möglich
        return null;
    }
}
