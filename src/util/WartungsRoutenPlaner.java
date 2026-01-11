package util;

import model.Windkraftanlage;

import java.util.List;
import java.util.Map;

/**
 * Hauptklasse fuer die Wartungsplanung eines Herstellers.
 * Koordiniert Graph-Aufbau, Cluster-Erkennung, Routen-Optimierung und Zeitberechnung.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Wartungsplanung orchestrieren
 * - KISS: Delegiert an spezialisierte Utility-Klassen
 * - Modularisierung: Zentrale Koordinationsklasse
 * 
 * Pre: Anlagenliste muss initialisiert sein
 * Post: Liefert kompletten Wartungsplan mit Routen und Zeitbedarf
 */
public final class WartungsRoutenPlaner {

    /**
     * Privater Konstruktor verhindert Instanziierung der Utility-Klasse.
     */
    private WartungsRoutenPlaner() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Plant die Wartung fuer alle Anlagen eines Herstellers.
     * Erkennt Cluster, optimiert Routen pro Cluster und berechnet Gesamtzeit.
     * 
     * Pre: herstellerAnlagen darf nicht null sein
     * Post: Gibt Wartungsplan auf Konsole aus und gibt Gesamttage zurueck
     * 
     * @param herstellerAnlagen Liste aller Anlagen eines Herstellers
     * @param herstellerName Name des Herstellers fuer Ausgabe
     * @return Gesamtanzahl benoetiger Wartungstage
     */
    public static int planeWartung(List<Windkraftanlage> herstellerAnlagen, String herstellerName) {
        if (herstellerAnlagen == null || herstellerAnlagen.isEmpty()) {
            return 0;
        }
        
        // Ueberschrift ausgeben
        AusgabeManager.gebeAusFormat(Konstanten.WARTUNG_HERSTELLER_UEBERSCHRIFT, 
            herstellerName, herstellerAnlagen.size());
        
        // Schritt 1: Erkenne Cluster
        List<List<Windkraftanlage>> clusters = WartungsClusterDetector.erkenneClustersAusAnlagen(herstellerAnlagen);
        
        // Cluster-Info ausgeben
        AusgabeManager.gebeAusFormat(Konstanten.WARTUNG_CLUSTER_INFO, clusters.size());
        
        // Schritt 2: Verarbeite jeden Cluster einzeln
        int gesamtTage = 0;
        
        for (int clusterNr = 0; clusterNr < clusters.size(); clusterNr++) {
            List<Windkraftanlage> cluster = clusters.get(clusterNr);
            int tage = verarbeiteCluster(cluster, clusterNr + 1, clusters);
            gesamtTage += tage;
        }
        
        // Gesamtergebnis ausgeben
        AusgabeManager.gebeAusFormat(Konstanten.WARTUNG_TAGE_GESAMT, gesamtTage);
        
        return gesamtTage;
    }

    /**
     * Verarbeitet einen einzelnen Cluster: Route optimieren, Warnungen pruefen, Zeit berechnen.
     * 
     * Pre: cluster nicht null und nicht leer
     * Post: Route ist optimiert und ausgegeben, Tage sind berechnet
     * 
     * @param cluster Liste der Anlagen im Cluster
     * @param clusterNummer Nummer des Clusters (1-basiert)
     * @param alleClusters Alle Cluster (fuer Distanz-Warnungen)
     * @return Anzahl benoetiger Tage fuer diesen Cluster
     */
    private static int verarbeiteCluster(List<Windkraftanlage> cluster, int clusterNummer,
                                         List<List<Windkraftanlage>> alleClusters) {
        // Cluster-Details ausgeben
        AusgabeManager.gebeAusFormat(Konstanten.WARTUNG_CLUSTER_DETAILS, clusterNummer, cluster.size());
        
        // Warnung wenn Cluster isoliert ist
        if (alleClusters.size() > 1) {
            gebeClusterWarnungAus(cluster, clusterNummer, alleClusters);
        }
        
        // Route optimieren
        List<Windkraftanlage> route = RoutenOptimierer.erstelleOptimierteRoute(cluster);
        
        // Startanlage ausgeben
        if (!route.isEmpty()) {
            Windkraftanlage start = route.get(0);
            String ort = start.getOrt();
            if (ort == null) {
                ort = Konstanten.UNBEKANNTER_ORT;
            }
            AusgabeManager.gebeAusFormat(Konstanten.WARTUNG_START_ANLAGE, 
                start.getObjektId(), start.getName(), ort);
        }
        
        // Routenlaenge ausgeben
        float routenLaenge = RoutenOptimierer.berechneGesamtDistanz(route);
        AusgabeManager.gebeAusFormat(Konstanten.WARTUNG_ROUTEN_LAENGE, routenLaenge);
        
        // Warnungen fuer zu lange Strecken
        List<String> warnungen = WartungsZeitBerechner.pruefeTransportDistanzen(route);
        for (int i = 0; i < warnungen.size(); i++) {
            AusgabeManager.gebeAus(warnungen.get(i));
        }
        
        // Wartungstage berechnen
        int tage = WartungsZeitBerechner.berechneGesamtdauer(route);
        AusgabeManager.gebeAusFormat(Konstanten.WARTUNG_TAGE_CLUSTER, clusterNummer, tage);
        
        // Arbeitsplan erstellen und ausgeben
        gebeArbeitsplanAus(route);
        
        return tage;
    }

