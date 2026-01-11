package util;

/**
 * Berechnet Distanzen zwischen geografischen Koordinaten.
 * 
 * Design-Prinzipien:
 * - Single Responsibility: Nur Distanzberechnungen
 * - KISS: Einfache mathematische Formeln ohne komplexe Optimierungen
 * - Modularization: Getrennte Utility-Klasse für Geo-Berechnungen
 * 
 * Pre: Koordinaten müssen valide sein (Breitengrad -90 bis 90, Längengrad -180 bis 180)
 * Post: Gibt Distanz in Kilometern zurück
 */
public final class DistanzBerechner {

    /**
     * Privater Konstruktor verhindert Instanziierung der Utility-Klasse.
     */
    private DistanzBerechner() {
        // Utility-Klasse, keine Instanzen
    }

    /**
     * Berechnet die Distanz zwischen zwei Punkten auf der Erde mittels Haversine-Formel.
     * Die Haversine-Formel berechnet die kürzeste Distanz über die Erdoberfläche
     * (Großkreis-Distanz) unter Berücksichtigung der Erdkrümmung.
     * 
     * Pre: breitengrad1, breitengrad2 im Bereich [-90, 90]
     *      laengengrad1, laengengrad2 im Bereich [-180, 180]
     * Post: Rückgabe ist die Distanz in Kilometern (>= 0)
     * 
     * @param breitengrad1 Breitengrad des ersten Punkts in Dezimalgrad
     * @param laengengrad1 Längengrad des ersten Punkts in Dezimalgrad
     * @param breitengrad2 Breitengrad des zweiten Punkts in Dezimalgrad
     * @param laengengrad2 Längengrad des zweiten Punkts in Dezimalgrad
     * @return Distanz zwischen den beiden Punkten in Kilometern
     */
    public static double berechneDistanzKm(double breitengrad1, double laengengrad1,
                                           double breitengrad2, double laengengrad2) {
        // Konvertiere Grad zu Radianten
        double lat1Rad = gradZuRadianten(breitengrad1);
        double lon1Rad = gradZuRadianten(laengengrad1);
        double lat2Rad = gradZuRadianten(breitengrad2);
        double lon2Rad = gradZuRadianten(laengengrad2);

        // Berechne Differenzen
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Haversine-Formel
        double a = berechneSinusQuadrat(deltaLat / 2.0) 
                 + Math.cos(lat1Rad) * Math.cos(lat2Rad) * berechneSinusQuadrat(deltaLon / 2.0);
        
        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));

        // Distanz = Radius * Winkel
        double distanzKm = Konstanten.ERDRADIUS_KM * c;

        return distanzKm;
    }

    /**
     * Konvertiert Grad zu Radianten.
     * 
     * Pre: -
     * Post: Rückgabe ist der Winkel in Radianten
     * 
     * @param grad Winkel in Grad
     * @return Winkel in Radianten
     */
    private static double gradZuRadianten(double grad) {
        return grad * Math.PI / 180.0;
    }

    /**
     * Berechnet das Quadrat des Sinus eines Winkels.
     * 
     * Pre: -
     * Post: Rückgabe ist sin(winkel)^2
     * 
     * @param winkel Winkel in Radianten
     * @return Quadrat des Sinus
     */
    private static double berechneSinusQuadrat(double winkel) {
        double sinus = Math.sin(winkel);
        return sinus * sinus;
    }
}
