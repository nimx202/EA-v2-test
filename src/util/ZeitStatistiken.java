package util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility zum Sammeln von Zeit- und Statistik-Metriken während der Laufzeit.
 * 
 * Design-Prinzipien:
 * - Modularisierung: Nutzt AusgabeManager für alle Ausgaben
 * - Keine hardcodierten Strings: Alle Texte kommen aus Konstanten
 * - Single Responsibility: Nur Sammlung und Anzeige von Statistiken
 */
public final class ZeitStatistiken {

    private static final Map<String, Float> ZEITEN = new LinkedHashMap<>();
    private static final Map<String, String> STATS = new LinkedHashMap<>();

    private ZeitStatistiken() { }

    /**
     * Zeichnet eine gemessene Zeit für einen Namen.
     *
     * Pre: name nicht null; millis >= 0
     * Post: Zeit wurde in ZEITEN gespeichert
     * 
     * @param name Name der Messung
     * @param millis Zeit in Millisekunden
     */
    public static void zeichneZeitAuf(String name, float millis) {
        ZEITEN.put(name, millis);
    }

    /**
     * Zeichnet eine Statistik-Metrik.
     *
     * Pre: name und value nicht null
     * Post: Statistik wurde in STATS gespeichert
     * 
     * @param name Name der Statistik
     * @param value Wert als String
     */
    public static void zeichneStat(String name, String value) {
        STATS.put(name, value);
    }

    /**
     * Gibt die gesammelten Zeiten und Statistiken auf stdout aus.
     * Nutzt AusgabeManager und Konstanten für alle Ausgaben.
     * 
     * Pre: keine
     * Post: Alle gesammelten Zeiten und Statistiken wurden ausgegeben
     */
    public static void druckeZusammenfassung() {
        AusgabeManager.gebeAus(Konstanten.ZUSAMMENFASSUNG_UEBERSCHRIFT);
        float gesamt = 0.0f;
        if (!ZEITEN.isEmpty()) {
            AusgabeManager.gebeAus(Konstanten.ZEITEN_UEBERSCHRIFT);
            for (Map.Entry<String, Float> e : ZEITEN.entrySet()) {
                AusgabeManager.gebeAusFormat(Konstanten.ZEIT_FORMAT, e.getKey(), e.getValue());
                gesamt += e.getValue();
            }
            AusgabeManager.gebeAusFormat(Konstanten.GESAMTZEIT_FORMAT, gesamt);
        } else {
            AusgabeManager.gebeAus(Konstanten.KEINE_ZEITMESSUNGEN);
        }

        if (!STATS.isEmpty()) {
            AusgabeManager.gebeAus(Konstanten.STATISTIKEN_UEBERSCHRIFT);
            for (Map.Entry<String, String> e : STATS.entrySet()) {
                AusgabeManager.gebeAusFormat(Konstanten.STATISTIK_FORMAT, e.getKey(), e.getValue());
            }
        } else {
            AusgabeManager.gebeAus(Konstanten.KEINE_STATISTIKEN);
        }

        AusgabeManager.gebeAus(Konstanten.ENDE_ZUSAMMENFASSUNG);
    }
}