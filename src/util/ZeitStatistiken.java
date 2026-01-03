package util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility zum Sammeln von Zeit- und Statistik-Metriken während der Laufzeit.
 */
public final class ZeitStatistiken {

    private static final Map<String, Double> ZEITEN = new LinkedHashMap<>();
    private static final Map<String, String> STATS = new LinkedHashMap<>();

    private ZeitStatistiken() { }

    /**
     * Zeichnet eine gemessene Zeit für einen Namen.
     *
     * @param name Name der Messung
     * @param millis Zeit in Millisekunden
     */
    public static void zeichneZeitAuf(String name, double millis) {
        ZEITEN.put(name, millis);
    }

    /**
     * Zeichnet eine Statistik-Metrik.
     *
     * @param name Name der Statistik
     * @param value Wert als String
     */
    public static void zeichneStat(String name, String value) {
        STATS.put(name, value);
    }

    /**
     * Gibt die gesammelten Zeiten und Statistiken auf stdout aus.
     */
    public static void druckeZusammenfassung() {
        System.out.println("\n=== Zusammenfassung: Zeiten und Statistiken ===");
        double gesamt = 0.0;
        if (!ZEITEN.isEmpty()) {
            System.out.println("\n-- Zeiten (ms) --");
            for (Map.Entry<String, Double> e : ZEITEN.entrySet()) {
                System.out.printf("%s: %.3f ms\n", e.getKey(), e.getValue());
                gesamt += e.getValue();
            }
            System.out.printf("Gesamt gemessene Zeit (Summe): %.3f ms\n", gesamt);
        } else {
            System.out.println("(Keine Zeit-Messungen vorhanden)");
        }

        if (!STATS.isEmpty()) {
            System.out.println("\n-- Statistiken --");
            for (Map.Entry<String, String> e : STATS.entrySet()) {
                System.out.printf("%s: %s\n", e.getKey(), e.getValue());
            }
        } else {
            System.out.println("(Keine Statistiken vorhanden)");
        }

        System.out.println("=== Ende Zusammenfassung ===\n");
    }
}
