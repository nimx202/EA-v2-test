package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Spezialisierte Klasse für das Parsen von CSV-Daten.
 * Verarbeitet quoted fields mit eingebetteten Zeilenumbrüchen und escaped quotes.
 *
 * Vertrag:
 * Pre: Methoden erhalten gültige, nicht-null Eingaben (wie dokumentiert)
 * Post: Rückgabewerte entsprechen der Dokumentation
 */
public class KonstantenParser {

    private KonstantenParser() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Liest aus dem Reader bis ein vollständiger CSV-Record vorliegt.
     * Ein Record endet bei einem Zeilenumbruch, der nicht innerhalb eines zitierten Feldes liegt.
     *
     * Pre: reader ist nicht null
     * Post: Rückgabe ein String mit vollständigem Record oder null bei EOF
     *
     * @param reader BufferedReader (nicht null)
     * @return String mit dem vollständigen Record oder null bei EOF
     * @throws IOException bei I/O-Fehlern
     */
    public static String leseNächstenDatensatz(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean inAnführungszeichen = false;
        int zeichencode;
        boolean leseAltZeichen = false;

        while ((zeichencode = reader.read()) != -1) {
            leseAltZeichen = true;
            char c = (char) zeichencode;

            if (c == Konstanten.ANFÜHRUNGSZEICHEN) {
                bearbeiteAnführungszeichen(reader, sb, c);
                inAnführungszeichen = !inAnführungszeichen;
            } else if ((c == Konstanten.ZEILENUMBRUCH || c == Konstanten.WAGENRÜCKLAUF) && !inAnführungszeichen) {
                bearbeiteDatensatzende(reader, c);
                break; // Record finished
            } else {
                sb.append(c);
            }
        }

        // Wenn kein Zeichen gelesen wurde und StringBuilder leer ist, EOF
        if (!leseAltZeichen && sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }

    /**
     * Behandelt Anführungszeichen in CSV-Records (escaped und normale Anführungszeichen).
     *
     * Pre: reader und sb sind nicht null; c ist '"'
     * Post: Escaped Quotes werden korrekt verarbeitet
     *
     * @param reader BufferedReader
     * @param sb StringBuilder für Record-Aufbau
     * @param c das Anführungszeichen
     * @throws IOException bei I/O-Fehlern
     */
    private static void bearbeiteAnführungszeichen(BufferedReader reader, StringBuilder sb, char c) throws IOException {
        reader.mark(Konstanten.MARKIERUNGSABSTAND);
        int nächsterZeichencode = reader.read();

        if (nächsterZeichencode == Konstanten.ANFÜHRUNGSZEICHEN) {
            // Escaped quote ("") -> ein Anführungszeichen anhängen
            sb.append(Konstanten.ANFÜHRUNGSZEICHEN);
        } else {
            // Normales Anführungszeichen -> Position zurücksetzen, damit Toggle inAnführungszeichen passiert
            if (nächsterZeichencode != -1) {
                reader.reset();
            }
        }
    }

    /**
     * Behandelt das Ende eines Records (Zeilenumbruch).
     * Unterstützt CRLF und LF Zeilenumbrüche.
     *
     * Pre: reader ist nicht null; c ist '\r' oder '\n'
     * Post: Korrekte Verarbeitung von CRLF oder LF
     *
     * @param reader BufferedReader
     * @param c das Zeilenumbruch-Zeichen
     * @throws IOException bei I/O-Fehlern
     */
    private static void bearbeiteDatensatzende(BufferedReader reader, char c) throws IOException {
        if (c == Konstanten.WAGENRÜCKLAUF) {
            reader.mark(Konstanten.MARKIERUNGSABSTAND);
            int nächsterZeichencode = reader.read();
            // Wenn nicht ZEILENUMBRUCH nach WAGENRÜCKLAUF, zurücksetzen
            if (nächsterZeichencode != -1 && nächsterZeichencode != Konstanten.ZEILENUMBRUCH) {
                reader.reset();
            }
        }
    }

    /**
     * Teilt eine CSV-Zeile in Felder auf und respektiert Anführungszeichen.
     * Unterstützt escaped quotes ("" -> ") und Kommata innerhalb von Anführungszeichen.
     *
     * Pre: zeile ist nicht null
     * Post: Rückgabe Array mit Feldern (mit umschließenden Spaces)
     *
     * @param zeile die CSV-Zeile
     * @return Array der Felder
     */
    public static String[] teileZeileRespektivierAnführungszeichen(String zeile) {
        List<String> felder = new ArrayList<>();
        StringBuilder aktuellesFeld = new StringBuilder();
        boolean inAnführungszeichen = false;
        int zeilenlänge = zeile.length();

        for (int i = 0; i < zeilenlänge; i++) {
            char c = zeile.charAt(i);

            if (c == Konstanten.ANFÜHRUNGSZEICHEN) {
                if (istEscapedAnführungszeichen(zeile, i, inAnführungszeichen)) {
                    aktuellesFeld.append(Konstanten.ANFÜHRUNGSZEICHEN);
                    i++; // Skip escaped quote
                } else {
                    inAnführungszeichen = !inAnführungszeichen;
                }
            } else if (c == Konstanten.KOMMA && !inAnführungszeichen) {
                felder.add(aktuellesFeld.toString());
                aktuellesFeld.setLength(0);
            } else {
                aktuellesFeld.append(c);
            }
        }

        // Letzte Spalte
        felder.add(aktuellesFeld.toString());
        return felder.toArray(new String[0]);
    }

    /**
     * Prüft, ob ein Anführungszeichen ein escaped Anführungszeichen ist ("").
     *
     * Pre: zeile nicht null; i < zeile.length()
     * Post: true wenn das nächste Zeichen auch ein Anführungszeichen ist (und wir in Anführungszeichen sind)
     *
     * @param zeile die CSV-Zeile
     * @param aktuellerIndex aktueller Index
     * @param inAnführungszeichen ob wir aktuell in Anführungszeichen sind
     * @return true wenn escaped quote, false sonst
     */
    private static boolean istEscapedAnführungszeichen(String zeile, int aktuellerIndex, boolean inAnführungszeichen) {
        return inAnführungszeichen && aktuellerIndex + 1 < zeile.length() && zeile.charAt(aktuellerIndex + 1) == Konstanten.ANFÜHRUNGSZEICHEN;
    }

    /**
     * Entfernt umschließende Anführungszeichen und konvertiert escaped quotes.
     * Beispiel: '"Hallo, ""Welt"""' -> 'Hallo, "Welt"'
     *
     * Pre: roh kann null oder beliebig sein
     * Post: Rückgabe bereinigtes Feld (umschließende Anführungszeichen entfernt, escaped Anführungszeichen konvertiert)
     *
     * @param roh rohes Feld aus der CSV
     * @return bereinigtes Feld
     */
    public static String bereinigesFeld(String roh) {
        if (roh == null) {
            return null;
        }

        String gekürzt = roh.trim();
        if (hatUmschließendeAnführungszeichen(gekürzt)) {
            // Entferne äußere Anführungszeichen
            gekürzt = gekürzt.substring(1, gekürzt.length() - 1);
            // Ersetze CSV-escaped Anführungszeichen ("") mit "
            gekürzt = gekürzt.replace(Konstanten.ESCAPED_ANFÜHRUNGSZEICHEN, Konstanten.EINZELNES_ANFÜHRUNGSZEICHEN);
        }
        return gekürzt;
    }

    /**
     * Prüft, ob ein String mit Anführungszeichen umschlossen ist.
     *
     * Pre: wert nicht null
     * Post: true wenn wert mit Anführungszeichen beginnt und endet
     *
     * @param wert der zu prüfende String
     * @return true wenn mit Anführungszeichen umschlossen
     */
    private static boolean hatUmschließendeAnführungszeichen(String wert) {
        return wert.length() >= 2 && wert.charAt(0) == Konstanten.ANFÜHRUNGSZEICHEN && wert.charAt(wert.length() - 1) == Konstanten.ANFÜHRUNGSZEICHEN;
    }
}
