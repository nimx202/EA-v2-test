package controler;

import model.Windkraftanlage;
import util.Konstanten;
import util.KonstantenParser;
import util.FeldParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Repository zum Einlesen und Verwalten von Windkraftanlagen-Daten.
 * Verwendet nur Java I/O Streams aus der Standardbibliothek und delegiert
 * CSV-Parsing an spezialisierte Klassen (Separation of Concerns).
 *
 * Vertrag:
 * Pre: CSV-Datei muss existieren und lesbar sein
 * Post: getAll() liefert unveränderliche Liste aller eingelesenen Datensätze
 */
public class WindkraftanlageRepository {

    private final List<Windkraftanlage> anlagen = new ArrayList<>();

    /**
     * Konstruktor erstellt ein leeres Repository.
     *
     * Post: Leeres Repository mit 0 Datensätzen
     */
    public WindkraftanlageRepository() {
        // Leerer Konstruktor
    }

    /**
     * Liest die CSV-Datei ein und füllt das Repository.
     *
     * Pre: dateipfad existiert und ist lesbar; muss nicht null sein
     * Post: getAll() liefert alle eingelesenen Datensätze;
     *       Rückgabe: Anzahl eingelesener Datensätze
     *
     * @param dateipfad Pfad zur CSV-Datei
     * @return Anzahl eingelesener Datensätze
     * @throws Exception bei I/O-Fehlern
     */
    public int ladeAusCsv(String dateipfad) throws Exception {
        Objects.requireNonNull(dateipfad, "dateipfad must not be null");

        anlagen.clear();

        try (BufferedReader leser = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(dateipfad),
                        StandardCharsets.UTF_8))) {

            // Lese Header (erste Zeile)
            String kopfzeile = KonstantenParser.leseNächstenDatensatz(leser);
            if (kopfzeile == null) {
                return 0;
            }

            // Lese Datensätze
            List<Windkraftanlage> geparsteDatensätze = leseAlleeDatensätze(leser);
            anlagen.addAll(geparsteDatensätze);
            return anlagen.size();
        }
    }

    /**
     * Liest alle Datensätze aus dem Reader bis EOF.
     *
     * Pre: leser ist nicht null und positioniert nach Header
     * Post: Rückgabe Liste mit allen gültigen Windkraftanlage-Objekten
     *
     * @param leser BufferedReader positioniert nach Header
     * @return Liste mit eingelesenen Anlagen
     * @throws Exception bei I/O-Fehlern
     */
    private List<Windkraftanlage> leseAlleeDatensätze(BufferedReader leser) throws Exception {
        List<Windkraftanlage> datensätze = new ArrayList<>();
        String datensatzZeile;

        while ((datensatzZeile = KonstantenParser.leseNächstenDatensatz(leser)) != null) {
            String gekürzt = datensatzZeile.trim();
            if (!gekürzt.isEmpty()) {
                Windkraftanlage anlage = parsenCsvZeile(datensatzZeile);
                if (anlage != null) {
                    datensätze.add(anlage);
                }
            }
        }

        return datensätze;
    }

    /**
     * Parst eine CSV-Zeile in ein Windkraftanlage-Objekt.
     *
     * Pre: zeile ist nicht null
     * Post: Rückgabe Windkraftanlage-Objekt oder null bei Parsing-Fehler
     *
     * @param zeile CSV-Zeile
     * @return Windkraftanlage oder null
     */
    private Windkraftanlage parsenCsvZeile(String zeile) {
        if (zeile == null) {
            return null;
        }

        String[] teile = KonstantenParser.teileZeileRespektivierAnführungszeichen(zeile);
        if (!istGültigZeilenlänge(teile)) {
            return null;
        }

        try {
            return erstelleWindkraftanlage(teile);
        } catch (Exception e) {
            // Ungültige Zeile wird verworfen
            return null;
        }
    }

    /**
     * Prüft, ob eine geparste CSV-Zeile die richtige Feldanzahl hat.
     *
     * Pre: teile ist nicht null
     * Post: true wenn Feldanzahl >= erwartet
     *
     * @param teile geparste CSV-Felder
     * @return true wenn gültige Feldanzahl
     */
    private boolean istGültigZeilenlänge(String[] teile) {
        return teile != null && teile.length >= Konstanten.ERWARTET_FELDANZAHL;
    }

    /**
     * Erstellt ein Windkraftanlage-Objekt aus geparsten CSV-Feldern.
     *
     * Pre: teile hat mindestens ERWARTET_FELDANZAHL Elemente
     * Post: Rückgabe vollständig initialisiertes Windkraftanlage-Objekt
     *
     * @param teile geparste CSV-Felder
     * @return Windkraftanlage-Objekt
     */
    private Windkraftanlage erstelleWindkraftanlage(String[] teile) {
        int idx = 0;

        int objektId = FeldParser.parseGanzzahlSicher(teile[idx++]);
        String name = FeldParser.leerZuNull(KonstantenParser.bereinigesFeld(teile[idx++]));
        Integer baujahr = FeldParser.parseGanzzahlNullbar(
                KonstantenParser.bereinigesFeld(teile[idx++]));
        Double gesamtLeistungMW = FeldParser.parseGleitkommaZahlNullbar(
                KonstantenParser.bereinigesFeld(teile[idx++]));
        Integer anzahl = FeldParser.parseGanzzahlNullbar(
                KonstantenParser.bereinigesFeld(teile[idx++]));
        String typ = FeldParser.leerZuNull(KonstantenParser.bereinigesFeld(teile[idx++]));
        String ort = FeldParser.leerZuNull(KonstantenParser.bereinigesFeld(teile[idx++]));
        String landkreis = FeldParser.leerZuNull(KonstantenParser.bereinigesFeld(teile[idx++]));
        Double breitengrad = FeldParser.parseGleitkommaZahlNullbar(
                KonstantenParser.bereinigesFeld(teile[idx++]));
        Double laengengrad = FeldParser.parseGleitkommaZahlNullbar(
                KonstantenParser.bereinigesFeld(teile[idx++]));
        String betreiber = FeldParser.leerZuNull(KonstantenParser.bereinigesFeld(teile[idx++]));
        String bemerkungen = FeldParser.leerZuNull(KonstantenParser.bereinigesFeld(teile[idx++]));

        return new Windkraftanlage(objektId, name, baujahr, gesamtLeistungMW,
                anzahl, typ, ort, landkreis, breitengrad, laengengrad,
                betreiber, bemerkungen);
    }

    /**
     * Liefert eine unveränderliche Liste aller eingelesenen Windkraftanlagen.
     *
     * Pre: keine
     * Post: Rückgabe nicht-null, unveränderliche Liste
     *
     * @return Liste der Windkraftanlagen
     */
    public List<Windkraftanlage> getAll() {
        return Collections.unmodifiableList(anlagen);
    }

    /**
     * Liefert die Anzahl der aktuell geladenen Datensätze.
     *
     * Pre: keine
     * Post: Rückgabe Anzahl >= 0
     *
     * @return Anzahl Datensätze
     */
    public int count() {
        return anlagen.size();
    }
}
