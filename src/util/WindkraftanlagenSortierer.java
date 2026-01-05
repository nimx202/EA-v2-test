package util;

import java.util.ArrayList;
import java.util.List;
import model.Windkraftanlage;

/**
 * Utility-Klasse fuer Sortierungen von Windkraftanlagen.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Sortierung von Windkraftanlagen
 * - KISS: Einfache Bubble-Sort-Implementierung
 * - Modularization: Vergleichslogik ausgelagert in VergleichsHelfer
 * 
 * Pre: Eingabelisten duerfen nicht null sein
 * Post: Alle Methoden geben neue sortierte Liste zurueck
 * Post: Original-Liste bleibt unveraendert
 */
public final class WindkraftanlagenSortierer {

    /**
     * Privater Konstruktor verhindert Instanziierung.
     */
    private WindkraftanlagenSortierer() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Sortiert Windkraftanlagen nach Ort, Name und ObjektId.
     * 
     * Pre: anlagen darf nicht null sein
     * Post: Rueckgabe ist neue sortierte Liste
     * Post: Original-Liste unveraendert
     * 
     * @param anlagen Liste der zu sortierenden Windkraftanlagen
     * @return neue sortierte Liste
     */
    public static List<Windkraftanlage> sortiereNachOrtNameId(List<Windkraftanlage> anlagen) {
        List<Windkraftanlage> kopie = kopiereListe(anlagen);
        bubbleSortOrtNameId(kopie);
        return kopie;
    }

    /**
     * Sortiert und gibt die ersten n Einträge sowie die Dauer der Sortierung aus.
     * 
     * Pre: anlagen darf nicht null sein, maxAnzeigen >= 0
     * Post: Gibt formatierte Ausgabe der ersten Elemente und der benötigten Zeit aus
     * 
     * @param anlagen Eingabeliste
     * @param maxAnzeigen Maximale Anzahl der ausgegebenen Einträge
     * @return Dauer der Sortierung in Millisekunden
     */
    public static float sortiereNachOrtNameIdUndGebeAus(List<Windkraftanlage> anlagen, int maxAnzeigen) {
        util.ZeitMessung timer = util.ZeitMessung.starte();
        List<Windkraftanlage> sortiert = sortiereNachOrtNameId(anlagen);
        float dauer = timer.stoppeUndGibMillis();

        util.AusgabeManager.gebeUeberschriftAus(util.Konstanten.SORTIERUNG_ORT_NAME_ID_UEBERSCHRIFT);
        util.AusgabeManager.gebeAusMitTrennzeichen(util.Konstanten.AUSGABE_ANZAHL, sortiert.size());
        util.AusgabeManager.gebeAusMitTrennzeichen(util.Konstanten.AUSGABE_DAUER_MS, dauer);
        util.AusgabeManager.gebeLeereZeileAus();

        int limit = Math.max(0, Math.min(maxAnzeigen, sortiert.size()));
        for (int i = 0; i < limit; i++) {
            util.AusgabeManager.gebeAus(sortiert.get(i).toString());
        }
        return dauer;
    }

    /**
     * Sortiert Windkraftanlagen nach Leistung, Baujahr und Name.
     * 
     * Pre: anlagen darf nicht null sein
     * Post: Rueckgabe ist neue sortierte Liste
     * Post: Leistung absteigend, Baujahr aufsteigend
     * 
     * @param anlagen Liste der zu sortierenden Windkraftanlagen
     * @return neue sortierte Liste
     */
    public static List<Windkraftanlage> sortiereNachLeistungBaujahrName(List<Windkraftanlage> anlagen) {
        List<Windkraftanlage> kopie = kopiereListe(anlagen);
        bubbleSortLeistungBaujahrName(kopie);
        return kopie;
    }

