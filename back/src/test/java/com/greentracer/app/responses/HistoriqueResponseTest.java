package com.greentracer.app.responses;

import com.greentracer.app.models.Historique;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoriqueResponseTest {

    @Test
    public void testHistoriqueResponseConstructorAndGetters() {
        Historique historique = new Historique(1, "testId", 1.0f);
        HistoriqueResponse historiqueResponse = new HistoriqueResponse("Test Message", 200, historique);

        assertEquals("Test Message", historiqueResponse.getMessage());
        assertEquals(200, historiqueResponse.getStatus());
        assertEquals(historique, historiqueResponse.getHistorique());
    }

    @Test
    public void testHistoriqueResponseSetters() {
        Historique historique = new Historique(1, "testId", 1.0f);
        HistoriqueResponse historiqueResponse = new HistoriqueResponse("Test Message", 200, historique);
        Historique newHistorique = new Historique(2, "newTestId", 2.0f);

        historiqueResponse.setMessage("New Test Message");
        historiqueResponse.setStatus(404);
        historiqueResponse.setHistorique(newHistorique);

        assertEquals("New Test Message", historiqueResponse.getMessage());
        assertEquals(404, historiqueResponse.getStatus());
        assertEquals(newHistorique, historiqueResponse.getHistorique());
    }
}