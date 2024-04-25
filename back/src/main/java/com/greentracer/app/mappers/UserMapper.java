package com.greentracer.app.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.greentracer.app.models.User;

/**
 * Mapper pour les objets User (pour le SQL).
 */
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User u = new User();
		u.setLogin(rs.getString("login"));
		u.setFname(rs.getString("nom"));
		u.setLname(rs.getString("prenom"));
		u.setPassword(rs.getString("mdps"));
		return u;
    }
    
}
