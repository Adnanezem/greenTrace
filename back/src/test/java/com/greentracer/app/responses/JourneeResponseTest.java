package com.greentracer.app.responses;

import com.greentracer.app.models.Journee;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JourneeResponseTest {

    @Test
    void testJourneeResponseConstructorAndGetters() {
        Journee journee = new Journee(1, "testId", new java.sql.Date(System.currentTimeMillis()), 1.0f);
        JourneeResponse journeeResponse = new JourneeResponse("Test Message", 200, journee);

        assertEquals("Test Message", journeeResponse.getMessage());
        assertEquals(200, journeeResponse.getStatus());
        assertEquals(journee, journeeResponse.getJournee());
    }

    @Test
    void testJourneeResponseSetters() {
        Journee journee = new Journee(1, "testId", new java.sql.Date(System.currentTimeMillis()), 1.0f);
        JourneeResponse journeeResponse = new JourneeResponse("Test Message", 200, journee);
        Journee newJournee = new Journee(2, "newTestId", new java.sql.Date(System.currentTimeMillis()), 2.0f);

        journeeResponse.setMessage("New Test Message");
        journeeResponse.setStatus(404);
        journeeResponse.setJournee(newJournee);

        assertEquals("New Test Message", journeeResponse.getMessage());
        assertEquals(404, journeeResponse.getStatus());
        assertEquals(newJournee, journeeResponse.getJournee());
    }
}