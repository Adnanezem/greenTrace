package com.greentracer.app.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.greentracer.app.mappers.HistoriqueMapper;

import com.greentracer.app.models.Historique;


/**
 * Dao dédié aux journées.
 */
@Component
public class HistoriqueDao implements Dao<String, Historique> {

    private final JdbcTemplate jdbcTemplate;

    private final String findRequest = "SELECT * FROM historique h INNER JOIN user u ON h.id_p = u.login WHERE u.login = ?";
    private final String deleteRequest = "DELETE FROM historique h INNER JOIN user u ON h.id_p = u.login WHERE u.login = ?";
    private final String updateRequest = "UPDATE historique h INNER JOIN user u ON h.id_p = u.login SET  h.historique = ? ";
    private final String insertRequest = "INSERT INTO historique( id_p, historique) VALUES ( ?, ?)";
    private final String findAllRequest = "select * from historique";

    @Autowired
    public HistoriqueDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Historique getById(String id) {
        try {
            Historique historique = jdbcTemplate.queryForObject(findRequest, new HistoriqueMapper(), id);
            return historique;
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IllegalArgumentException("Aucun historique trouvé avec l'id spécifié.");
        }

    }

    @Override
    public Boolean create(Historique historique) {
        return jdbcTemplate.update(insertRequest, historique.getid_p(), historique.gethistorique()) > 0;
    }

    @Override
    public Boolean update(Historique historique) {
        return jdbcTemplate.update(updateRequest, historique.gethistorique()) > 0;
    }
    
    @Override
    public Boolean delete(Historique historique) {
        return jdbcTemplate.update(deleteRequest, historique.getid()) > 0;
    }


    @Override
    public List<Historique> getAll() {
        return jdbcTemplate.query(findAllRequest, new HistoriqueMapper());
    }




}
