package com.greentracer.app.internal;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.greentracer.app.databd.Gestion;
import com.greentracer.app.models.User;
import com.greentracer.app.responses.TestRecord;

/**
 * Composant permettant la construction de la réponse "Hello World".
 */
@Component
public class DefaultGreenTracer {
    private Gestion gs;

    private static final String HELLO = "Hello ";

    /**
     * Constructeur par défaut.
     * @param gestion
     */
    public DefaultGreenTracer(Gestion gestion) {
        this.gs = gestion;
    }

    /**
     * Fonction interne pour "Hello World".
     * @param login
     * @return Une réponse construite avec le prénom de l'utilisateur visé si il existe, le login donné sinon.
     */
    public TestRecord greeting(String login) {
        try {
            User u = gs.helloUser(login);
            TestRecord t = new TestRecord(HELLO + u.getFname() + " !");
            System.out.println(t.content());
            return t;
        } catch (SQLException e) {
            TestRecord t = new TestRecord(HELLO + login + " !");
            System.out.println(t.content());
            return new TestRecord(HELLO + login + " !");
        }
    }
}
