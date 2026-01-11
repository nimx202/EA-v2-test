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
    public static float berechneDistanzKm(float breitengrad1, float laengengrad1,
                                          float breitengrad2, float laengengrad2) {
        // Konvertiere Grad zu Radianten
        float lat1Rad = gradZuRadianten(breitengrad1);
        float lon1Rad = gradZuRadianten(laengengrad1);
        float lat2Rad = gradZuRadianten(breitengrad2);
        float lon2Rad = gradZuRadianten(laengengrad2);

        // Berechne Differenzen
        float deltaLat = lat2Rad - lat1Rad;
        float deltaLon = lon2Rad - lon1Rad;

        // Haversine-Formel
        float a = berechneSinusQuadrat(deltaLat / 2.0f) 
                + (float) Math.cos(lat1Rad) * (float) Math.cos(lat2Rad) * berechneSinusQuadrat(deltaLon / 2.0f);
        
        float c = 2.0f * (float) Math.atan2(Math.sqrt(a), Math.sqrt(1.0f - a));

        // Distanz = Radius * Winkel
        return Konstanten.ERDRADIUS_KM * c;
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
    private static float gradZuRadianten(float grad) {
        return grad * (float) Math.PI / 180.0f;
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
    private static float berechneSinusQuadrat(float winkel) {
        float sinus = (float) Math.sin(winkel);
        return sinus * sinus;
    }
}
