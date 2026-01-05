package util;

import java.util.ArrayList;
import java.util.List;
import model.Windkraftanlage;

/**
 * Utility-Klasse fuer Sortierungen von {@link model.Windkraftanlage}.
 * <p>
 * Diese Klasse stellt spezialisierte Sortiermethoden fuer Windkraftanlagen bereit,
 * die nach verschiedenen Kriterien sortieren koennen. Alle Implementierungen verwenden
 * den stabilen Merge-Sort-Algorithmus, der garantiert, dass Elemente mit gleichen
 * Sortierkriterien ihre relative Reihenfolge beibehalten.
 * </p>
 *
 * <p><b>Design-Prinzipien:</b>
 * <ul>
 *   <li><b>Single Responsibility:</b> Diese Klasse ist ausschließlich fuer das Sortieren
 *       von Windkraftanlagen-Listen zustaendig. Jede Methode behandelt genau ein Sortierkriterium.</li>
 *   <li><b>KISS (Keep It Simple, Stupid):</b> Verwendet einfachen, lesbaren Merge-Sort
 *       statt komplexer Algorithmen. Explizite Schleifen statt Stream API.</li>
 *   <li><b>Modularisierung:</b> Separate Methoden fuer verschiedene Sortierkriterien,
 *       wiederverwendbare Vergleichshilfsmethoden.</li>
 * </ul>
 * </p>
 *
 * <p><b>Algorithmische Eigenschaften:</b>
 * <ul>
 *   <li>Zeitkomplexitaet: O(n log n) in allen Faellen (Best, Average, Worst Case)</li>
 *   <li>Speicherkomplexitaet: O(n) fuer temporaere Arrays</li>
 *   <li>Stabilitaet: Ja - gleiche Elemente behalten ihre Reihenfolge</li>
 *   <li>In-Place: Nein - arbeitet auf Kopien der Eingabeliste</li>
 * </ul>
 * </p>
 *
 * <p><b>Kontrakt:</b>
 * <ul>
 *   <li>Pre: Eingabelisten duerfen nicht null sein und sollten vom Aufrufer validiert werden.</li>
 *   <li>Post: Alle oeffentlichen Methoden geben eine neue, sortierte Liste zurueck.</li>
 *   <li>Post: Die Original-Eingabeliste bleibt unveraendert (Defensive Kopie).</li>
 *   <li>Post: Die Rueckgabeliste enthaelt dieselben Objekt-Referenzen wie die Eingabe.</li>
 * </ul>
 * </p>
 *
 * @see model.Windkraftanlage
 * @see java.util.List
 */
public final class WindkraftanlagenSortierer {

    /**
     * Privater Konstruktor verhindert Instanziierung.
     * Dies ist eine Utility-Klasse mit nur statischen Methoden.
     */
    private WindkraftanlagenSortierer() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Sortiert Windkraftanlagen nach geographischer und alphabetischer Ordnung.
     * <p>
     * Sortierkriterien in dieser Prioritaet:
     * <ol>
     *   <li>Ort (aufsteigend, case-insensitive, null-Werte ans Ende)</li>
     *   <li>Name (aufsteigend, case-insensitive, null-Werte ans Ende)</li>
     *   <li>Objekt-ID (aufsteigend, numerisch)</li>
     * </ol>
     * </p>
     *
     * <p>
     * Verwendungszweck: Ideal fuer geographische Auflistungen, bei denen Anlagen
     * nach Standort gruppiert und innerhalb des Standorts alphabetisch sortiert werden sollen.
     * </p>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: {@code anlagen} darf nicht null sein (Aufrufer muss validieren).</li>
     *   <li>Post: Rueckgabe ist eine neue Liste mit stabiler Sortierung.</li>
     *   <li>Post: Original-Liste {@code anlagen} bleibt vollstaendig unveraendert.</li>
     *   <li>Post: Null-Werte in Ort/Name werden ans Ende sortiert.</li>
     * </ul>
     *
     * @param anlagen Liste der zu sortierenden Windkraftanlagen (nicht null)
     * @return neue Liste, sortiert nach Ort, dann Name, dann ObjektId
     */
    public static List<Windkraftanlage> sortiereNachOrtNameId(List<Windkraftanlage> anlagen) {
        List<Windkraftanlage> kopie = kopiereListe(anlagen);
        mergeSortOrtNameId(kopie);
        return kopie;
    }

