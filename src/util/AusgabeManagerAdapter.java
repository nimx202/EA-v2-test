package util;

/**
 * Einfacher Adapter, der `AusgabeAdapter` implementiert und an `AusgabeManager` delegiert.
 */
public class AusgabeManagerAdapter implements AusgabeAdapter {

    @Override
    public void gebeAus(String text) {
        AusgabeManager.gebeAus(text);
    }

    @Override
    public void gebeAusFormat(String format, Object... args) {
        AusgabeManager.gebeAusFormat(format, args);
    }

    @Override
    public void gebeAusMitTrennzeichen(String schluessel, Object wert) {
        AusgabeManager.gebeAusMitTrennzeichen(schluessel, wert);
    }

    @Override
    public void gebeFehlerAus(String text) {
        AusgabeManager.gebeFehlerAus(text);
    }

    @Override
    public void gebeLeereZeileAus() {
        AusgabeManager.gebeLeereZeileAus();
    }

    @Override
    public void gebeTrennstrichAus(int laenge) {
        AusgabeManager.gebeTrennstrichAus(laenge);
    }

    @Override
    public void gebeUeberschriftAus(String ueberschrift) {
        AusgabeManager.gebeUeberschriftAus(ueberschrift);
    }
}