    /**
     * Sortiert nach Leistung/Baujahr/Name, gibt ersten n Einträge und Dauer aus.
     * @param anlagen Eingabeliste
     * @param maxAnzeigen Maximale Anzahl der ausgegebenen Einträge
     * @return Dauer in Millisekunden
     */
    public static float sortiereNachLeistungBaujahrNameUndGebeAus(List<Windkraftanlage> anlagen, int maxAnzeigen) {
        util.ZeitMessung timer = util.ZeitMessung.starte();
        List<Windkraftanlage> sortiert = sortiereNachLeistungBaujahrName(anlagen);
        float dauer = timer.stoppeUndGibMillis();

        util.AusgabeManager.gebeUeberschriftAus(util.Konstanten.SORTIERUNG_LEISTUNG_BAUJAHR_NAME_UEBERSCHRIFT);
        util.AusgabeManager.gebeAusMitTrennzeichen(util.Konstanten.AUSGABE_ANZAHL, sortiert.size());
        util.AusgabeManager.gebeAusMitTrennzeichen(util.Konstanten.AUSGABE_DAUER_MS, dauer);
        util.AusgabeManager.gebeLeereZeileAus();

        int limit = Math.max(0, Math.min(maxAnzeigen, sortiert.size()));
        for (int i = 0; i < limit; i++) {
            util.AusgabeManager.gebeAus(sortiert.get(i).toString());
        }
        return dauer;
    }

    /**
     * Sortiert Windkraftanlagen nach Landkreis, Ort und Name.
     * 
     * Pre: anlagen darf nicht null sein
     * Post: Rueckgabe ist neue sortierte Liste
     * Post: Alle Kriterien aufsteigend sortiert
     * 
     * @param anlagen Liste der zu sortierenden Windkraftanlagen
     * @return neue sortierte Liste
     */
    public static List<Windkraftanlage> sortiereNachLandkreisOrtName(List<Windkraftanlage> anlagen) {
        List<Windkraftanlage> kopie = kopiereListe(anlagen);
        bubbleSortLandkreisOrtName(kopie);
        return kopie;
    }

    /**
     * Sortiert nach Landkreis/Ort/Name, gibt ersten n Einträge und Dauer aus.
     * @param anlagen Eingabeliste
     * @param maxAnzeigen Maximale Anzahl der ausgegebenen Einträge
     * @return Dauer in Millisekunden
     */
    public static float sortiereNachLandkreisOrtNameUndGebeAus(List<Windkraftanlage> anlagen, int maxAnzeigen) {
        util.ZeitMessung timer = util.ZeitMessung.starte();
        List<Windkraftanlage> sortiert = sortiereNachLandkreisOrtName(anlagen);
        float dauer = timer.stoppeUndGibMillis();

        util.AusgabeManager.gebeUeberschriftAus(util.Konstanten.SORTIERUNG_LANDKREIS_ORT_NAME_UEBERSCHRIFT);
        util.AusgabeManager.gebeAusMitTrennzeichen(util.Konstanten.AUSGABE_ANZAHL, sortiert.size());
        util.AusgabeManager.gebeAusMitTrennzeichen(util.Konstanten.AUSGABE_DAUER_MS, dauer);
        util.AusgabeManager.gebeLeereZeileAus();

        int limit = Math.max(0, Math.min(maxAnzeigen, sortiert.size()));
        for (int i = 0; i < limit; i++) {
            util.AusgabeManager.gebeAus(sortiert.get(i).toString());
        }
        return dauer;
    }

    /**
     * Kopiert eine Liste (Shallow Copy).
     * 
     * Pre: anlagen darf nicht null sein
     * Post: Rueckgabe ist neue ArrayList mit gleichen Referenzen
     * 
     * @param anlagen Quellliste
     * @return neue Liste mit kopierten Referenzen
     */
    private static List<Windkraftanlage> kopiereListe(List<Windkraftanlage> anlagen) {
        List<Windkraftanlage> kopie = new ArrayList<>();
        for (Windkraftanlage anlage : anlagen) {
            kopie.add(anlage);
        }
        return kopie;
    }

    /**
     * Einfache Bubble-Sort nach Ort, Name, ID.
     * 
     * Pre: liste darf nicht null sein
     * Post: liste ist sortiert nach Ort, Name, ObjektId
     * 
     * @param liste Liste zum Sortieren (in-place)
     */
    private static void bubbleSortOrtNameId(List<Windkraftanlage> liste) {
        int groesse = liste.size();
        for (int i = 0; i < groesse - 1; i++) {
            for (int j = 0; j < groesse - i - 1; j++) {
                Windkraftanlage erste = liste.get(j);
                Windkraftanlage zweite = liste.get(j + 1);
                if (vergleicheOrtNameId(erste, zweite) > 0) {
                    liste.set(j, zweite);
                    liste.set(j + 1, erste);
                }
            }
        }
    }