    /**
     * Sortiert Windkraftanlagen nach Leistungsfaehigkeit und Alter.
     * <p>
     * Sortierkriterien in dieser Prioritaet:
     * <ol>
     *   <li>Gesamtleistung in MW (absteigend - staerkste Anlagen zuerst, null-Werte ans Ende)</li>
     *   <li>Baujahr (aufsteigend - aeltere Anlagen zuerst, null-Werte ans Ende)</li>
     *   <li>Name (aufsteigend, case-insensitive, als stabiler Tiebreaker)</li>
     * </ol>
     * </p>
     *
     * <p>
     * Verwendungszweck: Ideal fuer technische Analysen, bei denen leistungsstarke
     * Anlagen priorisiert werden sollen. Das Baujahr als Sekundaerkriterium ermoeglicht
     * die Identifikation alter, aber leistungsstarker Anlagen.
     * </p>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: {@code anlagen} darf nicht null sein (Aufrufer muss validieren).</li>
     *   <li>Post: Rueckgabe ist eine neue Liste, sortiert nach Leistung (desc), Baujahr (asc), Name (asc).</li>
     *   <li>Post: Original-Liste {@code anlagen} bleibt vollstaendig unveraendert.</li>
     *   <li>Post: Anlagen ohne Leistungsangabe (null) werden ans Ende sortiert.</li>
     *   <li>Post: Anlagen ohne Baujahr (null) werden innerhalb gleicher Leistung ans Ende sortiert.</li>
     * </ul>
     *
     * @param anlagen Liste der zu sortierenden Windkraftanlagen (nicht null)
     * @return neue Liste, sortiert nach Leistung (absteigend), Baujahr, Name
     */
    public static List<Windkraftanlage> sortiereNachLeistungBaujahrName(List<Windkraftanlage> anlagen) {
        List<Windkraftanlage> kopie = kopiereListe(anlagen);
        mergeSortLeistungBaujahrName(kopie);
        return kopie;
    }

    /**
     * Sortiert Windkraftanlagen nach administrativer Gebietsstruktur.
     * <p>
     * Sortierkriterien in dieser Prioritaet:
     * <ol>
     *   <li>Landkreis (aufsteigend, case-insensitive, null-Werte ans Ende)</li>
     *   <li>Ort (aufsteigend, case-insensitive, null-Werte ans Ende)</li>
     *   <li>Name (aufsteigend, case-insensitive, als stabiler Tiebreaker)</li>
     * </ol>
     * </p>
     *
     * <p>
     * Verwendungszweck: Ideal fuer administrative Berichte und regionale Analysen,
     * bei denen Anlagen nach Verwaltungseinheiten gruppiert werden sollen.
     * Ermoeglicht schnelle Uebersichten nach Landkreis und detaillierte Aufschluesse
     * nach Orten innerhalb der Landkreise.
     * </p>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: {@code anlagen} darf nicht null sein (Aufrufer muss validieren).</li>
     *   <li>Post: Rueckgabe ist eine neue Liste, sortiert nach Landkreis, Ort, Name.</li>
     *   <li>Post: Original-Liste {@code anlagen} bleibt vollstaendig unveraendert.</li>
     *   <li>Post: Null-Werte in Landkreis/Ort/Name werden ans Ende sortiert.</li>
     * </ul>
     *
     * @param anlagen Liste der zu sortierenden Windkraftanlagen (nicht null)
     * @return neue Liste, sortiert nach Landkreis, Ort, Name (alle aufsteigend)
     */
    public static List<Windkraftanlage> sortiereNachLandkreisOrtName(List<Windkraftanlage> anlagen) {
        List<Windkraftanlage> kopie = kopiereListe(anlagen);
        mergeSortLandkreisOrtName(kopie);
        return kopie;
    }

