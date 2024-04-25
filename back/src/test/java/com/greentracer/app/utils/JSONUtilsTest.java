package com.greentracer.app.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSONUtilsTest {

    @Test
    public void testGetStringField() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree("{\"field\":\"value\"}");

        assertEquals("value", JSONUtils.getStringField(jsonNode, "field"));
        assertEquals("", JSONUtils.getStringField(jsonNode, "nonexistentField"));
    }
}