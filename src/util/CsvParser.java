package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility-Klasse zum Parsen von CSV-Dateien.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur CSV-Parsing
 * - KISS: Einfache Implementierung ohne externe Bibliotheken
 * - Robustheit: Berücksichtigt Anführungszeichen und escaped Zeichen
 * 
 * Verantwortlichkeiten:
 * - Lesen von CSV-Zeilen aus BufferedReader
 * - Teilen von CSV-Zeilen in Felder unter Beachtung von Anführungszeichen
 * - Bereinigen von Feldwerten (Entfernen von Anführungszeichen)
 * 
 * Pre: Reader ist nicht null und geöffnet
 * Post: Liefert CSV-Zeilen und Felder zurück
 */
public final class CsvParser {

    /**
     * Privater Konstruktor verhindert Instanziierung.
     * Dies ist eine Utility-Klasse mit nur statischen Methoden.
     */
    private CsvParser() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Liest eine einzelne CSV-Zeile aus dem Reader.
     * Eine Zeile endet mit Zeilenumbruch oder Dateiende.
     * 
     * Pre: csvLeser nicht null und geöffnet
     * Post: Rückgabe: nächste Zeile oder null bei Dateiende
     * 
     * @param csvLeser BufferedReader zum Lesen der Datei
     * @return Die nächste CSV-Zeile oder null wenn Dateiende
     * @throws IOException bei Lesefehler
     */
    public static String leseNaechstenDatensatz(BufferedReader csvLeser) throws IOException {

        return csvLeser.readLine();
    }

    /**
     * Teilt eine CSV-Zeile in einzelne Felder auf.
     * Berücksichtigt Anführungszeichen und escaped Anführungszeichen.
     * 
     * Logik:
     * - Felder sind durch Komma getrennt
     * - Felder in Anführungszeichen können Kommas enthalten
     * - Doppelte Anführungszeichen ("") sind escaped Anführungszeichen
     * 
     * Pre: csvZeile kann null sein
     * Post: Rückgabe: Array mit einzelnen Feldwerten
     * 
     * @param csvZeile Die zu teilende CSV-Zeile
     * @return Array mit einzelnen Feldwerten
     */
    public static String[] teileZeileInFelder(String csvZeile) {
        if (csvZeile == null) {
            return new String[0];
        }

        List<String> feldListe = new ArrayList<>();
        StringBuilder aktuellesFeld = new StringBuilder();
        boolean istInAnfuehrungszeichen = false;
        int klammerTiefe = 0; // depth of parentheses when outside quotes

        // Durchlaufe jeden Buchstaben der Zeile
        for (int position = 0; position < csvZeile.length(); position++) {
            char zeichen = csvZeile.charAt(position);

            // Prüfe ob es ein Anführungszeichen ist
            if (zeichen == Konstanten.ANFUEHRUNGSZEICHEN) {
                // Prüfe ob es doppeltes Anführungszeichen ist (escaped)
                boolean istNaechstesAuchAnfuehrungszeichen = istInAnfuehrungszeichen 
                    && (position + 1 < csvZeile.length()) 
                    && (csvZeile.charAt(position + 1) == Konstanten.ANFUEHRUNGSZEICHEN);
                
                if (istNaechstesAuchAnfuehrungszeichen) {
                    // Füge ein einzelnes Anführungszeichen hinzu
                    aktuellesFeld.append(Konstanten.ANFUEHRUNGSZEICHEN);
                    position++; // Überspringe nächstes Anführungszeichen
                } else {
                    // Wechsle zwischen "in Anführungszeichen" und "außerhalb"
                    istInAnfuehrungszeichen = !istInAnfuehrungszeichen;
                }
            }
            // Prüfe auf Klammer-Öffner/Schließer (nur außerhalb von Anführungszeichen)
            else if (!istInAnfuehrungszeichen && zeichen == '(') {
                klammerTiefe++;
                aktuellesFeld.append(zeichen);
            } else if (!istInAnfuehrungszeichen && zeichen == ')') {
                if (klammerTiefe > 0) {
                    klammerTiefe--;
                }
                aktuellesFeld.append(zeichen);
            }
            // Prüfe ob es ein Komma ist (nur außerhalb von Anführungszeichen und Klammern)
            else if (zeichen == Konstanten.KOMMA && !istInAnfuehrungszeichen && klammerTiefe == 0) {
                // Feld ist komplett, füge zur Liste hinzu
                feldListe.add(aktuellesFeld.toString());
                aktuellesFeld = new StringBuilder();
            }
            // Normales Zeichen
            else {
                aktuellesFeld.append(zeichen);
            }
        }

        // Füge letztes Feld hinzu
        feldListe.add(aktuellesFeld.toString());

        // Konvertiere Liste zu Array
        return feldListe.toArray(new String[0]);
    }

    /**
     * Entfernt Anführungszeichen am Anfang und Ende eines Feldwerts.
     * Ersetzt doppelte Anführungszeichen durch einfache.
     * 
     * Pre: rohFeld kann null sein
     * Post: Rückgabe: bereinigter Feldwert
     * 
     * @param rohFeld Das rohe Feld mit möglichen Anführungszeichen
     * @return Bereinigter Feldwert
     */
    public static String bereinigesFeld(String rohFeld) {
        if (rohFeld == null) {
            return null;
        }

        // Entferne Leerzeichen am Anfang und Ende
        String bereinigtesFeld = rohFeld.trim();

        // Prüfe ob Feld mindestens 2 Zeichen lang ist
        if (bereinigtesFeld.length() >= 2) {
            char erstesZeichen = bereinigtesFeld.charAt(0);
            char letztesZeichen = bereinigtesFeld.charAt(bereinigtesFeld.length() - 1);
            
            // Prüfe ob Feld mit Anführungszeichen umgeben ist
            boolean beginntMitAnfuehrungszeichen = erstesZeichen == Konstanten.ANFUEHRUNGSZEICHEN;
            boolean endetMitAnfuehrungszeichen = letztesZeichen == Konstanten.ANFUEHRUNGSZEICHEN;
            
            if (beginntMitAnfuehrungszeichen && endetMitAnfuehrungszeichen) {
                // Entferne äußere Anführungszeichen
                bereinigtesFeld = bereinigtesFeld.substring(1, bereinigtesFeld.length() - 1);
                
                // Ersetze doppelte Anführungszeichen durch einfache
                bereinigtesFeld = bereinigtesFeld.replace(
                    Konstanten.ESCAPED_ANFUEHRUNGSZEICHEN, 
                    Konstanten.EINZELNES_ANFUEHRUNGSZEICHEN);
            }
        }

        return bereinigtesFeld;
    }
}