    /**
     * Erstellt eine flache Kopie der Eingabeliste (Shallow Copy).
     * <p>
     * Diese Methode erzeugt eine neue ArrayList und kopiert alle Referenzen
     * der Windkraftanlagen-Objekte hinein. Die Objekte selbst werden NICHT
     * geklont (keine Deep Copy), sondern nur die Referenzen kopiert.
     * Dies ist ausreichend, da die Sortiermethoden nur die Reihenfolge
     * aendern, aber die Objekte selbst nicht modifizieren.
     * </p>
     *
     * <p><b>Rationale:</b></p>
     * <ul>
     *   <li>Defensive Kopie schuetzt die Original-Liste vor Modifikation</li>
     *   <li>Shallow Copy ist ausreichend, da nur Reihenfolge geaendert wird</li>
     *   <li>Speichereffizient: O(n) fuer Referenzen statt O(n*m) fuer Deep Copy</li>
     *   <li>Folgt KISS-Prinzip: Einfache for-Schleife statt Stream API</li>
     * </ul>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: {@code anlagen} darf nicht null sein.</li>
     *   <li>Post: Rueckgabe ist eine neue ArrayList mit gleicher Groesse.</li>
     *   <li>Post: Rueckgabeliste enthaelt dieselben Objekt-Referenzen in gleicher Reihenfolge.</li>
     *   <li>Post: Aenderungen an der Rueckgabeliste beeinflussen die Eingabeliste nicht.</li>
     * </ul>
     *
     * @param anlagen Quellliste, die kopiert werden soll (nicht null)
     * @return neue ArrayList mit kopierten Referenzen
     */
    private static List<Windkraftanlage> kopiereListe(List<Windkraftanlage> anlagen) {
        List<Windkraftanlage> kopie = new ArrayList<>();
        for (Windkraftanlage anlage : anlagen) {
            kopie.add(anlage);
        }
        return kopie;
    }
    /**
     * Fuehrt stabilen Merge-Sort nach Ort/Name/ID auf der uebergebenen Liste aus.
     * <p>
     * Diese Methode dient als Wrapper fuer den rekursiven Merge-Sort-Algorithmus.
     * Sie konvertiert die Liste in ein Array (fuer effizienten Zugriff), fuehrt
     * die rekursive Sortierung aus und schreibt das Ergebnis zurueck in die Liste.
     * </p>
     *
     * <p><b>Implementierungsdetails:</b></p>
     * <ul>
     *   <li>Konvertierung Liste → Array fuer O(1)-Elementzugriff</li>
     *   <li>Allokation eines Hilfsarrays gleicher Groesse fuer Merge-Operationen</li>
     *   <li>Rekursive Sortierung durch {@link #mergeSortOrtNameIdRec}</li>
     *   <li>Rueckschreiben des sortierten Arrays in die Original-Liste</li>
     * </ul>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: {@code liste} darf nicht null sein.</li>
     *   <li>Post: {@code liste} ist in-place sortiert nach Ort, Name, ObjektId.</li>
     *   <li>Post: Sortierung ist stabil (gleiche Elemente behalten Reihenfolge).</li>
     *   <li>Komplexitaet: Zeit O(n log n), Speicher O(n) fuer temporaeres Array.</li>
     * </ul>
     *
     * @param liste die zu sortierende Liste (wird in-place modifiziert)
     */
    private static void mergeSortOrtNameId(List<Windkraftanlage> liste) {
        Windkraftanlage[] anlagenArray = liste.toArray(new Windkraftanlage[0]);
        Windkraftanlage[] hilfsArray = new Windkraftanlage[anlagenArray.length];
        mergeSortOrtNameIdRec(anlagenArray, hilfsArray, 0, anlagenArray.length - 1);
        for (int index = 0; index < anlagenArray.length; index++) {
            liste.set(index, anlagenArray[index]);
        }
    }

    /**
     * Rekursive Kernimplementierung des Merge-Sort-Algorithmus fuer Ort/Name/ID-Sortierung.
     * <p>
     * Klassischer Divide-and-Conquer-Ansatz:
     * <ol>
     *   <li><b>Divide:</b> Teile den Bereich [linkeGrenze, rechteGrenze] in der Mitte</li>
     *   <li><b>Conquer:</b> Sortiere beide Haelften rekursiv</li>
     *   <li><b>Combine:</b> Fuege die sortierten Haelften zu einem sortierten Bereich zusammen</li>
     * </ol>
     * </p>
     *
     * <p><b>Merge-Strategie:</b></p>
     * <ul>
     *   <li>Zwei Zeiger (linkerIndex, rechterIndex) durchlaufen die beiden sortierten Haelften</li>
     *   <li>Kleineres Element wird ins Hilfsarray geschrieben, Zeiger rueckt vor</li>
     *   <li>Verbleibende Elemente werden nach Ablauf einer Haelfte kopiert</li>
     *   <li>Hilfsarray wird zurueck ins Hauptarray kopiert</li>
     * </ul>
     *
     * <p><b>Stabilitaet:</b> Garantiert durch {@code <=} im Vergleich - bei Gleichheit
     * wird das Element aus der linken Haelfte bevorzugt, wodurch die urspruengliche
     * Reihenfolge erhalten bleibt.</p>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: {@code anlagenArray} und {@code hilfsArray} duerfen nicht null sein.</li>
     *   <li>Pre: {@code linkeGrenze} und {@code rechteGrenze} sind gueltige Array-Indizes.</li>
     *   <li>Pre: {@code hilfsArray.length >= anlagenArray.length}.</li>
     *   <li>Post: Bereich [linkeGrenze, rechteGrenze] in {@code anlagenArray} ist sortiert.</li>
     *   <li>Post: {@code hilfsArray} enthaelt temporaere Zwischenergebnisse.</li>
     * </ul>
     *
     * @param anlagenArray Hauptarray mit zu sortierenden Windkraftanlagen
     * @param hilfsArray Temporaeres Array gleicher Groesse fuer Merge-Operationen
     * @param linkeGrenze Startindex des zu sortierenden Bereichs (inklusiv)
     * @param rechteGrenze Endindex des zu sortierenden Bereichs (inklusiv)
     */
    private static void mergeSortOrtNameIdRec(Windkraftanlage[] anlagenArray, Windkraftanlage[] hilfsArray, int linkeGrenze, int rechteGrenze) {
        if (linkeGrenze >= rechteGrenze) {
            return;
        }
        int mitte = (linkeGrenze + rechteGrenze) / 2;
        mergeSortOrtNameIdRec(anlagenArray, hilfsArray, linkeGrenze, mitte);
        mergeSortOrtNameIdRec(anlagenArray, hilfsArray, mitte + 1, rechteGrenze);
        int linkerIndex = linkeGrenze;
        int rechterIndex = mitte + 1;
        int zielIndex = linkeGrenze;
        while (linkerIndex <= mitte && rechterIndex <= rechteGrenze) {
            if (vergleicheOrtNameId(anlagenArray[linkerIndex], anlagenArray[rechterIndex]) <= 0) {
                hilfsArray[zielIndex] = anlagenArray[linkerIndex];
                linkerIndex++;
                zielIndex++;
            } else {
                hilfsArray[zielIndex] = anlagenArray[rechterIndex];
                rechterIndex++;
                zielIndex++;
            }
        }
        while (linkerIndex <= mitte) {
            hilfsArray[zielIndex] = anlagenArray[linkerIndex];
            linkerIndex++;
            zielIndex++;
        }
        while (rechterIndex <= rechteGrenze) {
            hilfsArray[zielIndex] = anlagenArray[rechterIndex];
            rechterIndex++;
            zielIndex++;
        }
        for (int index = linkeGrenze; index <= rechteGrenze; index++) {
            anlagenArray[index] = hilfsArray[index];
        }
    }

