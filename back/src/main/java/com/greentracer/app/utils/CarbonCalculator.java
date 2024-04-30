package com.greentracer.app.utils;

/**
 * Implémente les méthodes pour calculer l'empreinte carbonne.
 * Sources des chiffres utilisées :
 * https://ekwateur.fr/blog/enjeux-environnementaux/empreinte-carbone-bus/
 * https://calculis.net/co2
 * https://avenirclimatique.org/calculer-empreinte-carbone-trajet/
 * https://climate.selectra.com/fr/empreinte-carbone/avion
 * https://ouestlecarbone.com/la-nourriture/
 * https://aucoeurduchr.fr/article/restauration/le-poids-des-emissions-carbone-dun-restaurant-a-la-loupe/
 * https://agribalyse.ademe.fr/app/aliments/25414
 * https://blog.helios.do/greenwashing-mcdo/
 * https://impactco2.fr/
 *
 */
public final class CarbonCalculator {

    /**
     * Constructeur privé (checkstyle).
     */
    private CarbonCalculator() {

    }

    /**
     * Calcule les émissions d'un trajet en voiture.
     * 
     * @param fuel
     * @param distance
     * @return un résultat en float.
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
     * Calcule les émissions d'un trajet en bus.
     * 
     * @param fuel
     * @param distance
     * @return un résultat en float.
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

    /**
     * Calcule les émissions d'un trajet en vélo.
     * 
     * @param vehicule
     * @param distance
     * @return un résultat en float.
     */
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

    /**
     * Calcule les émissions d'un trajet en avion.
     * 
     * @param vehicule
     * @param distance
     * @return un résultat en float.
     */
    public static float computeAvionEmissions(String vehicule, int distance) {
        float emissions = 0;
        if (!vehicule.isBlank()) {
            if (vehicule.equals("short haul")) {
                emissions += 387 * distance; // max 1000km
            } else if (vehicule.equals("medium haul")) {
                emissions += 408 * distance; // entre 1000 et 3500 km
            } else if (vehicule.equals("long haul")) {
                emissions += 673 * distance; // sup a 3500 km
            }
        }

        return emissions;
    }

    /**
     * Calcule l'empreinte carbone d'un repas en restaurant.
     * 
     * @param restaurant
     * @return un résultat en float.
     */
    public static float computeRepasResto(String restaurant) {
        float resto = 0;
        switch (restaurant) {
            case "fast food":
                resto += 4610; // 4460 (big mac de chez McDonald) + 150 (grande frite toujours McDonald).
                break;
            case "traditional":
                resto += 4489;
                break;
            case "vegan":
                resto += 1170; // 390 * 3 (entrée plat dessert)
                break;
            case "vegetarian":
                resto += 1530; // 510 * 3
                break;
            default:
                break;
        }
        return resto;
    }

    /**
     * Calcule l'empreinte carbone d'un repas chez soit.
     * 
     * @param meal
     * @param restaurant
     * @return un résultat en float.
     */
    public static float computeRepasMaison(String meal, String foodType) {
        float repas = 0;

        switch (meal) {
            case "breakfast": // le déjeuner calculer est (très) complet.
                switch (foodType) {
                    case "traditional":
                        repas += 839; // moyenne pondérée à partir des éléments composants généralement un déjeuner depuis https://ouestlecarbone.com/la-nourriture/ 
                        break;
                    case "vegan":
                        repas += 649.5; // meme principe avec produit vegan
                        break;
                    case "vegetarian":
                        repas += 839; // meme principe avec produit vege
                        break;
                    default:
                        break;
                }
                break;
            case "lunch":
            case "dinner":
                switch (foodType) {
                    case "traditional":
                        repas += 2980; // moyenne des repas qui ne sont pas vegan ou vege (depuis impactco2.fr/repas).
                        break;
                    case "vegan":
                        repas += 390;
                        break;
                    case "vegetarian":
                        repas += 510;
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
        return repas;
    }

}
