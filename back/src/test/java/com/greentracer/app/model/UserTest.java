package com.greentracer.app.model;

import com.greentracer.app.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
    }

    @Test
    void setAndGetLogin() {
        String login = "testLogin";
        user.setLogin(login);
        assertEquals(login, user.getLogin());
    }

    @Test
    void setAndGetPassword() {
        String password = "testPassword";
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }

    @Test
    void setAndGetLname() {
        String lname = "testLname";
        user.setLname(lname);
        assertEquals(lname, user.getLname());
    }

    @Test
    void setAndGetFname() {
        String fname = "testFname";
        user.setFname(fname);
        assertEquals(fname, user.getFname());
    }

    @Test
    void toString_returnsCorrectFormat() {
        String login = "testLogin";
        String password = "testPassword";
        String lname = "testLname";
        String fname = "testFname";

        user.setLogin(login);
        user.setPassword(password);
        user.setLname(lname);
        user.setFname(fname);

        String expected = "User [login=" + login + ", password=" + password + ", lname=" + lname + ", fname=" + fname + "]";
        assertEquals(expected, user.toString());
    }
}