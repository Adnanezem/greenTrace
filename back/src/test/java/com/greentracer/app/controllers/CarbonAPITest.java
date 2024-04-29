package com.greentracer.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.greentracer.app.internal.DefaultCarbon;
import com.greentracer.app.responses.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests pour l'API Carbon.
 */
public class CarbonAPITest {

    @Mock
    private DefaultCarbon defaultCarbon;

    private CarbonAPI carbonAPI;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        carbonAPI = new CarbonAPI(defaultCarbon);
    }

    @Test
    @DisplayName("Should create resource when compute is successful")
    public void computeSuccessful() throws URISyntaxException {
        String requestBody = "{\"login\":\"testUser\", \"form\":[]}";
        GreenTracerResponse successResponse = new GreenTracerResponse("Success", 201);
        Map<Boolean, GreenTracerResponse> result = Collections.singletonMap(true, successResponse);

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        URI uri = new URI("/testUser/history/" + today);

        when(defaultCarbon.defaultCompute(requestBody)).thenReturn(result);

        ResponseEntity<?> response = carbonAPI.compute(requestBody);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(successResponse, response.getBody());
        assertEquals(uri, response.getHeaders().getLocation());
    }

    @Test
    @DisplayName("Should return bad request when compute fails")
    public void computeUnsuccessful() {
        String requestBody = "bad request";
        Map<Boolean, GreenTracerResponse> errorResponseMap = new HashMap<>();
        GreenTracerResponse errorResponse = new ErrorResponse("Error in compute", 400);
        errorResponseMap.put(false, errorResponse);
        when(defaultCarbon.defaultCompute(requestBody)).thenReturn(errorResponseMap);

        ResponseEntity<?> response = carbonAPI.compute(requestBody);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    public void computeURISyntaxExceptionTest() {
        String requestBody = "{\"login\":\"testUser\", \"form\":[]}";
        Mockito.when(defaultCarbon.defaultCompute(requestBody)).thenAnswer(new Answer<Map<Boolean, GreenTracerResponse>>() {
            @Override
            public Map<Boolean, GreenTracerResponse> answer(InvocationOnMock invocation) throws Throwable {
                throw new URISyntaxException("Invalid URI", "Invalid syntax");
            }
        });

        ResponseEntity<?> response = carbonAPI.compute(requestBody);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    @DisplayName("Should return history data when successful")
    public void getHistorySuccessful() {
        String id = "user1";
        HistoriqueResponse expectedResponse = new HistoriqueResponse(id, null, null);
        Map<Boolean, GreenTracerResponse> result = Collections.singletonMap(true, expectedResponse);

        when(defaultCarbon.defaultGetHistory(id)).thenReturn(result);

        ResponseEntity<?> response = carbonAPI.getHistory(id);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    @DisplayName("Should handle errors in getting history")
    public void getHistoryUnsuccessful() {
        String id = "user1";
        GreenTracerResponse errorResponse = new GreenTracerResponse("Error", 404);
        Map<Boolean, GreenTracerResponse> result = Collections.singletonMap(false, errorResponse);

        when(defaultCarbon.defaultGetHistory(id)).thenReturn(result);

        ResponseEntity<?> response = carbonAPI.getHistory(id);

        assertEquals(404, response.getStatusCode().value());
        assertEquals(errorResponse, response.getBody());
    }

    @Test
    @DisplayName("Should return detailed history when successful")
    public void getDetailledHistoryNotNullTest() {
        String id = "user1";
        String date = "2024-04-26";
        JourneesResponse expectedResponse = new JourneesResponse(date, null, null);
        Map<Boolean, GreenTracerResponse> result = Collections.singletonMap(true, expectedResponse);

        Mockito.when(defaultCarbon.defaultGetDetailledHistory(id, date)).thenReturn(result);

        ResponseEntity<?> response = carbonAPI.getDetailledHistory(id, date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    @DisplayName("Should handle errors in detailed history retrieval")
    public void getDetailledHistoryUnsuccessful() {
        String id = "user1";
        String date = "2024-04-26";
        GreenTracerResponse errorResponse = new GreenTracerResponse("Error", 404);
        Map<Boolean, GreenTracerResponse> result = Collections.singletonMap(false, errorResponse);

        when(defaultCarbon.defaultGetDetailledHistory(id, date)).thenReturn(result);

        ResponseEntity<?> response = carbonAPI.getDetailledHistory(id, date);

        assertEquals(404, response.getStatusCode().value());
        assertEquals(errorResponse, response.getBody());
    }

    @Test
    public void computeJsonMappingExceptionTest() {
        String requestBody = "{\"login\":\"testUser\", \"form\":[]}";
        Mockito.when(defaultCarbon.defaultCompute(requestBody)).thenAnswer(new Answer<Map<Boolean, GreenTracerResponse>>() {
            @Override
            public Map<Boolean, GreenTracerResponse> answer(InvocationOnMock invocation) throws Throwable {
                throw new JsonMappingException(null, "Error in mapping");
            }
        });

        ResponseEntity<?> response = carbonAPI.compute(requestBody);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Malformed JSON.", response.getBody());
    }

    @Test
    public void computeJsonProcessingExceptionTest() {
        String requestBody = "{\"login\":\"testUser\", \"form\":[]}";
        Mockito.when(defaultCarbon.defaultCompute(requestBody)).thenAnswer(new Answer<Map<Boolean, GreenTracerResponse>>() {
            @Override
            public Map<Boolean, GreenTracerResponse> answer(InvocationOnMock invocation) throws Throwable {
                throw new JsonProcessingException("Error in processing") {};
            }
        });

        ResponseEntity<?> response = carbonAPI.compute(requestBody);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("JSON Process failed in CarbonAPI.compute.", response.getBody());
    }

    @Test
    @DisplayName("Should return not found when getHistory returns null")
    public void getHistoryNull() {
        String id = "user1";
        Map<Boolean, GreenTracerResponse> result = Collections.singletonMap(false, null);

        when(defaultCarbon.defaultGetHistory(id)).thenReturn(result);

        ResponseEntity<?> response = carbonAPI.getHistory(id);

        assertEquals(404, response.getStatusCode().value());
    }


    @Test
    public void getHistoryNotFound() {
        String id = "user1";
        Map<Boolean, GreenTracerResponse> result = Collections.singletonMap(false, null);

        Mockito.when(defaultCarbon.defaultGetHistory(id)).thenReturn(result);

        ResponseEntity<?> response = carbonAPI.getHistory(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

