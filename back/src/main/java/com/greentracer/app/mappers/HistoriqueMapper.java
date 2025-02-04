
package com.greentracer.app.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.greentracer.app.models.Historique;

/**
 * Mapper pour Historique.
 */
public class HistoriqueMapper implements RowMapper<Historique> {

    @Override
    public Historique mapRow(ResultSet rs, int rowNum) throws SQLException {
        Historique h = new Historique();
        h.setid(rs.getInt("idHistr"));
        h.setidp(rs.getString("idP"));
        h.sethistorique(rs.getFloat("historique"));
        return h;

    }

}
