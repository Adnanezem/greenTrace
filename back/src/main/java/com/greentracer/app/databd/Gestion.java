package com.greentracer.app.databd;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.greentracer.app.dao.UserDao;
import com.greentracer.models.User;

@Component
public class Gestion {

    private final UserDao userDao;

    @Autowired
    public Gestion(UserDao userDao) {
        this.userDao = userDao;
    }
    public User helloUser(String login) throws SQLException {
        try {
            User u = userDao.getById(login);
            return u;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new User(login, "1234", "toto", "titi");
        }
    }

}
