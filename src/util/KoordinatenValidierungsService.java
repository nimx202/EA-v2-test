package util;

// Datei kodiert in UTF-8

import controler.WindkraftanlageRepository;
import model.Windkraftanlage;

import java.util.ArrayList;
import java.util.List;

/**
 * Service-Klasse für Daten-Validierung und -Korrektur mit Ausgabe.
 * Kapselt die gesamte Logik für die Überprüfung, Korrektur und Ausgabe
 * von fehlerhaften Daten (Koordinaten, Baujahr, Gesamtleistung).
 *
 * Design: Stateless Service-Klasse
 */
public class KoordinatenValidierungsService {

    /**
     * Privater Konstruktor verhindert Instanziierung.
     */
    private KoordinatenValidierungsService() {
        // Utility-Klasse
    }

    /**
     * Validiert und korrigiert fehlerhafte Daten mit Zeitmessungen.
     *
     * Vorgehensweise:
     * 1. Validiere und korrigiere Koordinaten
     * 2. Validiere und korrigiere Baujahre
     * 3. Validiere und korrigiere Gesamtleistungen
     * 4. Messe und zeige Zeiten für jede Phase an
     *
     * Pre: repo nicht null und enthält geladene Daten
     * Post: Fehlerhafte Daten wurden korrigiert; Zeiten und Statistiken ausgegeben
     *
     * @param repo das Repository mit Windkraftanlagen
     */
    public static void validiereUndKorrigiere(WindkraftanlageRepository repo) {
        // Koordinaten-Validierung
        validiereUndKorrigiereKoordinaten(repo);
        
        // Baujahr-Validierung
        validiereUndKorrigiereBaujahr(repo);
        
        // Gesamtleistung-Validierung
        validiereUndKorrigiereGesamtLeistung(repo);
    }

    private static void validiereUndKorrigiereKoordinaten(WindkraftanlageRepository repo) {
        System.out.println(Konstanten.VALIDIERUNG_ÜBERSCHRIFT);

        long startValidierung = System.nanoTime();
        List<Windkraftanlage> fehlerhafte = KoordinatenValidator.findeFehlerhafte(repo.getAll());
        long endValidierung = System.nanoTime();
        double validierungMillis = (endValidierung - startValidierung) / Konstanten.NANOS_ZU_MILLIS;

        berichteValidierung("Koordinaten", validierungMillis, fehlerhafte.size(), Konstanten.FEHLERHAFTE_KOORDINATEN);

        List<KoordinatenFehler> ursprünglicheWerte = speichereUrsprünglicheWerte(fehlerhafte);

        gebeFehlerhateDatensätzeAus(fehlerhafte);

        long startKorrektur = System.nanoTime();
        int korrigiertAnzahl = KoordinatenKorrektor.korrigiereFehlerhafte(repo.getAll());
        long endKorrektur = System.nanoTime();
        double korrekturMillis = (endKorrektur - startKorrektur) / Konstanten.NANOS_ZU_MILLIS;

        berichteKorrektur("Koordinaten", korrekturMillis, korrigiertAnzahl, Konstanten.KORRIGIERTE_KOORDINATEN);

        gebeKorrigierteDatensätzeAus(ursprünglicheWerte, repo);

        System.out.println();
    }

    private static List<KoordinatenFehler> speichereUrsprünglicheWerte(List<Windkraftanlage> fehlerhafte) {
        List<KoordinatenFehler> ursprünglicheWerte = new ArrayList<>();
        for (Windkraftanlage anlage : fehlerhafte) {
            ursprünglicheWerte.add(new KoordinatenFehler(
                    anlage.getObjektId(),
                    anlage.getName(),
                    anlage.getBreitengrad(),
                    anlage.getLaengengrad(),
                    anlage.getBaujahr(),
                    anlage.getGesamtLeistungMW()
            ));
        }
        return ursprünglicheWerte;
    }

    private static void gebeFehlerhateDatensätzeAus(List<Windkraftanlage> fehlerhafte) {
        if (!fehlerhafte.isEmpty()) {
            System.out.println(Konstanten.FEHLERHAFTE_DATENSÄTZE);
            for (Windkraftanlage anlage : fehlerhafte) {
                System.out.printf(Konstanten.FEHLER_DATENSATZ,
                        anlage.getObjektId(),
                        anlage.getName() != null ? anlage.getName() : "<unbekannt>",
                        anlage.getBreitengrad() != null ? anlage.getBreitengrad() : 0.0,
                        anlage.getLaengengrad() != null ? anlage.getLaengengrad() : 0.0);
            }
        }
    }

