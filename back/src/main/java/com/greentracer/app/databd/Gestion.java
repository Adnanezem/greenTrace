package com.greentracer.app.databd;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.greentracer.app.dao.UserDao;
import com.greentracer.app.models.User;

/**
 * Classe test relation avec la BD.
 */
@Component
public class Gestion {

    private final UserDao userDao;
    private static Logger logger = LoggerFactory.getLogger(Gestion.class);

    /**
     * Default constr.
     * 
     * @param userDao
     */
    @Autowired
    public Gestion(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Vérifie la présence de l'utilisateur demandé dans la BD.
     * 
     * @param login
     * @return
     * @throws SQLException
     */
    public User helloUser(String login) throws SQLException {
        try {
            logger.info("login est {}.", login);
            User u = userDao.getById(login);
            return u;
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument: {}", e.getMessage());
            return new User(login, "1234", "toto", "titi");
        }
    }

}