    /**
     * Fuehrt stabilen Merge-Sort nach Leistung/Baujahr/Name auf der uebergebenen Liste aus.
     * <p>
     * Wrapper-Methode fuer den rekursiven Merge-Sort-Algorithmus mit Leistungskriterien.
     * Konvertiert Liste in Array, sortiert rekursiv und schreibt Ergebnis zurueck.
     * </p>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: {@code liste} darf nicht null sein.</li>
     *   <li>Post: {@code liste} ist sortiert nach Leistung (desc), Baujahr (asc), Name (asc).</li>
     *   <li>Post: Sortierung ist stabil (gleiche Elemente behalten Reihenfolge).</li>
     *   <li>Komplexitaet: Zeit O(n log n), Speicher O(n).</li>
     * </ul>
     *
     * @param liste die zu sortierende Liste (wird in-place modifiziert)
     */
    private static void mergeSortLeistungBaujahrName(List<Windkraftanlage> liste) {
        Windkraftanlage[] anlagenArray = liste.toArray(new Windkraftanlage[0]);
        Windkraftanlage[] hilfsArray = new Windkraftanlage[anlagenArray.length];
        mergeSortLeistungBaujahrNameRec(anlagenArray, hilfsArray, 0, anlagenArray.length - 1);
        for (int index = 0; index < anlagenArray.length; index++) {
            liste.set(index, anlagenArray[index]);
        }
    }

    /**
     * Rekursive Kernimplementierung des Merge-Sort fuer Leistung/Baujahr/Name-Sortierung.
     * <p>
     * Verwendet denselben Divide-and-Conquer-Ansatz wie die Ort/Name/ID-Variante,
     * aber mit angepasster Vergleichslogik ueber
     * {@link #vergleicheLeistungBaujahrName(Windkraftanlage, Windkraftanlage)}.
     * </p>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: Arrays und Indizes sind gueltig (analog zu mergeSortOrtNameIdRec).</li>
     *   <li>Post: Bereich [linkeGrenze, rechteGrenze] ist sortiert nach Leistung/Baujahr/Name.</li>
     * </ul>
     *
     * @param anlagenArray Hauptarray mit zu sortierenden Windkraftanlagen
     * @param hilfsArray Temporaeres Array fuer Merge-Operationen
     * @param linkeGrenze Startindex (inklusiv)
     * @param rechteGrenze Endindex (inklusiv)
     * @see #mergeSortOrtNameIdRec(Windkraftanlage[], Windkraftanlage[], int, int)
     */
    private static void mergeSortLeistungBaujahrNameRec(Windkraftanlage[] anlagenArray, Windkraftanlage[] hilfsArray, int linkeGrenze, int rechteGrenze) {
        if (linkeGrenze >= rechteGrenze) {
            return;
        }
        int mitte = (linkeGrenze + rechteGrenze) / 2;
        mergeSortLeistungBaujahrNameRec(anlagenArray, hilfsArray, linkeGrenze, mitte);
        mergeSortLeistungBaujahrNameRec(anlagenArray, hilfsArray, mitte + 1, rechteGrenze);
        int linkerIndex = linkeGrenze;
        int rechterIndex = mitte + 1;
        int zielIndex = linkeGrenze;
        while (linkerIndex <= mitte && rechterIndex <= rechteGrenze) {
            if (vergleicheLeistungBaujahrName(anlagenArray[linkerIndex], anlagenArray[rechterIndex]) <= 0) {
                hilfsArray[zielIndex] = anlagenArray[linkerIndex];
                linkerIndex++;
                zielIndex++;
            } else {
                hilfsArray[zielIndex] = anlagenArray[rechterIndex];
                rechterIndex++;
                zielIndex++;
            }
        }
        while (linkerIndex <= mitte) {
            hilfsArray[zielIndex] = anlagenArray[linkerIndex];
            linkerIndex++;
            zielIndex++;
        }
        while (rechterIndex <= rechteGrenze) {
            hilfsArray[zielIndex] = anlagenArray[rechterIndex];
            rechterIndex++;
            zielIndex++;
        }
        for (int index = linkeGrenze; index <= rechteGrenze; index++) {
            anlagenArray[index] = hilfsArray[index];
        }
    }

