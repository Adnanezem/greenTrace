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

    private static final String FIND_REQUEST = "select * from public.\"user\" where login = ?";
    private static final String DELETE_REQUEST = "delete from public.\"user\" where login = ?";
    private static final String UPDATE_REQUEST = "UPDATE public.user SET mdps = ?, nom = ?, prenom = ? WHERE login = ?";
    private static final String FIND_ALL_REQUEST = "select * from public.\"user\"";
    private static final String INSERT_REQUEST = "insert into public.\"user\"(login, prenom, nom, mdps) values(?,?,?,?)";

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
            return jdbcTemplate.queryForObject(FIND_REQUEST, new UserMapper(), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IllegalArgumentException("L'id utilisateur fourni est null.");
        }
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(FIND_ALL_REQUEST, new UserMapper());
    }

    @Override
    public Boolean delete(User obj) {
        return jdbcTemplate.update(DELETE_REQUEST, obj.getLogin()) > 0;
    }

    @Override
    public Boolean update(User obj) {
        return jdbcTemplate.update(UPDATE_REQUEST, obj.getPassword(), obj.getFname(),
                obj.getLname(), obj.getLogin()) > 0;
    }

    @Override
    public Boolean create(User obj) {
        return jdbcTemplate.update(INSERT_REQUEST, obj.getLogin(), obj.getFname(), obj.getLname(),
                obj.getPassword()) > 0;
    }


}
