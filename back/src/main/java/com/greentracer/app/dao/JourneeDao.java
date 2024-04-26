
package com.greentracer.app.dao;

import java.sql.Date;
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

    private final String findRequest = "SELECT * FROM public.journee INNER JOIN public.user  ON \"idP\" = login WHERE login = ?";
    private final String findByDateRequest = "SELECT * FROM public.journee INNER JOIN public.user ON \"idP\" = login WHERE login = ? AND \"Date\" = ?";
    private final String deleteRequest = "DELETE FROM public.journee WHERE \"idP\" = ?";
    private final String updateRequest = "UPDATE public.journee SET \"Date\" = ?, resultat = ? WHERE \"idP\" = ?";
    private final String insertRequest = "INSERT INTO public.journee( \"idP\", \"Date\", resultat) VALUES (?, ?, ?)";
    private final String findAllRequest = "select * from public.journee";

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
            throw new IllegalArgumentException("Aucune journée trouvée avec l'utilisateur spécifié.");
        }

    }

    /**
     * Méthode spécifique à JourneeDao permettant de trouver une date précise.
     * @param userId
     * @param date
     * @return
     */
    public Journee getByDate(String userId, Date date) {
        try {
            Journee journee = jdbcTemplate.queryForObject(findByDateRequest, new JourneeMapper(), userId, date);
            return journee;
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IllegalArgumentException("Aucune journée trouvée avec l'utilisateur et la date spécifiée.");
        }

    }

    @Override
    public Boolean create(Journee journee) {
        return jdbcTemplate.update(insertRequest, journee.getpatientId(), journee.getdate(), journee.getresultat()) > 0;
    }

    @Override
    public Boolean update(Journee journee) {
        return jdbcTemplate.update(updateRequest, journee.getdate(), journee.getresultat(), journee.getpatientId()) > 0;
    }
    
    @Override
    public Boolean delete(Journee journee) {
        return jdbcTemplate.update(deleteRequest, journee.getpatientId()) > 0;
    }

    @Override
    public List<Journee> getAll() {
        return jdbcTemplate.query(findAllRequest, new JourneeMapper());
    }

}