    /**
     * Gibt Warnung fuer isolierte Cluster aus.
     * 
     * Pre: cluster und alleClusters nicht null
     * Post: Warnung wurde ausgegeben wenn Cluster isoliert
     * 
     * @param cluster Der aktuelle Cluster
     * @param clusterNummer Nummer des Clusters
     * @param alleClusters Alle Cluster
     */
    private static void gebeClusterWarnungAus(List<Windkraftanlage> cluster, int clusterNummer,
                                              List<List<Windkraftanlage>> alleClusters) {
        // Finde minimale Distanz zu anderen Clustern
        float minDistanz = Float.MAX_VALUE;
        
        for (int i = 0; i < alleClusters.size(); i++) {
            List<Windkraftanlage> andererCluster = alleClusters.get(i);
            
            // Ueberspringe eigenen Cluster
            if (andererCluster == cluster) {
                continue;
            }
            
            float distanz = WartungsClusterDetector.berechneClusterDistanz(cluster, andererCluster);
            
            if (distanz < minDistanz) {
                minDistanz = distanz;
            }
        }
        
        // Warnung wenn Distanz > Transportlimit
        if (minDistanz > Konstanten.MAX_TRANSPORT_DISTANZ_KM) {
            AusgabeManager.gebeAusFormat(Konstanten.WARTUNG_CLUSTER_WARNUNG,
                clusterNummer, cluster.size(), minDistanz);
        }
    }

    /**
     * Gibt den Arbeitsplan fuer eine Route aus.
     * 
     * Pre: route nicht null
     * Post: Arbeitsplan wurde auf Konsole ausgegeben
     * 
     * @param route Die Wartungsroute
     */
    private static void gebeArbeitsplanAus(List<Windkraftanlage> route) {
        List<List<Integer>> arbeitsplan = WartungsZeitBerechner.erstelleArbeitsplan(route);
        
        for (int tag = 0; tag < arbeitsplan.size(); tag++) {
            List<Integer> tagesAnlagen = arbeitsplan.get(tag);
            String anlagenString = WartungsZeitBerechner.formatiereTagesAnlagen(tagesAnlagen);
            AusgabeManager.gebeAusFormat(Konstanten.WARTUNG_TAG_FORMAT, tag + 1, anlagenString);
        }
    }

    /**
     * Plant die Wartung fuer mehrere Hersteller und gibt Gesamtstatistik aus.
     * 
     * Pre: alleAnlagen nicht null, topAnzahl > 0
     * Post: Wartungsplaene fuer Top N Hersteller wurden ausgegeben
     * 
     * @param alleAnlagen Liste aller Windkraftanlagen
     * @param topAnzahl Anzahl der zu analysierenden Top-Hersteller
     * @return Map mit Hersteller -> benoetite Tage
     */
    public static Map<String, Integer> planeWartungFuerTopHersteller(List<Windkraftanlage> alleAnlagen, 
                                                                      int topAnzahl) {
        Map<String, Integer> ergebnis = new java.util.HashMap<>();
        
        if (alleAnlagen == null || alleAnlagen.isEmpty()) {
            return ergebnis;
        }
        
        // Ueberschrift
        AusgabeManager.gebeSektionAus(Konstanten.WARTUNG_UEBERSCHRIFT);
        
        // Gruppiere nach Hersteller
        ZeitMessung timer = ZeitMessung.starte();
        
        Map<String, List<Windkraftanlage>> herstellerGruppen = 
            HerstellerGruppierer.gruppiereNachHersteller(alleAnlagen);
        
        Map<String, Integer> herstellerZaehler = 
            HerstellerGruppierer.zaehleAnlagenProHersteller(alleAnlagen);
        
        float gruppierungsZeit = timer.stoppeUndGibMillis();
        ZeitStatistiken.zeichneZeitAuf(Konstanten.OPERATION_HERSTELLER_GRUPPIERUNG, gruppierungsZeit);
        ZeitStatistiken.zeichneStat(Konstanten.STAT_ANZAHL_HERSTELLER, 
            String.valueOf(herstellerGruppen.size()));
        
        // Hole Top N Hersteller
        List<String> topHersteller = HerstellerGruppierer.holeTopHersteller(herstellerZaehler, topAnzahl);
        
        // Plane Wartung fuer jeden Top-Hersteller
        int gesamtTageAlleHersteller = 0;
        
        for (int i = 0; i < topHersteller.size(); i++) {
            String hersteller = topHersteller.get(i);
            List<Windkraftanlage> herstellerAnlagen = herstellerGruppen.get(hersteller);
            
            ZeitMessung routenTimer = ZeitMessung.starte();
            int tage = planeWartung(herstellerAnlagen, hersteller);
            float routenZeit = routenTimer.stoppeUndGibMillis();
            
            AusgabeManager.gebeAusFormat(Konstanten.WARTUNG_BERECHNUNGSZEIT, routenZeit);
            
            ergebnis.put(hersteller, tage);
            gesamtTageAlleHersteller += tage;
        }
        
        // Gesamtstatistik
        String statKey = String.format(Konstanten.STAT_WARTUNGSTAGE_GESAMT, topAnzahl);
        ZeitStatistiken.zeichneStat(statKey, String.valueOf(gesamtTageAlleHersteller));
        
        return ergebnis;
    }
}