    private static void gebeKorrigierteDatensätzeAus(List<KoordinatenFehler> ursprünglicheWerte, 
                                                      WindkraftanlageRepository repo) {
        if (!ursprünglicheWerte.isEmpty()) {
            System.out.println(Konstanten.KORRIGIERTE_DATENSÄTZE);
            for (KoordinatenFehler alt : ursprünglicheWerte) {
                Windkraftanlage korrigiert = repo.getAll().stream()
                        .filter(a -> a.getObjektId() == alt.getObjektId())
                        .findFirst()
                        .orElse(null);

                if (korrigiert != null) {
                    System.out.printf(Konstanten.KORREKTUR_DATENSATZ,
                            korrigiert.getObjektId(),
                            korrigiert.getName() != null ? korrigiert.getName() : "<unbekannt>",
                            alt.getUrsprünglicherBreitengrad() != null ? alt.getUrsprünglicherBreitengrad() : 0.0,
                            alt.getUrsprünglicherLaengengrad() != null ? alt.getUrsprünglicherLaengengrad() : 0.0,
                            korrigiert.getBreitengrad() != null ? korrigiert.getBreitengrad() : 0.0,
                            korrigiert.getLaengengrad() != null ? korrigiert.getLaengengrad() : 0.0);
                }
            }
        }
    }

    private static void validiereUndKorrigiereBaujahr(WindkraftanlageRepository repo) {
        System.out.println("\n=== Überprüfung und Korrektur der Baujahre ===");

        long startValidierung = System.nanoTime();
        List<Windkraftanlage> fehlerhafte = KoordinatenValidator.findeFehlerhafesBaujahr(repo.getAll());
        long endValidierung = System.nanoTime();
        double validierungMillis = (endValidierung - startValidierung) / Konstanten.NANOS_ZU_MILLIS;

        berichteValidierung("Baujahre", validierungMillis, fehlerhafte.size(), Konstanten.FEHLERHAFTE_BAUJAHRE);

        List<KoordinatenFehler> ursprünglicheWerte = speichereUrsprünglicheWerte(fehlerhafte);

        gebeFehlerhafesBaujahrAus(fehlerhafte);

        long startKorrektur = System.nanoTime();
        int korrigiertAnzahl = KoordinatenKorrektor.korrigiereFehlerhafesBaujahr(repo.getAll());
        long endKorrektur = System.nanoTime();
        double korrekturMillis = (endKorrektur - startKorrektur) / Konstanten.NANOS_ZU_MILLIS;

        berichteKorrektur("Baujahre", korrekturMillis, korrigiertAnzahl, Konstanten.KORRIGIERTE_BAUJAHRE);

        gebeKorrigierteBaujahreAus(ursprünglicheWerte, repo);

        System.out.println();
    }

    private static void gebeFehlerhafesBaujahrAus(List<Windkraftanlage> fehlerhafte) {
        if (!fehlerhafte.isEmpty()) {
            System.out.println("\nFehlerhafte Datensätze (vor Korrektur):");
            for (Windkraftanlage anlage : fehlerhafte) {
                System.out.printf(Konstanten.FEHLER_BAUJAHR,
                        anlage.getObjektId(),
                        anlage.getName() != null ? anlage.getName() : "<unbekannt>",
                        anlage.getBaujahr() != null ? anlage.getBaujahr() : 0);
            }
        }
    }

    private static void gebeKorrigierteBaujahreAus(List<KoordinatenFehler> ursprünglicheWerte, 
                                                    WindkraftanlageRepository repo) {
        if (!ursprünglicheWerte.isEmpty()) {
            System.out.println("\nKorrigierte Datensätze (nach Korrektur):");
            for (KoordinatenFehler alt : ursprünglicheWerte) {
                Windkraftanlage korrigiert = repo.getAll().stream()
                        .filter(a -> a.getObjektId() == alt.getObjektId())
                        .findFirst()
                        .orElse(null);

                if (korrigiert != null && alt.getUrsprünglicherBaujahr() != null) {
                    System.out.printf(Konstanten.KORREKTUR_BAUJAHR,
                            korrigiert.getObjektId(),
                            korrigiert.getName() != null ? korrigiert.getName() : "<unbekannt>",
                            alt.getUrsprünglicherBaujahr(),
                            FeldParser.formatiereFürAnzeige(korrigiert.getBaujahr()));
                }
            }
        }
    }

