package view;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import util.AusgabeManager;
import util.Konstanten;

/**
 * Prueft und liefert den Pfad zur CSV-Datei.
 *
 * Design-Prinzipien:
 * - Single Responsibility: Nur Pfadermittlung und Existenzpruefung
 * - KISS: Verwendet einfache Methoden ohne komplexe Logik
 * - Modularisierung: Erzeugt klare Trennung zur Datenverarbeitung
 *
 * Pre: Konstanten.RESSOURCENPFAD zeigt auf eine gueltige Ressource
 * Post: Pfad erstellt und bei Bedarf geprueft
 */
public class CsvPfadPruefer {

    /**
     * Liefert den Standardpfad zur CSV-Datei.
     *
     * Pre: Keine speziellen Bedingungen
     * Post: Pfad auf Basis von Konstanten.RESSOURCENPFAD erstellt
     *
     * @return Pfad zur CSV-Datei
     */
    public Path ermittleCsvPfad() {
        return Paths.get(Konstanten.RESSOURCENPFAD);
    }

    /**
     * Prueft, ob die CSV-Datei existiert, und meldet Fehler, falls nicht.
     *
     * Pre: csvPfad darf nicht null sein
     * Post: Fehlerausgabe bei fehlender Datei; true/false Rueckgabe signalisiert Ergebnis
     *
     * @param csvPfad Pfad zur CSV-Datei
     * @return true, wenn Datei existiert, sonst false
     */
    public boolean pruefeCsvDatei(Path csvPfad) {
        Objects.requireNonNull(csvPfad);
        if (!Files.exists(csvPfad)) {
            AusgabeManager.gebeFehlerAus(Konstanten.CSV_NICHT_GEFUNDEN + csvPfad.toAbsolutePath());
            return false;
        }
        return true;
    }
}
