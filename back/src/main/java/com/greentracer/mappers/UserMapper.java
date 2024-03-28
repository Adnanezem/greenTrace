package com.greentracer.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.greentracer.models.User;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User u = new User();
		u.setLogin(rs.getString("login"));
		u.setFname(rs.getString("Nom"));
		u.setLname(rs.getString("Prenom"));
		u.setPassword(rs.getString("MDPS"));
		return u;
    }
    
}
