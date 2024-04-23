package com.greentracer.app.internal;

import com.greentracer.app.dao.UserDao;
import com.greentracer.app.models.User;
import com.greentracer.app.responses.ErrorResponse;
import com.greentracer.app.responses.GreenTracerResponse;
import com.greentracer.app.utils.JwtTokenUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class DefaultUserTest {

    private static Logger logger = LoggerFactory.getLogger(DefaultUserTest.class);

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
    public void defaultLogin_validCredentials_returnsTrue() {
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
        assertEquals(result.get(true).getMessage(), token);
    }

    @Test
    public void defaultLogin_invalidCredentials_returnsFalseWithErrorResponse() {
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
    public void defaultRegister_validData_returnsTrue() {
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
    public void defaultRegister_invalidData_returnsFalseWithErrorResponse() {
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
}