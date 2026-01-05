package view;

/**
 * Hauptklasse der Anwendung.
 * Delegiert den Ablauf an {@link AnwendungsAblaufKoordinator}.
 *
 * Design-Prinzipien:
 * - KISS: Nur Einstiegspunkt ohne Logik
 * - Modularisierung: Auslagerung der Orchestrierung
 * - Single Responsibility: Startet lediglich den Runner
 *
 * Pre: Keine speziellen Bedingungen
 * Post: Anwendung wurde gestartet oder Fehler angezeigt
 */
public final class Main {

    private Main() {
        // Keine Instanzen nötig
    }

    /**
     * Einstiegspunkt der Anwendung.
     *
     * Pre: Keine
    * Post: AnwendungsAblaufKoordinator wurde ausgefuehrt
     *
     * @param args Kommandozeilenargumente (nicht verwendet)
     */
    public static void main(String[] args) {
        AnwendungsAblaufKoordinator koordinator = new AnwendungsAblaufKoordinator();
        koordinator.run();
    }
}
