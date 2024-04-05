
package com.greentracer.app.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Component;

import com.greentracer.app.mappers.JourneeMapper;

import com.greentracer.app.models.Journee;


/**
 * Dao dédié aux journées.
 */
@Component
public class JourneeDao implements Dao<String, Journee> {

    private final JdbcTemplate jdbcTemplate;

    private final String findRequest = "SELECT * FROM Journee j INNER JOIN user u ON j.id_patient = u.login WHERE u.login = ?";
    private final String deleteRequest = "DELETE FROM Journee j INNER JOIN user u ON j.id_patient = u.login WHERE u.login = ?";
    private final String updateRequest = "UPDATE Journee j INNER JOIN user u ON j.id_patient = u.login SET j.date = ?, j.resultat = ? ";
    private final String insertRequest = "INSERT INTO Journee( id_patient, date, resultat) VALUES (?, ?, ?)";
    private final String findAllRequest = "select * from journee";

    @Autowired
    public JourneeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Journee getById(String id) {
        try {
            Journee journee = jdbcTemplate.queryForObject(findRequest, new JourneeMapper(), id);
            return journee;
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IllegalArgumentException("Aucune journée trouvée avec l'id spécifié.");
        }

    }

    @Override
    public Boolean create(Journee journee) {
        return jdbcTemplate.update(insertRequest, journee.getpatientId(), journee.getdate(), journee.getresultat()) > 0;
    }

    @Override
    public Boolean update(Journee journee) {
        return jdbcTemplate.update(updateRequest, journee.getdate(), journee.getresultat()) > 0;
    }
    
    @Override
    public Boolean delete(Journee journee) {
        return jdbcTemplate.update(deleteRequest, journee.getid()) > 0;
    }

    @Override
    public List<Journee> getAll() {
        return jdbcTemplate.query(findAllRequest, new JourneeMapper());
    }

}
