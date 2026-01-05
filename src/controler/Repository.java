package controler;

import java.util.List;

/**
 * Einfaches Repository-Interface für KISS-Repository-Implementierungen.
 *
 * @param <T> Typ der enthaltten Objekte
 */
public interface Repository<T> {

    /** Liefert alle Objekte als Liste. */
    List<T> getAll();

    /** Liefert die Anzahl der enthaltenen Objekte. */
    int count();
}
