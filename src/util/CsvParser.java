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
public class CsvParser {

    private CsvParser() {
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
    public static String readNextRecord(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        int charCode;
        boolean readAnyChar = false;

        while ((charCode = reader.read()) != -1) {
            readAnyChar = true;
            char c = (char) charCode;

            if (c == Konstanten.ANFÜHRUNGSZEICHEN) {
                handleQuoteCharacter(reader, sb, c);
                inQuotes = !inQuotes;
            } else if ((c == Konstanten.ZEILENUMBRUCH || c == Konstanten.WAGENRÜCKLAUF) && !inQuotes) {
                handleRecordEnd(reader, c);
                break; // Record finished
            } else {
                sb.append(c);
            }
        }

        // Wenn kein Zeichen gelesen wurde und StringBuilder leer ist, EOF
        if (!readAnyChar && sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }

    /**
     * Behandelt Quote-Zeichen in CSV-Records (escaped und normale Quotes).
     *
     * Pre: reader und sb sind nicht null; c ist '"'
     * Post: Escaped Quotes werden korrekt verarbeitet
     *
     * @param reader BufferedReader
     * @param sb StringBuilder für Record-Aufbau
     * @param c das Quote-Zeichen
     * @throws IOException bei I/O-Fehlern
     */
    private static void handleQuoteCharacter(BufferedReader reader, StringBuilder sb, char c) throws IOException {
        reader.mark(Konstanten.MARKIERUNGSABSTAND);
        int nextCharCode = reader.read();

        if (nextCharCode == Konstanten.ANFÜHRUNGSZEICHEN) {
            // Escaped quote ("") -> ein Quote anhängen
            sb.append(Konstanten.ANFÜHRUNGSZEICHEN);
        } else {
            // Normales Quote -> Position zurücksetzen, damit Toggle inQuotes passiert
            if (nextCharCode != -1) {
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
    private static void handleRecordEnd(BufferedReader reader, char c) throws IOException {
        if (c == Konstanten.WAGENRÜCKLAUF) {
            reader.mark(Konstanten.MARKIERUNGSABSTAND);
            int nextCharCode = reader.read();
            // Wenn nicht LF nach CR, zurücksetzen
            if (nextCharCode != -1 && nextCharCode != Konstanten.ZEILENUMBRUCH) {
                reader.reset();
            }
        }
    }

    /**
     * Teilt eine CSV-Zeile in Felder auf und respektiert Anführungszeichen.
     * Unterstützt escaped quotes ("" -> ") und Kommata innerhalb von Anführungszeichen.
     *
     * Pre: line ist nicht null
     * Post: Rückgabe Array mit Feldern (mit umschließenden Spaces)
     *
     * @param line die CSV-Zeile
     * @return Array der Felder
     */
    public static String[] splitCsvLineRespectingQuotes(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;
        int lineLength = line.length();

        for (int i = 0; i < lineLength; i++) {
            char c = line.charAt(i);

            if (c == Konstanten.ANFÜHRUNGSZEICHEN) {
                if (isEscapedQuote(line, i, inQuotes)) {
                    currentField.append(Konstanten.ANFÜHRUNGSZEICHEN);
                    i++; // Skip escaped quote
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == Konstanten.KOMMA && !inQuotes) {
                fields.add(currentField.toString());
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }

        // Letzte Spalte
        fields.add(currentField.toString());
        return fields.toArray(new String[0]);
    }

    /**
     * Prüft, ob ein Quote-Zeichen ein escaped Quote ist ("").
     *
     * Pre: line nicht null; i < line.length()
     * Post: true wenn das nächste Zeichen auch ein Quote ist (und wir in Quotes sind)
     *
     * @param line die CSV-Zeile
     * @param currentIndex aktueller Index
     * @param inQuotes ob wir aktuell in Quotes sind
     * @return true wenn escaped quote, false sonst
     */
    private static boolean isEscapedQuote(String line, int currentIndex, boolean inQuotes) {
        return inQuotes && currentIndex + 1 < line.length() && line.charAt(currentIndex + 1) == Konstanten.ANFÜHRUNGSZEICHEN;
    }

    /**
     * Entfernt umschließende Anführungszeichen und konvertiert escaped quotes.
     * Beispiel: '"Hallo, ""Welt"""' -> 'Hallo, "Welt"'
     *
     * Pre: raw kann null oder beliebig sein
     * Post: Rückgabe bereinigtes Feld (umschließende Quotes entfernt, escaped Quotes konvertiert)
     *
     * @param raw rohes Feld aus der CSV
     * @return bereinigtes Feld
     */
    public static String cleanField(String raw) {
        if (raw == null) {
            return null;
        }

        String trimmed = raw.trim();
        if (hasEnclosingQuotes(trimmed)) {
            // Entferne äußere Quotes
            trimmed = trimmed.substring(1, trimmed.length() - 1);
            // Ersetze CSV-escaped quotes ("") mit "
            trimmed = trimmed.replace(Konstanten.ESCAPED_ANFÜHRUNGSZEICHEN, Konstanten.EINZELNES_ANFÜHRUNGSZEICHEN);
        }
        return trimmed;
    }

    /**
     * Prüft, ob ein String mit Quotes umschlossen ist.
     *
     * Pre: value nicht null
     * Post: true wenn value mit Quotes beginnt und endet
     *
     * @param value der zu prüfende String
     * @return true wenn mit Quotes umschlossen
     */
    private static boolean hasEnclosingQuotes(String value) {
        return value.length() >= 2 && value.charAt(0) == Konstanten.ANFÜHRUNGSZEICHEN && value.charAt(value.length() - 1) == Konstanten.ANFÜHRUNGSZEICHEN;
    }
}
