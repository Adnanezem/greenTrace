package com.greentracer.app.internal;

import com.greentracer.app.databd.Gestion;
import com.greentracer.app.models.User;
import com.greentracer.app.responses.TestRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DefaultGreenTracerTest {

    private Gestion gestionMock;
    private DefaultGreenTracer defaultGreenTracer;

    @BeforeEach
    public void setup() {
        gestionMock = Mockito.mock(Gestion.class);
        defaultGreenTracer = new DefaultGreenTracer(gestionMock);
    }

    @Test
    public void greeting_existingUser_returnsGreetingWithFirstName() throws SQLException {
        // Arrange
        String login = "testUser";
        User user = new User();
        user.setFname("Test");
        when(gestionMock.helloUser(login)).thenReturn(user);

        // Act
        TestRecord result = defaultGreenTracer.greeting(login);

        // Assert
        assertEquals("Hello Test !", result.content());
    }

    @Test
    public void greeting_nonExistingUser_returnsGreetingWithLogin() throws SQLException {
        // Arrange
        String login = "testUser";
        when(gestionMock.helloUser(login)).thenThrow(SQLException.class);

        // Act
        TestRecord result = defaultGreenTracer.greeting(login);

        // Assert
        assertEquals("Hello testUser !", result.content());
    }
}