package util;

import model.Windkraftanlage;
import java.util.ArrayList;
import java.util.List;

/**
 * Service für erweiterte Validierung und Korrektur von Datenfeldern
 * (Koordinaten, Baujahr, Gesamtleistung).
 * 
 * Pre: Liste von Windkraftanlagen mit potentiellen Fehlern
 * Post: Alle fehlerhaften Werte werden validiert, korrigiert und gemeldet
 */
public class DatenfeldValidierungsService {
    
    /**
     * Private Konstruktor - Service-Klasse, keine Instanzen erlaubt.
     */
    private DatenfeldValidierungsService() {
        // Service-Klasse
    }

    /**
     * Validiert und korrigiert Datenfeldfehlern in der Windkraftanlage.
     * Überprüft nacheinander: Koordinaten, Baujahr, Gesamtleistung.
     * 
     * Pre: anlage ist nicht null
     * Post: Fehlerhafte Felder werden korrigiert, Fehlermeldungen ausgegeben
     *
     * @param anlage die zu verarbeitende Windkraftanlage
     */
    public static void validiereUndKorrigiere(Windkraftanlage anlage) {
        long startZeit = System.nanoTime();
        
        validiereUndKorrigiereKoordinaten(anlage);
        validiereUndKorrigiereBaujahr(anlage);
        validiereUndKorrigiereGesamtLeistung(anlage);
        
        long endZeit = System.nanoTime();
        System.out.println("\nDauer der gesamten Validierung und Korrektur: " + ((endZeit - startZeit) / 1_000_000) + " ms");
    }

    // ========== KOORDINATEN-VALIDIERUNG ==========

    /**
     * Validiert und korrigiert Koordinaten der Windkraftanlage.
     * 
     * Pre: anlage ist nicht null
     * Post: Fehlerhafte Koordinaten werden korrigiert
     */
    private static void validiereUndKorrigiereKoordinaten(Windkraftanlage anlage) {
        long startZeit = System.nanoTime();
        
        List<Windkraftanlage> fehlerhaft = new ArrayList<>();
        
        if (DatenfeldValidator.findeFehlerhaftenBreitengrad(anlage.getBreitengrad()) ||
            DatenfeldValidator.findeFehlerhaftenLängengrad(anlage.getLaengengrad())) {
            fehlerhaft.add(anlage);
        }
        
        long endZeit = System.nanoTime();
        System.out.println("\n=== Überprüfung und Korrektur der Koordinaten ===");
        System.out.println("Dauer der Überprüfung: " + ((endZeit - startZeit) / 1_000_000) + " ms");
        System.out.println("Anzahl Datensätze mit fehlerhaften Koordinaten: " + fehlerhaft.size());
        
        if (fehlerhaft.isEmpty()) {
            return;
        }
        
        // Fehlerhafte Datensätze vor Korrektur anzeigen
        System.out.println("\nFehlerhafte Datensätze (vor Korrektur):");
        for (Windkraftanlage w : fehlerhaft) {
            System.out.printf("ID %d | %s | Breitengrad: %.4f | Längengrad: %.4f%n",
                w.getObjektId(), w.getName(), w.getBreitengrad(), w.getLaengengrad());
        }
        
        // Korrigiere Datensätze
        startZeit = System.nanoTime();
        
        int anzahlKorrigiert = 0;
        List<String> korrigierteMeldungen = new ArrayList<>();
        
        for (Windkraftanlage w : fehlerhaft) {
            Double altBreitengrad = w.getBreitengrad();
            Double altLängengrad = w.getLaengengrad();
            
            Double neuBreitengrad = altBreitengrad;
            Double neuLängengrad = altLängengrad;
            
            if (DatenfeldValidator.findeFehlerhaftenBreitengrad(altBreitengrad)) {
                Double korrigiert = DatenfeldKorrektor.korrigiereFehlerhaftenBreitengrad(altBreitengrad);
                if (korrigiert != null) {
                    neuBreitengrad = korrigiert;
                }
            }
            
            if (DatenfeldValidator.findeFehlerhaftenLängengrad(altLängengrad)) {
                Double korrigiert = DatenfeldKorrektor.korrigiereFehlerhaftenLängengrad(altLängengrad);
                if (korrigiert != null) {
                    neuLängengrad = korrigiert;
                }
            }
            
            w.setBreitengrad(neuBreitengrad);
            w.setLaengengrad(neuLängengrad);
            
            anzahlKorrigiert++;
            korrigierteMeldungen.add(String.format(
                "ID %d | %s | Alt: (%.4f, %.4f) -> Neu: (%.4f, %.4f)",
                w.getObjektId(), w.getName(), altBreitengrad, altLängengrad, neuBreitengrad, neuLängengrad
            ));
        }
        
        endZeit = System.nanoTime();
        System.out.println("\nDauer der Korrektur: " + ((endZeit - startZeit) / 1_000_000) + " ms");
        System.out.println("Anzahl korrigierte Datensätze: " + anzahlKorrigiert);
        
        if (!korrigierteMeldungen.isEmpty()) {
            System.out.println("\nKorrigierte Datensätze (nach Korrektur):");
            for (String msg : korrigierteMeldungen) {
                System.out.println(msg);
            }
        }
    }

    // ========== BAUJAHR-VALIDIERUNG ==========

