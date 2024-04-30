package com.greentracer.app.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.greentracer.app.dao.UserDao;
import com.greentracer.app.models.User;
import com.greentracer.app.responses.ErrorResponse;
import com.greentracer.app.responses.GreenTracerResponse;
import com.greentracer.app.responses.UserResponse;
import com.greentracer.app.utils.JwtTokenUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

class DefaultUserTest {

    private UserDao userDaoMock;
    private JwtTokenUtil jwtMock;
    private DefaultUser defaultUser;

    @BeforeEach
    public void setup() {
        userDaoMock = Mockito.mock(UserDao.class);
        jwtMock = Mockito.mock(JwtTokenUtil.class);
        defaultUser = new DefaultUser(userDaoMock, jwtMock);
    }

    @Test
    void defaultLogin_validCredentials_returnsTrue() {
        // Arrange
        String login = "testUser";
        String password = "password";
        String token = "token";
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        when(userDaoMock.getById(login)).thenReturn(user);
        when(jwtMock.generateToken(login)).thenReturn(token);

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser
                .defaultLogin("{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}");

        // Assert
        assertTrue(result.containsKey(true));
        assertEquals(token, ((GreenTracerResponse) result.get(true)).getMessage());
    }

    @Test
    void defaultLogin_invalidCredentials_returnsFalseWithErrorResponse() {
        // Arrange
        String login = "testUser";
        String password = "wrongPassword";
        User user = new User();
        user.setLogin(login);
        user.setPassword("password");
        when(userDaoMock.getById(login)).thenReturn(user);

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser
                .defaultLogin("{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}");

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Mot de passe erronée.", ((ErrorResponse) result.get(false)).getMessage());
    }

    @Test
    void defaultLogin_emptyRequestBody_returnsFalseWithErrorResponse() {
        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultLogin("");

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Corps de requête vide.", ((ErrorResponse) result.get(false)).getMessage());
    }

    @Test
    void defaultLogin_emptyLoginOrPassword_returnsFalseWithErrorResponse() {
        // Arrange
        String login = "";
        String password = "password"; // Test empty login
        String body = "{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}";

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultLogin(body);

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Le champ login ou password est vide.", ((ErrorResponse) result.get(false)).getMessage());

        // Test empty password
        login = "testUser";
        password = "";
        body = "{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}";
        result = defaultUser.defaultLogin(body);

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Le champ login ou password est vide.", ((ErrorResponse) result.get(false)).getMessage());
    }

    @Test
    void defaultLogin_userNotFound_returnsFalseWithErrorResponse() {
        // Arrange
        when(userDaoMock.getById(anyString())).thenThrow(new IllegalArgumentException());

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultLogin("{\"login\":\"testUser\",\"password\":\"password\"}");

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Cet utilisateur n'existe pas ", ((ErrorResponse) result.get(false)).getMessage());
    }

    @Test
    void defaultRegister_validData_returnsTrue() {
        // Arrange
        String login = "testUser";
        String password = "password";
        String mail = "test@mail.com";
        String fname = "Test";
        String lname = "User";
        when(userDaoMock.create(Mockito.any(User.class))).thenReturn(true);

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser
                .defaultRegister("{\"login\":\"" + login + "\",\"password\":\"" + password + "\",\"mail\":\"" + mail
                        + "\",\"fname\":\"" + fname + "\",\"lname\":\"" + lname + "\"}");

        // Assert
        assertTrue(result.containsKey(true));
        assertNull(result.get(true));
    }

    @Test
    void defaultRegister_invalidData_returnsFalseWithErrorResponse() {
        // Arrange
        String login = "testUser";
        String password = "password";
        String mail = "test@mail.com";
        String fname = "Test";
        String lname = "User";
        when(userDaoMock.create(Mockito.any(User.class))).thenReturn(false);

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser
                .defaultRegister("{\"login\":\"" + login + "\",\"password\":\"" + password + "\",\"mail\":\"" + mail
                        + "\",\"fname\":\"" + fname + "\",\"lname\":\"" + lname + "\"}");

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Problème lors de la création d'un user.", ((ErrorResponse) result.get(false)).getMessage());
    }

