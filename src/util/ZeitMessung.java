package util;

/**
 * Utility-Klasse zum Messen und Sammeln von Zeitstatistiken.
 * Kapselt die Timer-Funktionalität für wiederverwendbare Zeitmessung.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Zeitmessung
 * - Wiederverwendbarkeit: Kann für beliebige Code-Abschnitte verwendet werden
 * - Einfache API: start(), stopp(), getDauer()
 * 
 * Verwendung:
 * ZeitMessung timer = ZeitMessung.starte();
 * // ... Code ausführen ...
<<<<<<< HEAD
 * float millis = timer.stoppeUndGibMillis();
=======
 * double millis = timer.stoppeUndGibMillis();
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
 */
public final class ZeitMessung {

    private long startZeit;
    private long endZeit;
    private boolean laeuft;

    /**
     * Privater Konstruktor. Verwende starte() zum Erstellen.
     * 
     * Pre: keine
     * Post: Timer erstellt aber nicht gestartet
     */
    private ZeitMessung() {
        this.laeuft = false;
    }

    /**
     * Erstellt und startet einen neuen Timer.
     * 
     * Pre: keine
     * Post: Timer läuft und misst Zeit
     * 
     * @return Neue laufende ZeitMessung-Instanz
     */
    public static ZeitMessung starte() {
        ZeitMessung messung = new ZeitMessung();
        messung.startZeit = System.nanoTime();
        messung.laeuft = true;
        return messung;
    }

    /**
     * Stoppt den Timer und gibt die verstrichene Zeit in Millisekunden zurück.
     * 
     * Pre: Timer wurde gestartet (läuft == true)
     * Post: Timer gestoppt; Rückgabe: verstrichene Zeit in ms
     * 
     * @return Verstrichene Zeit in Millisekunden
     */
<<<<<<< HEAD
    public float stoppeUndGibMillis() {
        if (!laeuft) {
            return 0.0f;
=======
    public double stoppeUndGibMillis() {
        if (!laeuft) {
            return 0.0;
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
        }
        endZeit = System.nanoTime();
        laeuft = false;
        return berechneDauerInMillis();
    }

    /**
     * Berechnet die Dauer zwischen Start und Ende in Millisekunden.
     * 
     * Pre: startZeit und endZeit gesetzt
     * Post: Rückgabe: Dauer in Millisekunden
     * 
     * @return Dauer in Millisekunden
     */
<<<<<<< HEAD
    private float berechneDauerInMillis() {
=======
    private double berechneDauerInMillis() {
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
        return (endZeit - startZeit) / Konstanten.NANOS_ZU_MILLIS;
    }

    /**
     * Gibt zurück, ob der Timer aktuell läuft.
     * 
     * Pre: keine
     * Post: Rückgabe: true wenn Timer läuft, false sonst
     * 
     * @return true wenn Timer läuft, false sonst
     */
    public boolean laeuft() {
        return laeuft;
    }

    /**
     * Hilfsmethode: Misst die Zeit einer Aktion und gibt sie direkt zurück.
     * Nutzt intern starte() und stoppeUndGibMillis().
     * 
     * Pre: keine
     * Post: Rückgabe: aktuelle Systemzeit in Nanosekunden
     * 
     * @return Aktuelle System-Nanozeit
     */
    public static long jetztNano() {
        return System.nanoTime();
    }

    /**
     * Konvertiert Nanosekunden in Millisekunden.
     * 
     * Pre: nanos >= 0
     * Post: Rückgabe: nanos in Millisekunden
     * 
     * @param nanos Zeit in Nanosekunden
     * @return Zeit in Millisekunden
     */
<<<<<<< HEAD
    public static float nanoZuMillis(long nanos) {
=======
    public static double nanoZuMillis(long nanos) {
>>>>>>> 64a22bc (Implement core utility classes for wind park analysis and coordinate validation)
        return nanos / Konstanten.NANOS_ZU_MILLIS;
    }
}