    /**
     * Fuehrt stabilen Merge-Sort nach Landkreis/Ort/Name auf der uebergebenen Liste aus.
     * <p>
     * Wrapper-Methode fuer den rekursiven Merge-Sort-Algorithmus mit administrativen Kriterien.
     * Konvertiert Liste in Array, sortiert rekursiv und schreibt Ergebnis zurueck.
     * </p>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: {@code liste} darf nicht null sein.</li>
     *   <li>Post: {@code liste} ist sortiert nach Landkreis, Ort, Name (alle aufsteigend).</li>
     *   <li>Post: Sortierung ist stabil (gleiche Elemente behalten Reihenfolge).</li>
     *   <li>Komplexitaet: Zeit O(n log n), Speicher O(n).</li>
     * </ul>
     *
     * @param liste die zu sortierende Liste (wird in-place modifiziert)
     */
    private static void mergeSortLandkreisOrtName(List<Windkraftanlage> liste) {
        Windkraftanlage[] anlagenArray = liste.toArray(new Windkraftanlage[0]);
        Windkraftanlage[] hilfsArray = new Windkraftanlage[anlagenArray.length];
        mergeSortLandkreisOrtNameRec(anlagenArray, hilfsArray, 0, anlagenArray.length - 1);
        for (int index = 0; index < anlagenArray.length; index++) {
            liste.set(index, anlagenArray[index]);
        }
    }

    /**
     * Rekursive Kernimplementierung des Merge-Sort fuer Landkreis/Ort/Name-Sortierung.
     * <p>
     * Verwendet denselben Divide-and-Conquer-Ansatz wie die anderen Varianten,
     * aber mit angepasster Vergleichslogik ueber
     * {@link #vergleicheLandkreisOrtName(Windkraftanlage, Windkraftanlage)}.
     * </p>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: Arrays und Indizes sind gueltig (analog zu mergeSortOrtNameIdRec).</li>
     *   <li>Post: Bereich [linkeGrenze, rechteGrenze] ist sortiert nach Landkreis/Ort/Name.</li>
     * </ul>
     *
     * @param anlagenArray Hauptarray mit zu sortierenden Windkraftanlagen
     * @param hilfsArray Temporaeres Array fuer Merge-Operationen
     * @param linkeGrenze Startindex (inklusiv)
     * @param rechteGrenze Endindex (inklusiv)
     * @see #mergeSortOrtNameIdRec(Windkraftanlage[], Windkraftanlage[], int, int)
     */
    private static void mergeSortLandkreisOrtNameRec(Windkraftanlage[] anlagenArray, Windkraftanlage[] hilfsArray, int linkeGrenze, int rechteGrenze) {
        if (linkeGrenze >= rechteGrenze) {
            return;
        }
        int mitte = (linkeGrenze + rechteGrenze) / 2;
        mergeSortLandkreisOrtNameRec(anlagenArray, hilfsArray, linkeGrenze, mitte);
        mergeSortLandkreisOrtNameRec(anlagenArray, hilfsArray, mitte + 1, rechteGrenze);
        int linkerIndex = linkeGrenze;
        int rechterIndex = mitte + 1;
        int zielIndex = linkeGrenze;
        while (linkerIndex <= mitte && rechterIndex <= rechteGrenze) {
            if (vergleicheLandkreisOrtName(anlagenArray[linkerIndex], anlagenArray[rechterIndex]) <= 0) {
                hilfsArray[zielIndex] = anlagenArray[linkerIndex];
                linkerIndex++;
                zielIndex++;
            } else {
                hilfsArray[zielIndex] = anlagenArray[rechterIndex];
                rechterIndex++;
                zielIndex++;
            }
        }
        while (linkerIndex <= mitte) {
            hilfsArray[zielIndex] = anlagenArray[linkerIndex];
            linkerIndex++;
            zielIndex++;
        }
        while (rechterIndex <= rechteGrenze) {
            hilfsArray[zielIndex] = anlagenArray[rechterIndex];
            rechterIndex++;
            zielIndex++;
        }
        for (int index = linkeGrenze; index <= rechteGrenze; index++) {
            anlagenArray[index] = hilfsArray[index];
        }
    }

