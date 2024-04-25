package com.greentracer.app.model;

import com.greentracer.app.models.Journee;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JourneeTest {

    @Test
    public void testJourneeConstructorAndGetters() {
        Journee journee = new Journee(1, "testId", new java.sql.Date(System.currentTimeMillis()), 1.0f);

        assertEquals(1, journee.getid());
        assertEquals("testId", journee.getpatientId());
        assertEquals(1.0f, journee.getresultat());
    }

    @Test
    public void testJourneeSetters() {
        Journee journee = new Journee();
        journee.setid(2);
        journee.setpatientId("newTestId");
        journee.setdate(new java.sql.Date(System.currentTimeMillis()));
        journee.setresultat(2.0f);

        assertEquals(2, journee.getid());
        assertEquals("newTestId", journee.getpatientId());
        assertEquals(2.0f, journee.getresultat());
    }

    @Test
    public void testToString() {
        Journee journee = new Journee(1, "testId", new java.sql.Date(System.currentTimeMillis()), 1.0f);
        String expectedString = "User [id=1, ID_patient=testId, Date=" + journee.getdate() + ", Resultat=1.0]";

        assertEquals(expectedString, journee.toString());
    }
}