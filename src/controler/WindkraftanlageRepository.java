package controler;

import model.Windkraftanlage;
import util.CsvParser;
import util.FeldParser;
import util.KoordinatenValidierer;
import util.KoordinatenKorrekturTracker;
import util.Konstanten;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository für Windkraftanlagen-Daten.
 * Verwaltet das Einlesen und Speichern von Windkraftanlagen aus CSV-Dateien.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Datenverwaltung
 * - Encapsulation: Private Liste, öffentliche Methoden
 * - KISS: Einfaches Laden und Speichern
 * 
 * Pre: CSV-Datei muss existieren und korrekt formatiert sein
 * Post: Liefert Liste aller Windkraftanlagen
 */
public class WindkraftanlageRepository {

    private final List<Windkraftanlage> alleAnlagen = new ArrayList<>();

    /**
     * Erstellt ein leeres Repository.
     * 
     * Pre: keine
     * Post: Leeres Repository erstellt
     */
    public WindkraftanlageRepository() {
    }

    /**
     * Liest CSV-Datei ein und füllt das Repository.
     * Überspringt die Kopfzeile und leere Zeilen.
     * 
     * Pre: csvDateipfad nicht null; Datei existiert
     * Post: Repository gefüllt mit geladenen Anlagen
     * 
     * @param csvDateipfad Pfad zur CSV-Datei
     * @return Anzahl geladener Datensätze
     * @throws Exception bei Lesefehlern
     */
    public int ladeAusCsv(String csvDateipfad) throws Exception {
        alleAnlagen.clear();

        BufferedReader csvLeser = new BufferedReader(
            new InputStreamReader(
                new FileInputStream(csvDateipfad),
                StandardCharsets.UTF_8));

        try {
            // Überspringe Kopfzeile
            String kopfZeile = CsvParser.leseNaechstenDatensatz(csvLeser);
            if (kopfZeile == null) {
                csvLeser.close();
                return 0;
            }

            // Lese alle Datenzeilen
            String datenZeile;
            while ((datenZeile = CsvParser.leseNaechstenDatensatz(csvLeser)) != null) {
                String zeileOhneLeerzeichen = datenZeile.trim();
                
                if (!zeileOhneLeerzeichen.isEmpty()) {
                    Windkraftanlage neueAnlage = erstelleAnlageAusZeile(datenZeile);
                    
                    if (neueAnlage != null) {
                        alleAnlagen.add(neueAnlage);
                    }
                }
            }
        } finally {
            csvLeser.close();
        }

        return alleAnlagen.size();
    }

    /**
     * Erstellt ein Windkraftanlage-Objekt aus einer CSV-Zeile.
     * Parst alle Felder und erstellt ein neues Objekt.
     * 
     * Pre: csvZeile nicht null
     * Post: Rückgabe: Windkraftanlage oder null bei Fehler
     * 
     * @param csvZeile Die CSV-Zeile
     * @return Windkraftanlage oder null bei Fehler
     */
    private Windkraftanlage erstelleAnlageAusZeile(String csvZeile) {
        if (csvZeile == null) {
            return null;
        }

        // Teile Zeile in Felder
        String[] csvFelder = CsvParser.teileZeileInFelder(csvZeile);
        
        // Prüfe ob genug Felder vorhanden sind
        if (csvFelder == null || csvFelder.length < Konstanten.ERWARTET_FELDANZAHL) {
            return null;
        }

        try {
            int feldIndex = 0;

            // Parse jedes Feld einzeln
            int objektId = FeldParser.parseGanzzahlSicher(csvFelder[feldIndex++]);
            String name = FeldParser.leerZuNull(CsvParser.bereinigesFeld(csvFelder[feldIndex++]));
            String baujahr = FeldParser.leerZuNull(CsvParser.bereinigesFeld(csvFelder[feldIndex++]));
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

            // Erstelle neue Anlage
            return new Windkraftanlage(objektId, name, baujahr, gesamtLeistungMW,
                anzahl, typ, ort, landkreis, breitengrad, laengengrad,
                betreiber, bemerkungen);
                
        } catch (Exception fehler) {
            return null;
        }
    }

    /**
     * Liefert alle geladenen Windkraftanlagen.
     *
     * @return Liste aller Anlagen
     */
    public List<Windkraftanlage> getAll() {
        return new ArrayList<>(alleAnlagen);
    }

    /**
     * Liefert Anzahl der geladenen Datensätze.
     *
     * @return Anzahl Anlagen
     */
    public int count() {
        return alleAnlagen.size();
    }

    /** und zeichnet Änderungen auf.
     * Teilt zu große Werte durch 1000.
     *
     * Pre: Repository wurde geladen
     * Post: Fehlerhafte Koordinaten wurden korrigiert oder auf null gesetzt
     *
     * @param tracker Tracker zum Aufzeichnen der Korrekturen
     * @return Anzahl korrigierter Datensätze
     */
    public int korrigiereKoordinaten(KoordinatenKorrekturTracker tracker) {
        int anzahlKorrigiert = 0;
        
        for (Windkraftanlage anlage : alleAnlagen) {
            boolean wurdeKorrigiert = false;
            
            Float alterBreitengrad = anlage.getBreitengrad();
            Float alterLaengengrad = anlage.getLaengengrad();
            Float neuerBreitengrad = alterBreitengrad;
            Float neuerLaengengrad = alterLaengengrad;
            
            // Korrigiere Breitengrad wenn ungültig
            if (!KoordinatenValidierer.istBreitengradGueltig(alterBreitengrad)) {
                neuerBreitengrad = KoordinatenValidierer.korrigiereBreitengrad(alterBreitengrad);
                if (neuerBreitengrad != null) {
                    anlage.setBreitengrad(neuerBreitengrad);
                    wurdeKorrigiert = true;
                } else {
                    anlage.setBreitengrad(null);
                    neuerBreitengrad = null;
                }
            }
            
            // Korrigiere Längengrad wenn ungültig
            if (!KoordinatenValidierer.istLaengengradGueltig(alterLaengengrad)) {
                neuerLaengengrad = KoordinatenValidierer.korrigiereLaengengrad(alterLaengengrad);
                if (neuerLaengengrad != null) {
                    anlage.setLaengengrad(neuerLaengengrad);
                    wurdeKorrigiert = true;
                } else {
                    anlage.setLaengengrad(null);
                    neuerLaengengrad = null;
                }
            }
            
            // Zeichne Korrektur auf wenn etwas korrigiert wurde
            if (wurdeKorrigiert) {
                tracker.zeichneKorrekturAuf(anlage, 
                    alterBreitengrad, alterLaengengrad,
                    neuerBreitengrad, neuerLaengengrad);
                anzahlKorrigiert++;
            }
        }
        
        return anzahlKorrigiert;
    }
}