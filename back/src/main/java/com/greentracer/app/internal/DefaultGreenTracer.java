package com.greentracer.app.internal;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.greentracer.app.databd.Gestion;
import com.greentracer.app.responses.testRecord;
import com.greentracer.models.User;

@Component
public class DefaultGreenTracer {
    private Gestion gs;

    public DefaultGreenTracer(Gestion gestion) {
        this.gs = gestion;
    }

    public testRecord greeting(String login) {
        try {
            User u = gs.helloUser(login);
            testRecord t = new testRecord("Hello " + u.getFname() + " !");
            System.out.println(t.content());
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
            testRecord t = new testRecord("Hello " + login + " !");
            System.out.println(t.content());
            return new testRecord("Hello " + login + " !");
        }
    }
}