    /**
     * Vergleicht zwei Windkraftanlagen nach Ort, Name und ObjektId.
     * <p>
     * Hierarchische Vergleichsstrategie mit drei Stufen:
     * <ol>
     *   <li>Vergleich nach Ort (primaer, case-insensitive)</li>
     *   <li>Bei gleichem Ort: Vergleich nach Name (sekundaer, case-insensitive)</li>
     *   <li>Bei gleichem Name: Vergleich nach ObjektId (tertiaer, numerisch)</li>
     * </ol>
     * </p>
     *
     * <p><b>Null-Behandlung:</b> Null-Werte werden als "groesser" betrachtet
     * und somit ans Ende sortiert. Dies ist sinnvoll, da unbekannte Orte/Namen
     * nicht am Anfang der Liste erscheinen sollen.</p>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: {@code erste} und {@code zweite} duerfen nicht null sein.</li>
     *   <li>Post: Rueckgabe &lt; 0 wenn erste &lt; zweite</li>
     *   <li>Post: Rueckgabe = 0 wenn erste == zweite (nach allen Kriterien)</li>
     *   <li>Post: Rueckgabe &gt; 0 wenn erste &gt; zweite</li>
     * </ul>
     *
     * @param erste erste Windkraftanlage im Vergleich
     * @param zweite zweite Windkraftanlage im Vergleich
     * @return negativer Wert, null oder positiver Wert (siehe {@link Comparable})
     */
    private static int vergleicheOrtNameId(Windkraftanlage erste, Windkraftanlage zweite) {
        // Primärkriterium: Ort (null-sichere, case-insensitive Sortierung)
        int ortVergleich = vergleicheStringNullSicher(erste.getOrt(), zweite.getOrt());
        if (ortVergleich != 0) {
            return ortVergleich;
        }

        // Sekundärkriterium: Name
        int nameVergleich = vergleicheStringNullSicher(erste.getName(), zweite.getName());
        if (nameVergleich != 0) {
            return nameVergleich;
        }

        // Tertiärkriterium: ObjektId (ganzzahliger Vergleich)
        return vergleicheInt(erste.getObjektId(), zweite.getObjektId());
    }

    /**
     * Vergleicht zwei Windkraftanlagen nach Leistung, Baujahr und Name.
     * <p>
     * Hierarchische Vergleichsstrategie mit drei Stufen:
     * <ol>
     *   <li>Vergleich nach Gesamtleistung (primaer, absteigend - hoehere Werte zuerst)</li>
     *   <li>Bei gleicher Leistung: Vergleich nach Baujahr (sekundaer, aufsteigend - aeltere zuerst)</li>
     *   <li>Bei gleichem Baujahr: Vergleich nach Name (tertiaer, aufsteigend, case-insensitive)</li>
     * </ol>
     * </p>
     *
     * <p><b>Besonderheit:</b> Primaerkriterium ist ABSTEIGEND sortiert, um die
     * leistungsstaerksten Anlagen an den Anfang der Liste zu bringen.</p>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: {@code erste} und {@code zweite} duerfen nicht null sein.</li>
     *   <li>Post: Rueckgabe entspricht Comparable-Konvention</li>
     *   <li>Post: Null-Werte in Leistung/Baujahr werden ans Ende sortiert</li>
     * </ul>
     *
     * @param erste erste Windkraftanlage im Vergleich
     * @param zweite zweite Windkraftanlage im Vergleich
     * @return Vergleichsergebnis (negativ/null/positiv)
     */
    private static int vergleicheLeistungBaujahrName(Windkraftanlage erste, Windkraftanlage zweite) {
        // Primär: Gesamtleistung (absteigend, null-sicher)
        int leistungVergleich = vergleicheFloatNullSicherAbsteigend(erste.getGesamtLeistungMW(), zweite.getGesamtLeistungMW());
        if (leistungVergleich != 0) {
            return leistungVergleich;
        }

        // Sekundär: Baujahr (aufsteigend, null-sicher)
        int baujahrVergleich = vergleicheIntegerNullSicher(erste.getBaujahr(), zweite.getBaujahr());
        if (baujahrVergleich != 0) {
            return baujahrVergleich;
        }

        // Tertiär: Name als stabiler Tiebreaker
        return vergleicheStringNullSicher(erste.getName(), zweite.getName());
    }

