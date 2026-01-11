package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Zentrale Ausgabe-Verwaltung für die gesamte Anwendung.
 * Verantwortlich für formatierte Konsolenausgabe mit optionaler Pufferung.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Ausgabe-Logik mit Pufferung
 * - Wiederverwendbarkeit: Alle Ausgaben erfolgen über diese Klasse
 * - Konsistenz: Einheitliche Formatierung
 * - KISS: Einfache Liste für Pufferung
 * 
 * Verwendung:
 * - AusgabeManager.gebeAus(text) - einfache Ausgabe
 * - AusgabeManager.gebeAusFormat(format, args) - formatierte Ausgabe
 * - AusgabeManager.gebeKeyValue(key, value) - mit Trennzeichen
 */
public final class AusgabeManager {

    private static final List<String> pufferZeilen = new ArrayList<>();
    private static boolean istPufferungAktiv = false;

    private AusgabeManager() {
        // Utility-Klasse, keine Instanzen
    }
    
    /**
     * Aktiviert die Pufferung von Ausgaben.
     * 
     * Pre: Keine
     * Post: Ausgaben werden gepuffert statt sofort ausgegeben
     */
    public static void aktivierePufferung() {
        istPufferungAktiv = true;
        pufferZeilen.clear();
    }
    
    /**
     * Gibt alle gepufferten Ausgaben auf einmal aus.
     * 
     * Pre: Keine
     * Post: Alle gepufferten Ausgaben wurden ausgegeben
     */
    public static void gebeGepufferteAusgabenAus() {
        for (String zeile : pufferZeilen) {
            System.out.println(zeile);
        }
        istPufferungAktiv = false;
        pufferZeilen.clear();
    }

    /**
     * Gibt einen Text auf der Standardausgabe aus.
     * 
     * Pre: text nicht null
     * Post: Text wurde auf stdout ausgegeben oder gepuffert
     * 
     * @param text Der auszugebende Text
     */
    public static void gebeAus(String text) {
        if (istPufferungAktiv) {
            String zeile = (text == null) ? Konstanten.LEERSTRING : text;
            pufferZeilen.add(zeile);
        } else {
            System.out.println(text);
        }
    }

    /**
     * Gibt einen formatierten Text auf der Standardausgabe aus.
     * 
     * Pre: format nicht null
     * Post: Formatierter Text wurde auf stdout ausgegeben oder gepuffert
     * 
     * @param format Format-String (wie in printf)
     * @param args Argumente für die Formatierung
     */
    public static void gebeAusFormat(String format, Object... args) {
        String formatierterText = String.format(format, args);
        if (istPufferungAktiv) {
            // Entferne abschließenden Zeilenumbruch wenn vorhanden
            if (formatierterText.endsWith(System.lineSeparator())) {
                formatierterText = formatierterText.substring(0, 
                    formatierterText.length() - System.lineSeparator().length());
            }
            pufferZeilen.add(formatierterText);
        } else {
            System.out.print(formatierterText);
        }
    }

    /**
     * Gibt einen Text auf die Fehlerausgabe aus.
     * 
     * Pre: text nicht null
     * Post: Text wurde auf stderr ausgegeben
     * 
     * @param text Der auszugebende Fehlertext
     */
    public static void gebeFehlerAus(String text) {
        System.err.println(text);
    }

    /**
     * Gibt eine leere Zeile aus.
     * 
     * Pre: keine
     * Post: Leere Zeile wurde auf stdout ausgegeben oder gepuffert
     */
    public static void gebeLeereZeileAus() {
        if (istPufferungAktiv) {
            pufferZeilen.add(Konstanten.LEERSTRING);
        } else {
            System.out.println();
        }
    }

    /**
     * Gibt einen Trennstrich mit gegebener Länge aus.
     * 
     * Pre: länge >= 0
     * Post: Trennstrich wurde auf stdout ausgegeben oder gepuffert
     * 
    * @param laenge Länge des Trennstrichs
     */
    public static void gebeTrennstrichAus(int laenge) {
        String trennstrich = Konstanten.TRENNSTRICH_ZEICHEN.repeat(Math.max(0, laenge));
        if (istPufferungAktiv) {
            pufferZeilen.add(trennstrich);
        } else {
            System.out.println(trennstrich);
        }
    }

    /**
     * Gibt eine Sektion/Überschrift mit einheitlicher Formatierung aus.
     *
     * @param titel Sektionstitel
     */
    public static void gebeSektionAus(String titel) {
        gebeAus(AusgabeFormatter.ueberschrift(titel));
    }

    /**
     * Gibt ein Schlüssel-Wert-Paar in einheitlichem Stil aus.
     *
     * @param schluessel Schlüssel
     * @param wert Wert (kann null sein)
     */
    public static void gebeKeyValue(String schluessel, Object wert) {
        gebeAus(AusgabeFormatter.schluesselWert(schluessel, wert));
    }
}