    /**
     * Validiert und korrigiert Baujahr der Windkraftanlage.
     * 
     * Pre: anlage ist nicht null
     * Post: Fehlerhafte Baujahre werden korrigiert
     */
    private static void validiereUndKorrigiereBaujahr(Windkraftanlage anlage) {
        long startZeit = System.nanoTime();
        
        List<Windkraftanlage> fehlerhaft = new ArrayList<>();
        
        if (DatenfeldValidator.findeFehlerhaftesBaujahr(anlage.getBaujahr())) {
            fehlerhaft.add(anlage);
        }
        
        long endZeit = System.nanoTime();
        System.out.println("\n=== Überprüfung und Korrektur der Baujahre ===");
        System.out.println("Dauer der Überprüfung: " + ((endZeit - startZeit) / 1_000_000) + " ms");
        System.out.println("Anzahl Datensätze mit fehlerhaftem Baujahr: " + fehlerhaft.size());
        
        if (fehlerhaft.isEmpty()) {
            return;
        }
        
        // Fehlerhafte Datensätze vor Korrektur anzeigen
        System.out.println("\nFehlerhafte Datensätze (vor Korrektur):");
        for (Windkraftanlage w : fehlerhaft) {
            System.out.printf("ID %d | %s | Baujahr: %s%n",
                w.getObjektId(), w.getName(), 
                FeldParser.formatiereFürAnzeige(w.getBaujahr()));
        }
        
        // Korrigiere Datensätze
        startZeit = System.nanoTime();
        
        int anzahlKorrigiert = 0;
        List<String> korrigierteMeldungen = new ArrayList<>();
        
        for (Windkraftanlage w : fehlerhaft) {
            Integer altBaujahr = w.getBaujahr();
            
            Integer neuBaujahr = null;
            if (DatenfeldValidator.findeFehlerhaftesBaujahr(altBaujahr)) {
                Integer korrigiert = DatenfeldKorrektor.korrigiereFehlerhaftesBaujahr(altBaujahr);
                if (korrigiert != null) {
                    neuBaujahr = korrigiert;
                }
            }
            
            w.setBaujahr(neuBaujahr);
            
            anzahlKorrigiert++;
            korrigierteMeldungen.add(String.format(
                "ID %d | %s | Alt: %s -> Neu: %s",
                w.getObjektId(), w.getName(), 
                FeldParser.formatiereFürAnzeige(altBaujahr),
                FeldParser.formatiereFürAnzeige(neuBaujahr)
            ));
        }
        
        endZeit = System.nanoTime();
        System.out.println("\nDauer der Korrektur: " + ((endZeit - startZeit) / 1_000_000) + " ms");
        System.out.println("Anzahl korrigierte Baujahre: " + anzahlKorrigiert);
        
        if (!korrigierteMeldungen.isEmpty()) {
            System.out.println("\nKorrigierte Datensätze (nach Korrektur):");
            for (String msg : korrigierteMeldungen) {
                System.out.println(msg);
            }
        }
    }

    // ========== GESAMTLEISTUNG-VALIDIERUNG ==========

    /**
     * Validiert und korrigiert Gesamtleistung der Windkraftanlage.
     * 
     * Pre: anlage ist nicht null
     * Post: Fehlerhafte Gesamtleistungen werden korrigiert
     */
    private static void validiereUndKorrigiereGesamtLeistung(Windkraftanlage anlage) {
        long startZeit = System.nanoTime();
        
        List<Windkraftanlage> fehlerhaft = new ArrayList<>();
        
        if (DatenfeldValidator.findeFehlerhafteGesamtLeistung(anlage.getGesamtLeistungMW())) {
            fehlerhaft.add(anlage);
        }
        
        long endZeit = System.nanoTime();
        System.out.println("\n=== Überprüfung und Korrektur der Gesamtleistungen ===");
        System.out.println("Dauer der Überprüfung: " + ((endZeit - startZeit) / 1_000_000) + " ms");
        System.out.println("Anzahl Datensätze mit fehlerhafter Gesamtleistung: " + fehlerhaft.size());
        
        if (fehlerhaft.isEmpty()) {
            return;
        }
        
        // Fehlerhafte Datensätze vor Korrektur anzeigen
        System.out.println("\nFehlerhafte Datensätze (vor Korrektur):");
        for (Windkraftanlage w : fehlerhaft) {
            System.out.printf("ID %d | %s | Gesamtleistung: %s MW%n",
                w.getObjektId(), w.getName(), 
                FeldParser.formatiereFürAnzeige(w.getGesamtLeistungMW()));
        }
        
        // Korrigiere Datensätze
        startZeit = System.nanoTime();
        
        int anzahlKorrigiert = 0;
        List<String> korrigierteMeldungen = new ArrayList<>();
        
        for (Windkraftanlage w : fehlerhaft) {
            Double altGesamtLeistung = w.getGesamtLeistungMW();
            
            Double neuGesamtLeistung = null;
            if (DatenfeldValidator.findeFehlerhafteGesamtLeistung(altGesamtLeistung)) {
                Double korrigiert = DatenfeldKorrektor.korrigiereFehlerhafteGesamtLeistung(altGesamtLeistung);
                if (korrigiert != null) {
                    neuGesamtLeistung = korrigiert;
                }
            }
            
            w.setGesamtLeistungMW(neuGesamtLeistung);
            
            anzahlKorrigiert++;
            korrigierteMeldungen.add(String.format(
                "ID %d | %s | Alt: %s MW -> Neu: %s",
                w.getObjektId(), w.getName(), 
                FeldParser.formatiereFürAnzeige(altGesamtLeistung),
                FeldParser.formatiereFürAnzeige(neuGesamtLeistung)
            ));
        }
        
        endZeit = System.nanoTime();
        System.out.println("\nDauer der Korrektur: " + ((endZeit - startZeit) / 1_000_000) + " ms");
        System.out.println("Anzahl korrigierte Gesamtleistungen: " + anzahlKorrigiert);
        
        if (!korrigierteMeldungen.isEmpty()) {
            System.out.println("\nKorrigierte Datensätze (nach Korrektur):");
            for (String msg : korrigierteMeldungen) {
                System.out.println(msg);
            }
        }
    }
}