    /**
     * Einfache Bubble-Sort nach Leistung, Baujahr, Name.
     * 
     * Pre: liste darf nicht null sein
     * Post: liste ist sortiert nach Leistung (desc), Baujahr, Name
     * 
     * @param liste Liste zum Sortieren (in-place)
     */
    private static void bubbleSortLeistungBaujahrName(List<Windkraftanlage> liste) {
        int groesse = liste.size();
        for (int i = 0; i < groesse - 1; i++) {
            for (int j = 0; j < groesse - i - 1; j++) {
                Windkraftanlage erste = liste.get(j);
                Windkraftanlage zweite = liste.get(j + 1);
                if (vergleicheLeistungBaujahrName(erste, zweite) > 0) {
                    liste.set(j, zweite);
                    liste.set(j + 1, erste);
                }
            }
        }
    }

    /**
     * Einfache Bubble-Sort nach Landkreis, Ort, Name.
     * 
     * Pre: liste darf nicht null sein
     * Post: liste ist sortiert nach Landkreis, Ort, Name
     * 
     * @param liste Liste zum Sortieren (in-place)
     */
    private static void bubbleSortLandkreisOrtName(List<Windkraftanlage> liste) {
        int groesse = liste.size();
        for (int i = 0; i < groesse - 1; i++) {
            for (int j = 0; j < groesse - i - 1; j++) {
                Windkraftanlage erste = liste.get(j);
                Windkraftanlage zweite = liste.get(j + 1);
                if (vergleicheLandkreisOrtName(erste, zweite) > 0) {
                    liste.set(j, zweite);
                    liste.set(j + 1, erste);
                }
            }
        }
    }

    /**
     * Vergleicht zwei Anlagen nach Ort, Name, ID.
     * 
     * Pre: erste und zweite duerfen nicht null sein
     * Post: Rueckgabe: -1 (erste kleiner), 0 (gleich), 1 (erste groesser)
     * 
     * @param erste erste Anlage
     * @param zweite zweite Anlage
     * @return Vergleichsergebnis
     */
    private static int vergleicheOrtNameId(Windkraftanlage erste, Windkraftanlage zweite) {
        int ortVergleich = VergleichsHelfer.vergleicheStringNullSicher(erste.getOrt(), zweite.getOrt());
        if (ortVergleich != 0) {
            return ortVergleich;
        }
        int nameVergleich = VergleichsHelfer.vergleicheStringNullSicher(erste.getName(), zweite.getName());
        if (nameVergleich != 0) {
            return nameVergleich;
        }
        return VergleichsHelfer.vergleicheInt(erste.getObjektId(), zweite.getObjektId());
    }

    /**
     * Vergleicht zwei Anlagen nach Leistung, Baujahr, Name.
     * 
     * Pre: erste und zweite duerfen nicht null sein
     * Post: Leistung absteigend, Baujahr aufsteigend, Name aufsteigend
     * 
     * @param erste erste Anlage
     * @param zweite zweite Anlage
     * @return Vergleichsergebnis
     */
    private static int vergleicheLeistungBaujahrName(Windkraftanlage erste, Windkraftanlage zweite) {
        int leistungVergleich = VergleichsHelfer.vergleicheFloatNullSicherAbsteigend(
            erste.getGesamtLeistungMW(), zweite.getGesamtLeistungMW());
        if (leistungVergleich != 0) {
            return leistungVergleich;
        }
        int baujahrVergleich = VergleichsHelfer.vergleicheIntegerNullSicher(erste.getBaujahr(), zweite.getBaujahr());
        if (baujahrVergleich != 0) {
            return baujahrVergleich;
        }
        return VergleichsHelfer.vergleicheStringNullSicher(erste.getName(), zweite.getName());
    }

    /**
     * Vergleicht zwei Anlagen nach Landkreis, Ort, Name.
     * 
     * Pre: erste und zweite duerfen nicht null sein
     * Post: Alle Kriterien aufsteigend sortiert
     * 
     * @param erste erste Anlage
     * @param zweite zweite Anlage
     * @return Vergleichsergebnis
     */
    private static int vergleicheLandkreisOrtName(Windkraftanlage erste, Windkraftanlage zweite) {
        int landkreisVergleich = VergleichsHelfer.vergleicheStringNullSicher(
            erste.getLandkreis(), zweite.getLandkreis());
        if (landkreisVergleich != 0) {
            return landkreisVergleich;
        }
        int ortVergleich = VergleichsHelfer.vergleicheStringNullSicher(erste.getOrt(), zweite.getOrt());
        if (ortVergleich != 0) {
            return ortVergleich;
        }
        return VergleichsHelfer.vergleicheStringNullSicher(erste.getName(), zweite.getName());
    }
}
