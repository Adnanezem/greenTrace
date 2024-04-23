package com.greentracer.app.internal;

import org.mockito.Mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.greentracer.app.dao.HistoriqueDao;
import com.greentracer.app.dao.JourneeDao;
import com.greentracer.app.models.Historique;
import com.greentracer.app.models.Journee;
import com.greentracer.app.responses.ErrorResponse;
import com.greentracer.app.responses.GreenTracerResponse;
import com.greentracer.app.responses.HistoriqueResponse;
import com.greentracer.app.responses.JourneeResponse;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Tests pour DefaultCarbon.
 */
class DefaultCarbonTest {

    private HistoriqueDao histDaoMock;

    private JourneeDao journeeDaoMock;

    private DefaultCarbon defaultCarbon;

    @BeforeEach
    void setUp() {
        histDaoMock = Mockito.mock(HistoriqueDao.class);
        journeeDaoMock = Mockito.mock(JourneeDao.class);
        defaultCarbon = new DefaultCarbon(histDaoMock, journeeDaoMock);
    }

    @Test
    void defaultCompute_validInput_returnsTrue() throws Exception {
        // Arrange
        String jsonBody = "{\"login\":\"testUser\"}";
        Journee journee = new Journee(0, "testUser", new Date(System.currentTimeMillis()), 0);
        when(journeeDaoMock.create(Mockito.any(Journee.class))).thenReturn(true);

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultCarbon.defaultCompute(jsonBody);

        // Assert
        assertTrue(result.containsKey(true));
        assertTrue(result.get(true) instanceof JourneeResponse);
    }

    @Test
    void defaultCompute_invalidJson_returnsFalse() {
        // Arrange
        String invalidJsonBody = "{invalidJson}";

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultCarbon.defaultCompute(invalidJsonBody);

        // Assert
        assertTrue(result.containsKey(false));
        assertTrue(result.get(false) instanceof ErrorResponse);
    }

    @Test
    void defaultGetHistory_existingUser_returnsTrue() {
        // Arrange
        Historique historique = new Historique();
        when(histDaoMock.getById("user1")).thenReturn(historique);

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultCarbon.defaultGetHistory("user1");

        // Assert
        assertTrue(result.containsKey(true));
        assertTrue(result.get(true) instanceof HistoriqueResponse);
    }

    @Test
    void defaultGetHistory_nonExistingUser_returnsFalse() {
        // Arrange
        when(histDaoMock.getById("user1")).thenThrow(new IllegalArgumentException());

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultCarbon.defaultGetHistory("user1");

        // Assert
        assertTrue(result.containsKey(false));
        assertNull(result.get(false));
    }

    @Test
    void defaultGetDetailedHistory_validDate_returnsTrue() throws Exception {
        // Arrange
        Journee journee = new Journee();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date utilDate = dateFormat.parse("22-04-2024");
        Date sqlDate = new Date(utilDate.getTime());
        when(journeeDaoMock.getByDate("user1", sqlDate)).thenReturn(journee);

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultCarbon.defaultGetDetailledHistory("user1", "22-04-2024");

        // Assert
        assertTrue(result.containsKey(true));
        assertTrue(result.get(true) instanceof JourneeResponse);
    }

    @Test
    void defaultGetDetailedHistory_invalidDate_returnsFalse() {
        // Arrange
        when(journeeDaoMock.getByDate(Mockito.any(), Mockito.any())).thenThrow(new IllegalArgumentException());

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultCarbon.defaultGetDetailledHistory("user1", "invalid-date");

        // Assert
        assertTrue(result.containsKey(false));
        assertTrue(result.get(false) instanceof ErrorResponse);
    }
}
