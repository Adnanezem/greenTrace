package com.greentracer.app.utils;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Classe utilitaire pour lire un JsonNode sans provoquer de NullPointerException.
 */
public final class JSONUtils {

    /**
     * Constructeur privé (classe utilitaire).
     */
    private JSONUtils() {

    } 

    /**
     * Lit le champ donné dans l'objet JsonNode en paramètre.
     * @param json
     * @param fieldName
     * @return la valeur du champ ou string vide si le champ n'existe pas.
     */
    public static String getStringField(JsonNode json, String fieldName) {
        JsonNode node = json.get(fieldName);
        return (node != null && !node.isNull()) ? node.asText() : "";
    }
}
