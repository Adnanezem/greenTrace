package com.greentracer.app.internal;

import com.greentracer.app.dao.UserDao;
import com.greentracer.app.models.User;
import com.greentracer.app.responses.Error;
import com.greentracer.app.responses.GreenTracerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class DefaultUserTest {

    private UserDao userDaoMock;
    private DefaultUser defaultUser;

    @BeforeEach
    public void setup() {
        userDaoMock = Mockito.mock(UserDao.class);
        defaultUser = new DefaultUser(userDaoMock);
    }

    @Test
    public void defaultLogin_validCredentials_returnsTrue() {
        // Arrange
        String login = "testUser";
        String password = "password";
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        when(userDaoMock.getById(login)).thenReturn(user);

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultLogin("{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}");

        // Assert
        assertTrue(result.containsKey(true));
        assertNull(result.get(true));
    }

    @Test
    public void defaultLogin_invalidCredentials_returnsFalseWithError() {
        // Arrange
        String login = "testUser";
        String password = "wrongPassword";
        User user = new User();
        user.setLogin(login);
        user.setPassword("password");
        when(userDaoMock.getById(login)).thenReturn(user);

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultLogin("{\"login\":\"" + login + "\",\"password\":\"" + password + "\"}");

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Mot de passe erronée.", ((Error) result.get(false)).getMsg());
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
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultRegister("{\"login\":\"" + login + "\",\"password\":\"" + password + "\",\"mail\":\"" + mail + "\",\"fname\":\"" + fname + "\",\"lname\":\"" + lname + "\"}");

        // Assert
        assertTrue(result.containsKey(true));
        assertNull(result.get(true));
    }

    @Test
    public void defaultRegister_invalidData_returnsFalseWithError() {
        // Arrange
        String login = "testUser";
        String password = "password";
        String mail = "test@mail.com";
        String fname = "Test";
        String lname = "User";
        when(userDaoMock.create(Mockito.any(User.class))).thenReturn(false);

        // Act
        Map<Boolean, GreenTracerResponse> result = defaultUser.defaultRegister("{\"login\":\"" + login + "\",\"password\":\"" + password + "\",\"mail\":\"" + mail + "\",\"fname\":\"" + fname + "\",\"lname\":\"" + lname + "\"}");

        // Assert
        assertTrue(result.containsKey(false));
        assertEquals("Problème lors de la création d'un user.", ((Error) result.get(false)).getMsg());
    }
}