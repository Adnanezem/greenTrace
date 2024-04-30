
package com.greentracer.app.dao;

import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    private static final String FIND_REQUEST = "SELECT * FROM public.journee INNER JOIN public.user  ON \"idP\" = login WHERE login = ?";
    private static final String FIND_BY_DATE_REQUEST = "SELECT * FROM public.journee WHERE \"idP\" = ? AND \"Date\" = ?";
    private static final String DELETE_REQUEST = "DELETE FROM public.journee WHERE \"idP\" = ?";
    private static final String UPDATE_REQUEST = "UPDATE public.journee SET \"Date\" = ?, resultat = ? WHERE \"idP\" = ?";
    private static final String INSERT_REQUEST = "INSERT INTO public.journee( \"idP\", \"Date\", resultat) VALUES (?, ?, ?)";
    private static final String FIND_ALL_REQUEST = "select * from public.journee";

    @Autowired
    public JourneeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Journee getById(String id) {
        try {
            List<Journee> journees = jdbcTemplate.query(FIND_REQUEST, new JourneeMapper(), id);
            if(journees.isEmpty()) {
                throw new IllegalArgumentException("Aucune journée trouvée avec l'utilisateur spécifié.");
            }
            return journees.get(journees.size() - 1);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IllegalArgumentException("Aucune journée trouvée avec l'utilisateur spécifié.");
        }

    }

    /**
     * Méthode spécifique à JourneeDao permettant de trouver des dates précise.
     * @param userId
     * @param date
     * @return
     */
    public List<Journee> getByDate(String userId, Date date) {
        List<Journee> journees = jdbcTemplate.query(FIND_BY_DATE_REQUEST, new JourneeMapper(), userId, date);
        if (journees.isEmpty()) {
            throw new IllegalArgumentException("Aucune journée trouvée avec l'utilisateur et la date spécifiée.");
        }
        return journees;
    }

    @Override
    public Boolean create(Journee journee) {
        try {
            return jdbcTemplate.update(INSERT_REQUEST, journee.getpatientId(), journee.getdate(), journee.getresultat()) > 0;
        } catch (DataIntegrityViolationException e) {
            return false;
        }
    }

    @Override
    public Boolean update(Journee journee) {
        return jdbcTemplate.update(UPDATE_REQUEST, journee.getdate(), journee.getresultat(), journee.getpatientId()) > 0;
    }
    
    @Override
    public Boolean delete(Journee journee) {
        return jdbcTemplate.update(DELETE_REQUEST, journee.getpatientId()) > 0;
    }

    @Override
    public List<Journee> getAll() {
        return jdbcTemplate.query(FIND_ALL_REQUEST, new JourneeMapper());
    }

}
