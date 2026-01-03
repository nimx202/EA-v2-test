package view;

// Datei kodiert in UTF-8

import controler.WindkraftanlageRepository;

/**
 * Veraltet: Wrapper der alten Position. Wird in einem kommenden Release entfernt.
 * Bitte die Implementierung in {@link util.KoordinatenValidierungsService} verwenden.
 *
 * Hinweis: Diese Klasse ist jetzt fail-fast, um versehentliche Verwendung zu verhindern.
 *
 * @deprecated Use {@link util.KoordinatenValidierungsService} instead. Will be removed.
 */
@Deprecated(since = "2026-01-03", forRemoval = true)
public final class KoordinatenValidierungsService {

    private KoordinatenValidierungsService() {
        throw new UnsupportedOperationException("KoordinatenValidierungsService in 'view' wurde entfernt; benutzen Sie util.KoordinatenValidierungsService.");
    }

    public static void validiereUndKorrigiere(WindkraftanlageRepository repo) {
        throw new UnsupportedOperationException("KoordinatenValidierungsService in 'view' wurde entfernt; benutzen Sie util.KoordinatenValidierungsService.");
    }
}