    /**
     * Vergleicht zwei Windkraftanlagen nach Landkreis, Ort und Name.
     * <p>
     * Hierarchische Vergleichsstrategie mit drei Stufen:
     * <ol>
     *   <li>Vergleich nach Landkreis (primaer, case-insensitive, aufsteigend)</li>
     *   <li>Bei gleichem Landkreis: Vergleich nach Ort (sekundaer, case-insensitive, aufsteigend)</li>
     *   <li>Bei gleichem Ort: Vergleich nach Name (tertiaer, case-insensitive, aufsteigend)</li>
     * </ol>
     * </p>
     *
     * <p><b>Verwendungszweck:</b> Ermoeglicht administrative Gruppierung nach
     * Verwaltungseinheiten, ideal fuer regionale Berichte.</p>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: {@code erste} und {@code zweite} duerfen nicht null sein.</li>
     *   <li>Post: Rueckgabe entspricht Comparable-Konvention</li>
     *   <li>Post: Null-Werte werden ans Ende sortiert</li>
     * </ul>
     *
     * @param erste erste Windkraftanlage im Vergleich
     * @param zweite zweite Windkraftanlage im Vergleich
     * @return Vergleichsergebnis (negativ/null/positiv)
     */
    private static int vergleicheLandkreisOrtName(Windkraftanlage erste, Windkraftanlage zweite) {
        // Primär: Landkreis
        int landkreisVergleich = vergleicheStringNullSicher(erste.getLandkreis(), zweite.getLandkreis());
        if (landkreisVergleich != 0) {
            return landkreisVergleich;
        }

        // Sekundär: Ort
        int ortVergleich = vergleicheStringNullSicher(erste.getOrt(), zweite.getOrt());
        if (ortVergleich != 0) {
            return ortVergleich;
        }

        // Tertiär: Name
        return vergleicheStringNullSicher(erste.getName(), zweite.getName());
    }

    /**
     * Vergleicht zwei Strings null-sicher und case-insensitive.
     * <p>
     * Vergleichslogik:
     * <ul>
     *   <li>Beide null: Gleichwertig (return 0)</li>
     *   <li>Nur ersterWert null: ersterWert > zweiterWert (return 1) → ans Ende</li>
     *   <li>Nur zweiterWert null: ersterWert &lt; zweiterWert (return -1) → ans Ende</li>
     *   <li>Beide nicht-null: Lexikographischer Vergleich (case-insensitive)</li>
     * </ul>
     * </p>
     *
     * <p><b>Design-Entscheidung:</b> Null-Werte werden als "groesser" behandelt,
     * sodass Elemente mit unbekannten/fehlenden Werten ans Ende der Liste sortiert
     * werden. Dies verbessert die Lesbarkeit von Reports.</p>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: Keine Vorbedingungen (null-Werte explizit erlaubt)</li>
     *   <li>Post: Rueckgabe entspricht Comparable-Konvention</li>
     *   <li>Post: Vergleich ist case-insensitive fuer nicht-null Werte</li>
     * </ul>
     *
     * @param ersterWert erster String im Vergleich (kann null sein)
     * @param zweiterWert zweiter String im Vergleich (kann null sein)
     * @return Vergleichsergebnis: negativ (erster < zweiter), 0 (gleich), positiv (erster > zweiter)
     */
    private static int vergleicheStringNullSicher(String ersterWert, String zweiterWert) {
        // Nulls werden so behandelt, dass null > Nicht-null ist (damit unbekannte Werte ans Ende rutschen).
        if (ersterWert == null && zweiterWert == null) {
            return 0;
        }
        if (ersterWert == null) {
            return 1;
        }
        if (zweiterWert == null) {
            return -1;
        }
        return ersterWert.compareToIgnoreCase(zweiterWert);
    }

