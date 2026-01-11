package util;

import model.Graphknoten;

import java.util.List;

/**
 * Gibt Graph-Statistiken und Informationen aus.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Graph-Ausgabe
 * - KISS: Einfache Zähl- und Ausgabelogik
 * - Modularization: Getrennte Utility-Klasse für Graph-Ausgabe
 * 
 * Pre: -
 * Post: Graph-Informationen werden auf Konsole ausgegeben
 */
public class GraphAusgabe {

    /**
     * Privater Konstruktor verhindert Instanziierung der Utility-Klasse.
     */
    private GraphAusgabe() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Gibt eine Zusammenfassung des Graphen aus.
     * Zeigt Anzahl Knoten, Kanten, durchschnittlicher Grad und Beispiel-Nachbarschaften.
     * 
     * Pre: graph darf nicht null sein
     * Post: Graph-Statistiken sind ausgegeben
     * 
     * @param graph Der Windkraftanlagen-Graph
     */
    public static void gebeGraphZusammenfassungAus(WindkraftanlageGraph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph darf nicht null sein");
        }

        int anzahlKnoten = graph.getAnzahlKnoten();
        int anzahlKanten = graph.getAnzahlKanten();
        float maxDistanz = graph.getMaxDistanzKm();

        AusgabeManager.gebeAusFormat(Konstanten.GRAPH_UEBERSCHRIFT);
        AusgabeManager.gebeAusFormat(Konstanten.GRAPH_MAX_DISTANZ, maxDistanz);
        AusgabeManager.gebeAusFormat(Konstanten.GRAPH_ANZAHL_KNOTEN, anzahlKnoten);
        AusgabeManager.gebeAusFormat(Konstanten.GRAPH_ANZAHL_KANTEN, anzahlKanten);

        // Berechne und gebe durchschnittlichen Grad aus
        if (anzahlKnoten > 0) {
            double durchschnittGrad = berechneDurchschnittlicherGrad(graph);
            AusgabeManager.gebeAusFormat(Konstanten.GRAPH_DURCHSCHNITT_GRAD, durchschnittGrad);
        }

        // Zeige Beispiele von Nachbarschaften
        gebeBeispielNachbarschaftenAus(graph);
    }

    /**
     * Berechnet den durchschnittlichen Grad der Knoten im Graphen.
     * Der Grad eines Knotens ist die Anzahl seiner Nachbarn.
     * 
     * Pre: graph darf nicht null sein und muss Knoten enthalten
     * Post: Rückgabe ist durchschnittlicher Grad
     * 
     * @param graph Der Windkraftanlagen-Graph
     * @return Durchschnittlicher Grad
     */
    private static float berechneDurchschnittlicherGrad(WindkraftanlageGraph graph) {
        List<Graphknoten> knoten = graph.getKnoten();
        int summeGrad = 0;

        for (int i = 0; i < knoten.size(); i++) {
            Graphknoten k = knoten.get(i);
            summeGrad = summeGrad + k.getAnzahlNachbarn();
        }

        return (float) summeGrad / knoten.size();
    }

    /**
     * Gibt Beispiele von Nachbarschaften aus (erste N Knoten).
     * 
     * Pre: graph darf nicht null sein
     * Post: Beispiele sind ausgegeben
     * 
     * @param graph Der Windkraftanlagen-Graph
     */
    private static void gebeBeispielNachbarschaftenAus(WindkraftanlageGraph graph) {
        List<Graphknoten> knoten = graph.getKnoten();
        int anzahlBeispiele = Math.min(Konstanten.GRAPH_BEISPIEL_ANZAHL, knoten.size());

        if (anzahlBeispiele > 0) {
            AusgabeManager.gebeAusFormat(Konstanten.GRAPH_BEISPIELE_UEBERSCHRIFT);

            for (int i = 0; i < anzahlBeispiele; i++) {
                Graphknoten k = knoten.get(i);
                gebeKnotenDetailsAus(k, i + 1);
            }
        }
    }

    /**
     * Gibt Details eines Knotens aus (Anlage und Nachbarn).
     * 
     * Pre: knoten darf nicht null sein
     * Post: Knoten-Details sind ausgegeben
     * 
     * @param knoten Der auszugebende Knoten
     * @param nummer Fortlaufende Nummer für die Ausgabe
     */
    private static void gebeKnotenDetailsAus(Graphknoten knoten, int nummer) {
        String anlageName = knoten.getAnlage().getName();
        String ort = knoten.getAnlage().getOrt();
        int anzahlNachbarn = knoten.getAnzahlNachbarn();

        AusgabeManager.gebeAusFormat(Konstanten.GRAPH_KNOTEN_INFO, 
                                     nummer, anlageName, ort, anzahlNachbarn);
    }

    /**
     * Gibt Statistiken über die Leistungsschätzung aus.
     * 
     * Pre: -
     * Post: Statistiken sind ausgegeben
     * 
     * @param anzahlErgaenzt Anzahl der ergänzten Werte
     * @param gesamtAnzahl Gesamtanzahl der Anlagen
     */
    public static void gebeSchaetzStatistikenAus(int anzahlErgaenzt, int gesamtAnzahl) {
        AusgabeManager.gebeAusFormat(Konstanten.SCHAETZUNG_UEBERSCHRIFT);
        AusgabeManager.gebeAusFormat(Konstanten.SCHAETZUNG_ERGAENZT, anzahlErgaenzt);
        
        if (gesamtAnzahl > 0) {
            float prozent =  anzahlErgaenzt * 100.0f / gesamtAnzahl;
            AusgabeManager.gebeAusFormat(Konstanten.SCHAETZUNG_PROZENT, prozent);
        }
    }
}