    private static void validiereUndKorrigiereGesamtLeistung(WindkraftanlageRepository repo) {
        System.out.println("\n=== Überprüfung und Korrektur der Gesamtleistungen ===");

        long startValidierung = System.nanoTime();
        List<Windkraftanlage> fehlerhafte = KoordinatenValidator.findeFehlerhafeGesamtLeistung(repo.getAll());
        long endValidierung = System.nanoTime();
        double validierungMillis = (endValidierung - startValidierung) / Konstanten.NANOS_ZU_MILLIS;

        berichteValidierung("Leistungen", validierungMillis, fehlerhafte.size(), Konstanten.FEHLERHAFTE_LEISTUNGEN);

        List<KoordinatenFehler> ursprünglicheWerte = speichereUrsprünglicheWerte(fehlerhafte);

        gebeFehlerhafeGesamtLeistungAus(fehlerhafte);

        long startKorrektur = System.nanoTime();
        int korrigiertAnzahl = KoordinatenKorrektor.korrigiereFehlerhafeGesamtLeistung(repo.getAll());
        long endKorrektur = System.nanoTime();
        double korrekturMillis = (endKorrektur - startKorrektur) / Konstanten.NANOS_ZU_MILLIS;

        berichteKorrektur("Leistungen", korrekturMillis, korrigiertAnzahl, Konstanten.KORRIGIERTE_LEISTUNGEN);

        gebeKorrigierteGesamtLeistungenAus(ursprünglicheWerte, repo);

        System.out.println();
    }

    private static void gebeFehlerhafeGesamtLeistungAus(List<Windkraftanlage> fehlerhafte) {
        if (!fehlerhafte.isEmpty()) {
            System.out.println("\nFehlerhafte Datensätze (vor Korrektur):");
            for (Windkraftanlage anlage : fehlerhafte) {
                System.out.printf(Konstanten.FEHLER_LEISTUNG,
                        anlage.getObjektId(),
                        anlage.getName() != null ? anlage.getName() : "<unbekannt>",
                        anlage.getGesamtLeistungMW() != null ? anlage.getGesamtLeistungMW() : 0.0);
            }
        }
    }

    private static void gebeKorrigierteGesamtLeistungenAus(List<KoordinatenFehler> ursprünglicheWerte, 
                                                            WindkraftanlageRepository repo) {
        if (!ursprünglicheWerte.isEmpty()) {
            System.out.println("\nKorrigierte Datensätze (nach Korrektur):");
            for (KoordinatenFehler alt : ursprünglicheWerte) {
                Windkraftanlage korrigiert = repo.getAll().stream()
                        .filter(a -> a.getObjektId() == alt.getObjektId())
                        .findFirst()
                        .orElse(null);

                if (korrigiert != null && alt.getUrsprünglicheGesamtLeistungMW() != null) {
                    System.out.printf(Konstanten.KORREKTUR_LEISTUNG,
                            korrigiert.getObjektId(),
                            korrigiert.getName() != null ? korrigiert.getName() : "<unbekannt>",
                            alt.getUrsprünglicheGesamtLeistungMW(),
                            FeldParser.formatiereFürAnzeige(korrigiert.getGesamtLeistungMW()));
                }
            }
        }
    }

    private static void berichteValidierung(String bereich, double millis, int anzahl, String fehlerFormat) {
        System.out.printf(Konstanten.VALIDIERUNG_ZEIT, millis);
        System.out.printf(fehlerFormat, anzahl);
        ZeitStatistiken.zeichneZeitAuf(bereich + ": Validierung", millis);
        ZeitStatistiken.zeichneStat("Fehlerhafte " + bereich, String.valueOf(anzahl));
    }

    private static void berichteKorrektur(String bereich, double millis, int anzahl, String korrekturFormat) {
        System.out.printf(Konstanten.KORREKTUR_ZEIT, millis);
        System.out.printf(korrekturFormat, anzahl);
        ZeitStatistiken.zeichneZeitAuf(bereich + ": Korrektur", millis);
        ZeitStatistiken.zeichneStat("Korrigierte " + bereich, String.valueOf(anzahl));
    }
}
