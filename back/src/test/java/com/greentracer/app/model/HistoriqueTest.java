package com.greentracer.app.model;

import com.greentracer.app.models.Historique;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoriqueTest {

    @Test
    public void testHistoriqueConstructorAndGetters() {
        Historique historique = new Historique(1, "testId", 1.0f);

        assertEquals(1, historique.getid());
        assertEquals("testId", historique.getidp());
        assertEquals(1.0f, historique.gethistorique());
    }

    @Test
    public void testHistoriqueSetters() {
        Historique historique = new Historique();
        historique.setid(2);
        historique.setidp("newTestId");
        historique.sethistorique(2.0f);

        assertEquals(2, historique.getid());
        assertEquals("newTestId", historique.getidp());
        assertEquals(2.0f, historique.gethistorique());
    }

    @Test
    public void testToString() {
        Historique historique = new Historique(1, "testId", 1.0f);
        String expectedString = "User [id=1, IDpatient=testId, historique=1.0]";

        assertEquals(expectedString, historique.toString());
    }
}