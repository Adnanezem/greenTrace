package com.greentracer.app.dao;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.greentracer.app.mappers.UserMapper;
import com.greentracer.app.models.User;


/**
 * Dao dédié aux utilisateurs.
 */
@Component
public class UserDao implements Dao<String, User> {

    private static Logger logger = LoggerFactory.getLogger(UserDao.class);

    private final JdbcTemplate jdbcTemplate;

    private final String findRequest = "select * from public.user where login = ?";
    private final String deleteRequest = "delete from public.user where login = ?";
    private final String updateRequest = "update public.user set Prenom = ?, Nom = ?, MDPS = ? where login = ?";
    private final String findAllRequest = "select * from public.user";
    private final String insertRequest = "insert into public.user(login, Prenom, Nom, MDPS) values(?,?,?,?)";

    @Autowired
    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User getById(String id) throws IllegalArgumentException {
        if(id == null) {
            throw new IllegalArgumentException("L'id utilisateur fourni est null.");
        }
        try {
            User u = jdbcTemplate.queryForObject(findRequest, new UserMapper(), id);
            return u;
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IllegalArgumentException("L'id utilisateur fourni est null.");
        }
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(findAllRequest, new UserMapper());
    }

    @Override
    public Boolean delete(User obj) {
        return jdbcTemplate.update(deleteRequest, obj.getLogin()) > 0;
    }

    @Override
    public Boolean update(User obj) {
        return jdbcTemplate.update(updateRequest, obj.getFname(),
                obj.getLname(), obj.getPassword(), obj.getLogin()) > 0;
    }

    @Override
    public Boolean create(User obj) {
        return jdbcTemplate.update(insertRequest, obj.getLogin(), obj.getFname(), obj.getLname(),
                obj.getPassword()) > 0;
    }

}
