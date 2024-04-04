package com.greentracer.app.controllers;

import com.greentracer.app.internal.DefaultUser;
import com.greentracer.app.responses.Error;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

public class UserAPITest {

    @Mock
    private DefaultUser defaultUser;

    private UserAPI userAPI;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userAPI = new UserAPI(defaultUser);
    }

    @Test
    @DisplayName("Should return no content when login is successful")
    public void loginSuccessful() {
        Mockito.when(defaultUser.defaultLogin(anyString())).thenReturn(Collections.singletonMap(true, new Error("Success", 0)));

        ResponseEntity<?> response = userAPI.login("test");

        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    @DisplayName("Should return bad request when login is unsuccessful")
    public void loginUnsuccessful() {
        Error errorResponse = new Error("Error message", 1);
        Mockito.when(defaultUser.defaultLogin(anyString())).thenReturn(Collections.singletonMap(false, errorResponse));

        ResponseEntity<?> response = userAPI.login("test");

        assertEquals(ResponseEntity.badRequest().body(errorResponse), response);
    }

    @Test
    @DisplayName("Should return no content when registration is successful")
    public void registerSuccessful() {
        Mockito.when(defaultUser.defaultRegister(anyString())).thenReturn(Collections.singletonMap(true, new Error("Success", 0)));

        ResponseEntity<?> response = userAPI.register("test");

        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    @DisplayName("Should return bad request when registration is unsuccessful")
    public void registerUnsuccessful() {
        Error errorResponse = new Error("Error message", 1);
        Mockito.when(defaultUser.defaultRegister(anyString())).thenReturn(Collections.singletonMap(false, errorResponse));

        ResponseEntity<?> response = userAPI.register("test");

        assertEquals(ResponseEntity.badRequest().body(errorResponse), response);
    }
}