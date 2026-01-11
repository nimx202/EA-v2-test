package util;

import model.Graphknoten;
import model.Windkraftanlage;

import java.util.List;

/**
 * Schätzt fehlende Gesamtleistungswerte von Windkraftanlagen basierend auf Nachbaranlagen.
 * Nutzt den Windkraftanlagen-Graphen um ähnliche Anlagen in der Umgebung zu finden.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Leistungsschätzung
 * - KISS: Einfacher Durchschnitt der Nachbarn mit gleichen Eigenschaften
 * - Modularization: Getrennte Utility-Klasse für Schätzalgorithmus
 * 
 * Pre: Graph muss aufgebaut sein
 * Post: Fehlende Gesamtleistungswerte werden geschätzt
 */
public class LeistungsSchaetzer {

    /**
     * Privater Konstruktor verhindert Instanziierung der Utility-Klasse.
     */
    private LeistungsSchaetzer() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Ergänzt fehlende Gesamtleistungswerte in allen Anlagen des Graphen.
     * Verwendet Durchschnitt der Nachbaranlagen mit gleichem Hersteller und Typ.
     * 
     * Pre: graph darf nicht null sein
     * Post: Anlagen ohne Gesamtleistung bekommen geschätzten Wert (falls möglich)
     * 
     * @param graph Der Windkraftanlagen-Graph
     * @return Anzahl der ergänzten Werte
     */
    public static int ergaenzeFehlendeDaten(WindkraftanlageGraph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph darf nicht null sein");
        }

        int anzahlErgaenzt = 0;
        List<Graphknoten> knoten = graph.getKnoten();

        // Durchlaufe alle Knoten
        for (int i = 0; i < knoten.size(); i++) {
            Graphknoten knoten1 = knoten.get(i);
            Windkraftanlage anlage = knoten1.getAnlage();

            // Prüfe ob Gesamtleistung fehlt
            if (anlage.getGesamtLeistungMW() == null) {
                // Versuche Wert zu schätzen
                Float geschaetzterWert = schaetzeGesamtleistung(knoten1);
                
                if (geschaetzterWert != null) {
                    anlage.setGesamtLeistungMW(geschaetzterWert);
                    anzahlErgaenzt = anzahlErgaenzt + 1;
                }
            }
        }

        return anzahlErgaenzt;
    }

    /**
     * Schätzt die Gesamtleistung einer Anlage basierend auf ihren Nachbarn.
     * Verwendet Durchschnitt aller Nachbarn mit gleichem Typ,
     * die eine bekannte Gesamtleistung haben.
     * 
     * Pre: knoten darf nicht null sein
     * Post: Rückgabe ist geschätzter Wert oder null wenn keine Nachbarn verfügbar
     * 
     * @param knoten Der Knoten dessen Gesamtleistung geschätzt werden soll
     * @return Geschätzter Wert oder null
     */
    private static Float schaetzeGesamtleistung(Graphknoten knoten) {
        Windkraftanlage anlage = knoten.getAnlage();
        List<Graphknoten> nachbarn = knoten.getNachbarn();

        float summe = 0.0f;
        int anzahl = 0;

        // Durchlaufe alle Nachbarn
        for (int i = 0; i < nachbarn.size(); i++) {
            Graphknoten nachbarKnoten = nachbarn.get(i);
            Windkraftanlage nachbar = nachbarKnoten.getAnlage();

            // Prüfe ob Nachbar geeignet ist
            if (istNachbarGeeignet(anlage, nachbar)) {
                summe = summe + nachbar.getGesamtLeistungMW();
                anzahl = anzahl + 1;
            }
        }

        // Berechne Durchschnitt wenn Nachbarn gefunden
        if (anzahl > 0) {
            return summe / anzahl;
        }

        return null;
    }

    /**
     * Prüft ob ein Nachbar zur Schätzung geeignet ist.
     * Ein Nachbar ist geeignet wenn er:
     * - Eine bekannte Gesamtleistung hat
     * - Den gleichen Typ hat (oder beide unbekannt)
     * 
     * Pre: anlage und nachbar dürfen nicht null sein
     * Post: Rückgabe true wenn Nachbar geeignet ist
     * 
     * @param anlage Die Anlage für die geschätzt wird
     * @param nachbar Der potenzielle Nachbar
     * @return true wenn Nachbar geeignet ist
     */
    private static boolean istNachbarGeeignet(Windkraftanlage anlage, Windkraftanlage nachbar) {
        // Nachbar muss Gesamtleistung haben
        if (nachbar.getGesamtLeistungMW() == null) {
            return false;
        }

        // Prüfe Typ-Übereinstimmung
        boolean typPasst = sindWerteGleichOderBeideNull(
            anlage.getTyp(), 
            nachbar.getTyp()
        );

        return typPasst;
    }

    /**
     * Prüft ob zwei Werte gleich sind oder beide null.
     * 
     * Pre: -
     * Post: Rückgabe true wenn beide null oder beide gleich (equals)
     * 
     * @param wert1 Erster Wert
     * @param wert2 Zweiter Wert
     * @return true wenn Werte übereinstimmen
     */
    private static boolean sindWerteGleichOderBeideNull(String wert1, String wert2) {
        // Beide null
        if (wert1 == null && wert2 == null) {
            return true;
        }

        // Einer null, anderer nicht
        if (wert1 == null || wert2 == null) {
            return false;
        }

        // Beide nicht null - vergleiche
        return wert1.equals(wert2);
    }
}
