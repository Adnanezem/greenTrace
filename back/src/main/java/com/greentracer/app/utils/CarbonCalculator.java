package com.greentracer.app.utils;


/**
 * Implémente les méthodes pour calculer l'empreinte carbonne.
 * Sources des chiffres utilisées :
 * https://ekwateur.fr/blog/enjeux-environnementaux/empreinte-carbone-bus/
 * https://calculis.net/co2
 * https://avenirclimatique.org/calculer-empreinte-carbone-trajet/
 * https://climate.selectra.com/fr/empreinte-carbone/avion
 * https://ouestlecarbone.com/la-nourriture/
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
    public static float computeVeloEmissions(String vehicule, int distance) {
        float emissions = 0;
        if (!vehicule.isBlank()) {
            if (vehicule.equals("electric")) {
                emissions += 11 * distance; 
            } else {
                emissions += 1 * distance;
            }
        }
        return emissions;
    }
    public static float computeAvionEmissions(String vehicule, int distance) {
        float emissions = 0;
        if (!vehicule.isBlank()) {
            if (vehicule.equals("short haul")) {
                emissions += 387 * distance; // max 1000km
            } else if (vehicule.equals("medium haul")) {
                emissions += 408  * distance; // entre 1000 et 3500 km
            } else if (vehicule.equals("long haul")) {
                emissions += 673 * distance; // sup a 3500 km
            }
        }

        return emissions;
    }

    public static float computeRepasResto(String meal, String restaurant) {
        float resto = 0;
        if (!meal.isBlank()) {
            switch (meal) {
                case "breakfast":
                    switch (restaurant) {
                        case "fast food":
                            resto += 4.25;
                            break;
                        case "traditional":
                            resto += 1.5;
                            break;
                        case "gourmet":
                            resto += 1;
                            break;
                        case "vegan":
                            resto += 0.55;
                            break;
                        case "vegetarian":
                            resto += 0.35;
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            
        }
        return resto;
    }    


    
}
