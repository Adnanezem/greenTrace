/*package com.greentracer.app.databd;

import com.greentracer.app.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GestionIntegrationTest {

    @Autowired
    private Gestion gestion;

    @Test
    @Sql("/schemaAfaire.sql")
    public void testHelloUser() throws SQLException {
        String login = "testLogin"; //login a insérer dans la base
        User result = gestion.helloUser(login);

        assertNotNull(result);
        assertEquals(login, result.getLogin());
    }
} */