package util;

/**
 * Adapter-Interface für Ausgaben; erlaubt unterschiedliche Ausgabemechanismen
 * (Konsole, Datei, GUI) ohne Änderung an Aufrufern.
 */
public interface AusgabeAdapter {

    void gebeAus(String text);

    void gebeAusFormat(String format, Object... args);

    void gebeAusMitTrennzeichen(String schluessel, Object wert);

    void gebeFehlerAus(String text);

    void gebeLeereZeileAus();

    void gebeTrennstrichAus(int laenge);

    void gebeUeberschriftAus(String ueberschrift);
}
