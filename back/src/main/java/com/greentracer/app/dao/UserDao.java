package com.greentracer.app.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.greentracer.mappers.UserMapper;
import com.greentracer.models.User;


/**
 * UserDao
 */
@Component
public class UserDao implements Dao<String, User> {

    private final JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_USER = "select * from public.users where login = ?";
    private final String SQL_DELETE_USER = "delete from public.users where login = ?";
    private final String SQL_UPDATE_USER = "update public.users set Prenom = ?, Nom = ?, MDPS = ? where login = ?";
    private final String SQL_GET_ALL = "select * from public.users";
    private final String SQL_INSERT_USER = "insert into public.users(login, Prenom, Nom, MDPS) values(?,?,?,?)";

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
            User u = jdbcTemplate.queryForObject(SQL_FIND_USER, new UserMapper(), id);
            return u;
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IllegalArgumentException("L'id utilisateur fourni est null.");
        }
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL, new UserMapper());
    }

    @Override
    public Boolean delete(User obj) {
        return jdbcTemplate.update(SQL_DELETE_USER, obj.getLogin()) > 0;
    }

    @Override
    public Boolean update(User obj) {
        return jdbcTemplate.update(SQL_UPDATE_USER, obj.getFname(),
                obj.getLname(), obj.getPassword(), obj.getLogin()) > 0;
    }

    @Override
    public Boolean create(User obj) {
        return jdbcTemplate.update(SQL_INSERT_USER, obj.getLogin(), obj.getFname(), obj.getLname(),
                obj.getPassword()) > 0;
    }

}