    @Test
    void defaultRegister_emptyRequestBody_returnsFalseWithErrorResponse() {
        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultRegister("");

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Corps de requête vide.", ((ErrorResponse) result.get(false)).getMessage());
    }

    @Test
    void defaultRegister_emptyLogin_returnsFalseWithErrorResponse() {
        // Arrange
        String body = "{\"login\":\"\",\"password\":\"password\",\"mail\":\"email@example.com\",\"fname\":\"John\",\"lname\":\"Doe\"}";

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultRegister(body);

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Il manque des champs à la requête." +
                        "La requête comporte les champs login, password, mail, fname, lname." +
                        " Tous les champs sont des strings.",
                ((ErrorResponse) result.get(false)).getMessage());
    }


    @Test
    void defaultRegister_duplicateUser_returnsFalseWithErrorResponse() {
        // Arrange
        String login = "testUser";
        when(userDaoMock.create(Mockito.any(User.class))).thenReturn(false); // user duplicated

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultRegister("{\"login\":\"" + login + "\", \"password\":\"password\", \"mail\":\"email@example.com\", \"fname\":\"John\", \"lname\":\"Doe\"}");

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Problème lors de la création d'un user.", ((ErrorResponse) result.get(false)).getMessage());
    }


    @Test
    void defaultRegister_malformedJson_returnsFalseWithErrorResponse() {
        // Arrange
        String malformedJson = "{login:\"testUser\",password:\"password\",mail:\"email@example.com\",fname:\"John\",lname:\"Doe\"";  // missing closing brace

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultRegister(malformedJson);

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Format JSON non respecté.", ((ErrorResponse) result.get(false)).getMessage());
    }
    @Test
    void defaultRegister_existingUser_returnsFalseWithErrorResponse() {
        // Arrange
        String login = "existingUser";
        String password = "password";
        String mail = "existing@mail.com";
        String fname = "Existing";
        String lname = "User";
        when(userDaoMock.create(Mockito.any(User.class))).thenReturn(false);

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser
                .defaultRegister("{\"login\":\"" + login + "\",\"password\":\"" + password + "\",\"mail\":\"" + mail
                        + "\",\"fname\":\"" + fname + "\",\"lname\":\"" + lname + "\"}");

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Problème lors de la création d'un user.", ((ErrorResponse) result.get(false)).getMessage());
    }


    @Test
    void defaultGetUser_validUser_returnsTrueWithUserResponse() {
        // Arrange
        String login = "testUser";
        User user = new User();
        user.setLogin(login);
        when(userDaoMock.getById(login)).thenReturn(user);

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultGetUser(login);

        // Assert
        assertTrue(result.containsKey(true));
        assertTrue(result.get(true) instanceof UserResponse);
    }

    @Test
    void defaultGetUser_invalidUser_returnsFalseWithErrorResponse() {
        // Arrange
        String login = "invalidUser";
        when(userDaoMock.getById(login)).thenThrow(new IllegalArgumentException());

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultGetUser(login);

        // Assert
        assertTrue(result.containsKey(false));
        assertTrue(result.get(false) instanceof ErrorResponse);
    }

    @Test
    void defaultUpdateUser_validData_returnsTrueWithUserResponse() {
        // Arrange
        String login = "testUser";
        String password = "newPassword";
        String mail = "new@mail.com";
        String fname = "New";
        String lname = "User";
        User user = new User(login, password, lname, fname);
        when(userDaoMock.update(Mockito.any(User.class))).thenReturn(true);

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser
                .defaultUpdateUser(login, "{\"password\":\"" + password + "\",\"mail\":\"" + mail
                        + "\",\"fname\":\"" + fname + "\",\"lname\":\"" + lname + "\"}");

        // Assert
        assertTrue(result.containsKey(true));
        assertTrue(result.get(true) instanceof UserResponse);
    }

    @Test
    void defaultUpdateUser_invalidData_returnsFalseWithErrorResponse() {
        // Arrange
        String login = "testUser";
        String password = "newPassword";
        String mail = "new@mail.com";
        String fname = "New";
        String lname = "User";
        when(userDaoMock.update(Mockito.any(User.class))).thenReturn(false);

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser
                .defaultUpdateUser(login, "{\"password\":\"" + password + "\",\"mail\":\"" + mail
                        + "\",\"fname\":\"" + fname + "\",\"lname\":\"" + lname + "\"}");

        // Assert
        assertTrue(result.containsKey(false));
        assertTrue(result.get(false) instanceof ErrorResponse);
    }

    @Test
    void defaultUpdateUser_emptyLogin_returnsFalseWithErrorResponse() {
        // Arrange
        String login = "";

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultUpdateUser(login, "{\"password\":\"password\",\"mail\":\"email@example.com\",\"fname\":\"John\",\"lname\":\"Doe\"}");

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Le login est vide.", ((ErrorResponse) result.get(false)).getMessage());
    }

    @Test
    void defaultUpdateUser_malformedJson_returnsFalseWithErrorResponse() {
        // Arrange
        String login = "testUser";
        String malformedJson = "{password:\"newPassword\",mail:\"new@mail.com\",fname:\"New\",lname:\"User\"";  // missing closing brace

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultUpdateUser(login, malformedJson);

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Format JSON non respecté.", ((ErrorResponse) result.get(false)).getMessage());
    }

    @Test
    void defaultUpdateUser_emptyFieldsInRequestBody_returnsFalseWithErrorResponse() {
        // Arrange
        String login = "testUser";
        String body = "{\"password\":\"\",\"mail\":\"\",\"fname\":\"\",\"lname\":\"\"}";  // Empty fields

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultUpdateUser(login, body);

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Il manque des champs à la requête." +
                "La requête comporte les champs password, mail, fname, lname." +
                " Tous les champs sont des strings.", ((ErrorResponse) result.get(false)).getMessage());
    }

    @Test
    void defaultUpdateUser_nonExistingUser_returnsFalseWithErrorResponse() {
        // Arrange
        String login = "nonExistingUser";
        String password = "newPassword";
        String mail = "new@mail.com";
        String fname = "New";
        String lname = "User";
        when(userDaoMock.update(Mockito.any(User.class))).thenThrow(new IllegalArgumentException());

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser
                .defaultUpdateUser(login, "{\"password\":\"" + password + "\",\"mail\":\"" + mail
                        + "\",\"fname\":\"" + fname + "\",\"lname\":\"" + lname + "\"}");

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Une erreur est survenue lors de la mise à jour de l'utilisateur.", ((ErrorResponse) result.get(false)).getMessage());
    }


    @Test
    void defaultGetUser_nonExistingUser_returnsFalseWithErrorResponse() {
        // Arrange
        String login = "nonExistingUser";
        when(userDaoMock.getById(login)).thenThrow(new IllegalArgumentException());

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultGetUser(login);

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Une erreur est survenue lors de la récupération des données.", ((ErrorResponse) result.get(false)).getMessage());
    }


    @Test
    void defaultGetUser_emptyLogin_returnsFalseWithErrorResponse() {
        // Arrange
        String login = "";

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultGetUser(login);

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Le login est vide.", ((ErrorResponse) result.get(false)).getMessage());
    }


    @Test
    void defaultLogout_always_returnsTrueWithNullResponse() {
        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultLogout();

        // Assert
        assertTrue(result.containsKey(true));
        assertNull(result.get(true));
    }


    @Test
    void defaultLogin_malformedJson_returnsFalseWithErrorResponse() {
        // Arrange
        String malformedJson = "{login:\"testUser\",password:\"password\"";  // accolade

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultLogin(malformedJson);

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Format JSON non respecté.", ((ErrorResponse) result.get(false)).getMessage());
    }


    @Test
    void defaultLogin_nonExistingUser_returnsFalseWithErrorResponse() {
        // Arrange
        String login = "nonExistingUser";
        String password = "password";
        when(userDaoMock.getById(login)).thenThrow(new IllegalArgumentException());

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser
                .defaultLogin("{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}");

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Cet utilisateur n'existe pas ", ((ErrorResponse) result.get(false)).getMessage());
    }



}
