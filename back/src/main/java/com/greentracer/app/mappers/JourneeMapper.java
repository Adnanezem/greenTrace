package com.greentracer.app.mappers;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.greentracer.app.models.Journee;


/**
 * Mapper pour journee (pour le SQL).
 */

public class JourneeMapper implements RowMapper<Journee> {
    

    @Override
    public Journee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Journee j = new Journee(); 
		j.setid(rs.getString("id_journee"));
		j.setpatientId(rs.getString("id_patient"));
        j.setdate(rs.getDate("Date")); 
        j.setresultat(rs.getString("resultat"));
		return j;
    }
}


