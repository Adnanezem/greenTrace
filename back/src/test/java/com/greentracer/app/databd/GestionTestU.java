package com.greentracer.app.databd;

import com.greentracer.app.dao.UserDao;
import com.greentracer.app.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GestionTestU {

    @Mock
    private UserDao userDao;

    private Gestion gestion;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        gestion = new Gestion(userDao);
    }

    @Test
    public void testHelloUser() throws SQLException {
        String login = "testLogin";
        User user = new User(login, "1234", "toto", "titi");
        when(userDao.getById(login)).thenReturn(user);

        User result = gestion.helloUser(login);

        assertEquals(user, result);
        verify(userDao, times(1)).getById(login);
    }

    @Test
    public void testHelloUserThrowsIllegalArgumentException() throws SQLException {
        String login = "testLogin";
        when(userDao.getById(login)).thenThrow(new IllegalArgumentException());

        User result = gestion.helloUser(login);

        assertNotNull(result);
        assertEquals(login, result.getLogin());
        assertEquals("1234", result.getPassword());
        assertEquals("toto", result.getLname());
        assertEquals("titi", result.getFname());
        verify(userDao, times(1)).getById(login);
    }

    @Test
    public void testHelloUserThrowsRuntimeException() {
        String login = "testLogin";
        when(userDao.getById(login)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> gestion.helloUser(login));
        verify(userDao, times(1)).getById(login);
    }
}