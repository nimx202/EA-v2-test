package view;

// Datei kodiert in UTF-8

import controler.WindkraftanlageRepository;
import model.Windkraftanlage;
import util.Konstanten;
import util.ZeitStatistiken;
import util.KoordinatenValidierungsService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Starterklasse der Anwendung.
 * Verwaltet das Einlesen der CSV-Datei und gibt Metriken zur Konsole aus.
 *
 * Verantwortlichkeiten:
 * - CSV-Datei laden
 * - Zeitmessung durchführen
 * - Statistiken anzeigen
 */
public class Main {

    /**
     * Hauptmethode der Anwendung.
     *
     * Pre: args[] kann leer sein (nicht verwendet)
     * Post: CSV-Datei wurde geladen und Statistiken ausgegeben
     *
     * @param args Kommandozeilenargumente (nicht verwendet)
     */
    public static void main(String[] args) {
        try {
            Path csvPfad = Paths.get(Konstanten.RESSOURCENPFAD);
            if (!Files.exists(csvPfad)) {
                gebeFehlerAus(Konstanten.CSV_NICHT_GEFUNDEN + csvPfad.toAbsolutePath());
                return;
            }

            WindkraftanlageRepository repo = new WindkraftanlageRepository();
            int geladenCount = ladeUndMesseRepository(repo, csvPfad);
            List<Windkraftanlage> alleAnlagen = repo.getAll();

            // Koordinaten-Validierung und -Korrektur mit separatem Service
            long startValidierung = System.nanoTime();
            KoordinatenValidierungsService.validiereUndKorrigiere(repo);
            double validierungMillis = (System.nanoTime() - startValidierung) / Konstanten.NANOS_ZU_MILLIS;
            ZeitStatistiken.zeichneZeitAuf("Validierung und Korrektur (gesamt)", validierungMillis);

            gebeStatistikenAus(alleAnlagen);
            gebeTopWindparksAus(alleAnlagen);
            gebeBeispielDatensätzeAus(alleAnlagen);

            // Gesamte Zusammenfassung am Ende anzeigen
            ZeitStatistiken.druckeZusammenfassung();

        } catch (Exception e) {
            gebeFehlerAus(Konstanten.FEHLER_PREFIX + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lädt die CSV-Datei und misst die benötigte Zeit.
     *
     * Pre: repo nicht null; csvPfad existiert
     * Post: Daten sind in repo geladen; Zeitmessung wurde ausgegeben
     *
     * @param repo das Repository zum Laden
     * @param csvPfad Pfad zur CSV-Datei
     * @return Anzahl geladener Datensätze
     * @throws Exception bei I/O-Fehler
     */
    private static int ladeUndMesseRepository(WindkraftanlageRepository repo, Path csvPfad) throws Exception {
        long startZeit = System.nanoTime();
        int count = repo.ladeAusCsv(csvPfad.toString());
        long endZeit = System.nanoTime();
        double verstrricheneMillis = (endZeit - startZeit) / Konstanten.NANOS_ZU_MILLIS;

        System.out.println(Konstanten.LADEN_ABGESCHLOSSEN);
        System.out.println(Konstanten.DATEI_INFO + csvPfad.toAbsolutePath());
        System.out.printf(Konstanten.DATENSÄTZE_ANZAHL, count);
        System.out.printf(Konstanten.VERSTRICHENE_ZEIT, verstrricheneMillis);

        // Record time and stats
        ZeitStatistiken.zeichneZeitAuf("Laden CSV", verstrricheneMillis);
        ZeitStatistiken.zeichneStat("Geladene Datensätze", String.valueOf(count));

        return count;
    }

    /**
     * Gibt Basishtatistiken zu den Anlagen aus.
     *
     * Pre: alleAnlagen nicht null
     * Post: Statistiken wurden auf Konsole ausgegeben
     *
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    private static void gebeStatistikenAus(List<Windkraftanlage> alleAnlagen) {
        long start = System.nanoTime();
        long anlagenMitKoordinaten = zähleMitKoordinaten(alleAnlagen);
        long anlagenOhneBetreiber = zähleOhneBetreiber(alleAnlagen);

        System.out.printf(Konstanten.MIT_KOORDINATEN, anlagenMitKoordinaten);
        System.out.printf(Konstanten.OHNE_BETREIBER, anlagenOhneBetreiber);

        double ms = (System.nanoTime() - start) / Konstanten.NANOS_ZU_MILLIS;
        ZeitStatistiken.zeichneZeitAuf("gebeStatistikenAus", ms);
        ZeitStatistiken.zeichneStat("Anlagen mit Koordinaten", String.valueOf(anlagenMitKoordinaten));
        ZeitStatistiken.zeichneStat("Anlagen ohne Betreiber", String.valueOf(anlagenOhneBetreiber));
    }

    /**
     * Zählt Anlagen die Koordinaten haben.
     *
     * Pre: anlagen nicht null
     * Post: Rückgabe Anzahl Anlagen mit Lat/Lon
     *
     * @param anlagen Liste aller Anlagen
     * @return Anzahl mit Koordinaten
     */
    private static long zähleMitKoordinaten(List<Windkraftanlage> anlagen) {
        return anlagen.stream()
                .filter(w -> w.getBreitengrad() != null && w.getLaengengrad() != null)
                .count();
    }

    /**
     * Zählt Anlagen ohne Betreiber-Angabe.
     *
     * Pre: anlagen nicht null
     * Post: Rückgabe Anzahl Anlagen ohne Betreiber
     *
     * @param anlagen Liste aller Anlagen
     * @return Anzahl ohne Betreiber
     */
    private static long zähleOhneBetreiber(List<Windkraftanlage> anlagen) {
        return anlagen.stream()
                .filter(w -> w.getBetreiber() == null)
                .count();
    }

    /**
     * Gibt die Top-Windparks/Anlagen nach Anzahl aus.
     *
     * Pre: alleAnlagen nicht null
     * Post: Top 5 Windparks werden auf Konsole ausgegeben
     *
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    private static void gebeTopWindparksAus(List<Windkraftanlage> alleAnlagen) {
        long start = System.nanoTime();
        Map<String, Long> parkNachName = gruppierAnlagenNachNamen(alleAnlagen);

        System.out.printf(Konstanten.TOP_PARKS, Konstanten.TOP_LIMIT);
        parkNachName.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .limit(Konstanten.TOP_LIMIT)
                .forEach(e -> System.out.println(e.getKey() + Konstanten.AUSGABE_TRENNZEICHEN + e.getValue()));
        System.out.printf(Konstanten.GESAMT_PARKS, parkNachName.size());

        double ms = (System.nanoTime() - start) / Konstanten.NANOS_ZU_MILLIS;
        ZeitStatistiken.zeichneZeitAuf("gebeTopWindparksAus", ms);
        ZeitStatistiken.zeichneStat("Anzahl Parks (einzigartig)", String.valueOf(parkNachName.size()));

        // record top-n as a compact stat
        String topN = parkNachName.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .limit(Konstanten.TOP_LIMIT)
                .map(e -> e.getKey() + "(" + e.getValue() + ")")
                .collect(java.util.stream.Collectors.joining(", "));
        ZeitStatistiken.zeichneStat("Top " + Konstanten.TOP_LIMIT + " Parks", topN);
    }

    /**
     * Gruppiert Anlagen nach ihrem Namen (Windpark/Anlagenname).
     *
     * Pre: anlagen nicht null
     * Post: Rückgabe Map mit Namen als Key und Häufigkeit als Value
     *
     * @param anlagen Liste aller Anlagen
     * @return Map Name -> Anzahl
     */
    private static Map<String, Long> gruppierAnlagenNachNamen(List<Windkraftanlage> anlagen) {
        return anlagen.stream()
                .map(w -> w.getName() == null ? Konstanten.UNBEKANNTER_ORT : w.getName())
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
    }

    /**
     * Gibt Beispiel-Datensätze aus.
     *
     * Pre: alleAnlagen nicht null
     * Post: Bis zu 10 Beispiel-Records wurden ausgegeben
     *
     * @param alleAnlagen Liste aller Windkraftanlagen
     */
    private static void gebeBeispielDatensätzeAus(List<Windkraftanlage> alleAnlagen) {
        long start = System.nanoTime();
        System.out.println(Konstanten.BEISPIEL_DATENSÄTZE);
        alleAnlagen.stream()
                .limit(Konstanten.BEISPIEL_LIMIT)
                .forEach(System.out::println);
        long count = Math.min(alleAnlagen.size(), Konstanten.BEISPIEL_LIMIT);

        double ms = (System.nanoTime() - start) / Konstanten.NANOS_ZU_MILLIS;
        ZeitStatistiken.zeichneZeitAuf("gebeBeispielDatensätzeAus", ms);
        ZeitStatistiken.zeichneStat("Anzahl Beispiel-Datensätze ausgegeben", String.valueOf(count));
    }

    /**
     * Gibt eine Fehlermeldung aus.
     *
     * Pre: meldung nicht null
     * Post: Fehlermeldung auf System.err ausgegeben
     *
     * @param meldung die Fehlermeldung
     */
    private static void gebeFehlerAus(String meldung) {
        System.err.println(meldung);
    }
}
