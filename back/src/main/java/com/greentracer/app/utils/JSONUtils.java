package com.greentracer.app.utils;

import com.fasterxml.jackson.databind.JsonNode;

public class JSONUtils {
    public static String getStringField(JsonNode json, String fieldName) {
        JsonNode node = json.get(fieldName);
        return (node != null && !node.isNull()) ? node.asText() : "";
    }
}
