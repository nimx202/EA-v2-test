package util;

import model.Windkraftanlage;

/**
 * Adapter, der die statische Erstellungsfunktion an das Parser-Interface bindet.
 */
public class WindkraftanlageParserAdapter implements Parser<Windkraftanlage> {

    @Override
    public Windkraftanlage parse(String[] fields) {
        return WindkraftanlageErsteller.erstelleAusFelder(fields);
    }
}
