package view;

import controler.WindkraftanlageRepository;
import model.Windkraftanlage;
import util.Konstanten;

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

            gebeStatistikenAus(alleAnlagen);
            gebeTopWindparksAus(alleAnlagen);
            gebeBeispielDatensätzeAus(alleAnlagen);

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
        long anlagenMitKoordinaten = zähleMitKoordinaten(alleAnlagen);
        long anlagenOhneBetreiber = zähleOhneBetreiber(alleAnlagen);

        System.out.printf(Konstanten.MIT_KOORDINATEN, anlagenMitKoordinaten);
        System.out.printf(Konstanten.OHNE_BETREIBER, anlagenOhneBetreiber);
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
        Map<String, Long> parkNachName = gruppierAnlagenNachNamen(alleAnlagen);

        System.out.printf(Konstanten.TOP_PARKS, Konstanten.TOP_LIMIT);
        parkNachName.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .limit(Konstanten.TOP_LIMIT)
                .forEach(e -> System.out.println(e.getKey() + Konstanten.AUSGABE_TRENNZEICHEN + e.getValue()));
        System.out.printf(Konstanten.GESAMT_PARKS, parkNachName.size());
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
        System.out.println(Konstanten.BEISPIEL_DATENSÄTZE);
        alleAnlagen.stream()
                .limit(Konstanten.BEISPIEL_LIMIT)
                .forEach(System.out::println);
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
