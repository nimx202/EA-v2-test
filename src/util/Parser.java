package util;

/**
 * Kleines Parser-Interface für Mapping von CSV-Feldern zu Domain-Objekten.
 * @param <T> Zieltyp
 */
public interface Parser<T> {

    /** Parst ein Feld-Array zu einem Objekt vom Typ T. Kann null bei Fehler zurückliefern. */
    T parse(String[] fields);
}
