
package com.greentracer.app.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.greentracer.app.models.Historique;



public class HistoriqueMapper  implements RowMapper<Historique>{

    
    @Override
    public Historique mapRow(ResultSet rs, int rowNum) throws SQLException {
        Historique h = new Historique(); 
		h.setid(rs.getInt("id_histr"));
		h.setid_p(rs.getString("id_p"));
        h.sethistorique(rs.getFloat("historique"));
		return h;
        
    }

}
