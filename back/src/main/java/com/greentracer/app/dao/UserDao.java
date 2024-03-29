package com.greentracer.app.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.greentracer.mappers.UserMapper;
import com.greentracer.models.User;

@Component
public class UserDao implements Dao<String, User> {

    private final JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_USER = "select * from user where login = ?";
    private final String SQL_DELETE_USER = "delete from user where login = ?";
    private final String SQL_UPDATE_USER = "update user set Prenom = ?, Nom = ?, MDPS = ? where login = ?";
    private final String SQL_GET_ALL = "select * from user";
    private final String SQL_INSERT_USER = "insert into user(login, Prenom, Nom, MDPS) values(?,?,?,?)";

    @Autowired
    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User getById(String id) {
        return jdbcTemplate.queryForObject(SQL_FIND_USER, new UserMapper(), new Object[] { id });
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
