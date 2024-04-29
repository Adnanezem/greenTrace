package com.greentracer.app.controllers;

import com.greentracer.app.internal.DefaultUser;
import com.greentracer.app.models.User;
import com.greentracer.app.responses.ErrorResponse;
import com.greentracer.app.responses.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * Tests pour l'API User.
 */
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
        Mockito.when(defaultUser.defaultLogin(anyString())).thenReturn(Collections.singletonMap(true, new ErrorResponse("Success", 0)));

        ResponseEntity<?> response = userAPI.login("test");

        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Should return bad request when login is unsuccessful")
    public void loginUnsuccessful() {
        ErrorResponse errorResponse = new ErrorResponse("ErrorResponse message", 1);
        Mockito.when(defaultUser.defaultLogin(anyString())).thenReturn(Collections.singletonMap(false, errorResponse));

        ResponseEntity<?> response = userAPI.login("test");

        assertEquals(ResponseEntity.badRequest().body(errorResponse), response);
    }


    @Test
    public void getUserTest() {
        Mockito.when(defaultUser.defaultGetUser(anyString())).thenReturn(Collections.singletonMap(true, new UserResponse("Success", 200, new User())));

        ResponseEntity<?> response = userAPI.getUser("testUser");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should return no content when registration is successful")
    public void registerSuccessful() {
        Mockito.when(defaultUser.defaultRegister(anyString())).thenReturn(Collections.singletonMap(true, new ErrorResponse("Success", 0)));

        ResponseEntity<?> response = userAPI.register("test");

        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    @DisplayName("Should return bad request when registration is unsuccessful")
    public void registerUnsuccessful() {
        ErrorResponse errorResponse = new ErrorResponse("ErrorResponse message", 1);
        Mockito.when(defaultUser.defaultRegister(anyString())).thenReturn(Collections.singletonMap(false, errorResponse));

        ResponseEntity<?> response = userAPI.register("test");

        assertEquals(ResponseEntity.badRequest().body(errorResponse), response);
    }


    @Test
    public void updateUserTest() {
        Mockito.when(defaultUser.defaultUpdateUser(anyString(), anyString())).thenReturn(Collections.singletonMap(true, new UserResponse("Success", 200, new User())));

        ResponseEntity<?> response = userAPI.updateUser("testUser", "{\"name\":\"newName\"}");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getUserHistoryTest() {
        ResponseEntity<?> response = userAPI.getUserHistory("testUser");

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals("/carbon/testUser/history", response.getHeaders().getLocation().getPath());
    }
}