    /**
     * Vergleicht zwei Integer-Werte null-sicher in aufsteigender Reihenfolge.
     * <p>
     * Vergleichslogik:
     * <ul>
     *   <li>Beide null: Gleichwertig (return 0)</li>
     *   <li>Nur ersterWert null: ans Ende sortieren (return 1)</li>
     *   <li>Nur zweiterWert null: ans Ende sortieren (return -1)</li>
     *   <li>Beide nicht-null: Numerischer aufsteigender Vergleich</li>
     * </ul>
     * </p>
     *
     * <p><b>Verwendung:</b> Primaer fuer Baujahr-Vergleiche, wobei Anlagen ohne
     * Baujahrangabe ans Ende sortiert werden.</p>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: Keine Vorbedingungen (null-Werte explizit erlaubt)</li>
     *   <li>Post: Rueckgabe entspricht Comparable-Konvention</li>
     *   <li>Post: Aufsteigende Sortierung (kleinere Werte zuerst)</li>
     * </ul>
     *
     * @param ersterWert erster Integer im Vergleich (kann null sein)
     * @param zweiterWert zweiter Integer im Vergleich (kann null sein)
     * @return Vergleichsergebnis: -1 (erster < zweiter), 0 (gleich), 1 (erster > zweiter)
     */
    private static int vergleicheIntegerNullSicher(Integer ersterWert, Integer zweiterWert) {
        // Nulls werden ans Ende sortiert; ansonsten normaler aufsteigender Vergleich.
        if (ersterWert == null && zweiterWert == null) {
            return 0;
        }
        if (ersterWert == null) {
            return 1;
        }
        if (zweiterWert == null) {
            return -1;
        }
        if (ersterWert < zweiterWert) {
            return -1;
        }
        if (ersterWert > zweiterWert) {
            return 1;
        }
        return 0;
    }

    /**
     * Vergleicht zwei Float-Werte null-sicher in ABSTEIGENDER Reihenfolge.
     * <p>
     * Vergleichslogik:
     * <ul>
     *   <li>Beide null: Gleichwertig (return 0)</li>
     *   <li>Nur ersterWert null: ans Ende sortieren (return 1)</li>
     *   <li>Nur zweiterWert null: ans Ende sortieren (return -1)</li>
     *   <li>Beide nicht-null: Numerischer ABSTEIGENDER Vergleich (groessere Werte zuerst)</li>
     * </ul>
     * </p>
     *
     * <p><b>Besonderheit:</b> Im Gegensatz zu den anderen Vergleichsmethoden
     * ist diese ABSTEIGEND sortiert. Dies ist wichtig fuer Leistungsvergleiche,
     * bei denen die staerksten Anlagen an erster Stelle stehen sollen.</p>
     *
     * <p><b>Verwendung:</b> Primaer fuer Gesamtleistung-Vergleiche (MW),
     * um leistungsstarke Anlagen zu priorisieren.</p>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: Keine Vorbedingungen (null-Werte explizit erlaubt)</li>
     *   <li>Post: Rueckgabe entspricht Comparable-Konvention (aber invertiert)</li>
     *   <li>Post: ABSTEIGENDE Sortierung (groessere Werte zuerst)</li>
     * </ul>
     *
     * @param ersterWert erster Float im Vergleich (kann null sein)
     * @param zweiterWert zweiter Float im Vergleich (kann null sein)
     * @return Vergleichsergebnis: -1 (erster > zweiter!), 0 (gleich), 1 (erster < zweiter!)
     */
    private static int vergleicheFloatNullSicherAbsteigend(Float ersterWert, Float zweiterWert) {
        // Für absteigende Sortierung: größere Werte kommen zuerst.
        // Nulls werden ans Ende sortiert.
        if (ersterWert == null && zweiterWert == null) {
            return 0;
        }
        if (ersterWert == null) {
            return 1;
        }
        if (zweiterWert == null) {
            return -1;
        }
        if (ersterWert > zweiterWert) {
            return -1;
        }
        if (ersterWert < zweiterWert) {
            return 1;
        }
        return 0;
    }

    /**
     * Vergleicht zwei primitive int-Werte in aufsteigender Reihenfolge.
     * <p>
     * Einfacher numerischer Vergleich ohne Null-Behandlung, da primitive
     * int-Werte nie null sein koennen.
     * </p>
     *
     * <p><b>Verwendung:</b> Primaer fuer ObjektId-Vergleiche als tertiaeres
     * Sortierkriterium fuer eindeutige Ordnung.</p>
     *
     * <p><b>Kontrakt:</b></p>
     * <ul>
     *   <li>Pre: Keine Vorbedingungen (primitive Werte, nie null)</li>
     *   <li>Post: Rueckgabe -1 wenn ersterWert &lt; zweiterWert</li>
     *   <li>Post: Rueckgabe 0 wenn ersterWert == zweiterWert</li>
     *   <li>Post: Rueckgabe 1 wenn ersterWert &gt; zweiterWert</li>
     * </ul>
     *
     * @param ersterWert erster int-Wert im Vergleich
     * @param zweiterWert zweiter int-Wert im Vergleich
     * @return Vergleichsergebnis: -1 (kleiner), 0 (gleich), 1 (groesser)
     */
    private static int vergleicheInt(int ersterWert, int zweiterWert) {
        if (ersterWert < zweiterWert) {
            return -1;
        }
        if (ersterWert > zweiterWert) {
            return 1;
        }
        return 0;
    }
}
