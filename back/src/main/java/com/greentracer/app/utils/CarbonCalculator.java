package com.greentracer.app.utils;


/**
 * Implémente les méthodes pour calculer l'empreinte carbonne.
 * Sources des chiffres utilisées :
 * https://ekwateur.fr/blog/enjeux-environnementaux/empreinte-carbone-bus/
 * https://calculis.net/co2
 * https://avenirclimatique.org/calculer-empreinte-carbone-trajet/
 * https://climate.selectra.com/fr/empreinte-carbone/avion
 *
 */
public final class CarbonCalculator {

    /**
     * Constructeur privé (checkstyle).
     */
    private CarbonCalculator() {

    }

    /**
     * 
     * @param fuel
     * @param distance
     * @return
     */
    public static float computeCarEmissions(String fuel, int distance) {
        float emissions = 0;
        if (!fuel.isBlank()) {
            if (fuel.equals("diesel")) {
                emissions += 160 * distance;
            } else if (fuel.equals("electric")) {
                emissions += 70 * distance;
            } else if (fuel.equals("gasoline")) {
                emissions += 180 * distance;
            }
        }
        return emissions;
    }

    /**
     * 
     * @param fuel
     * @param distance
     * @return
     */
    public static float computeBusEmissions(String fuel, int distance) {
        float emissions = 0;
        if (!fuel.isBlank()) {
            if (fuel.equals("diesel")) {
                emissions += 200 * distance; // 1100 pour tt le bus
            } else if (fuel.equals("electric")) {
                emissions += 80 * distance; // 200
            }
        }
        return emissions;
    }
    public static float computeVeloEmissions(String fuel, int distance) {
        float emissions = 0;
        if (!fuel.isBlank()) {
            if (fuel.equals("electric")) {
                emissions += 11 * distance; 
            } else {
                emissions += 1 * distance;
            }
        }
        return emissions;
    }
    public static float computeAvionEmissions(String fuel, int distance) {
        float emissions = 0;
        if (!fuel.isBlank()) {
            if (fuel.equals("short haul")) {
                emissions += 387 * distance; // max 1000km
            } else if (fuel.equals("medium haul")) {
                emissions += 408  * distance; // entre 1000 et 3500 km
            } else if (fuel.equals("long haul")) {
                emissions += 673 * distance; // sup a 3500 km
            }
        }
        
        return emissions;
    }
}
