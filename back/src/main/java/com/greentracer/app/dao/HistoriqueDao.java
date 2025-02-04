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

    private static final String FIND_REQUEST = "SELECT * FROM public.historique h INNER JOIN public.\"user\" u ON h.\"idP\" = u.login WHERE u.login = ?";
    private static final String DELETE_REQUEST = "DELETE FROM public.historique WHERE \"idP\" IN (SELECT \"idP\" FROM public.\"user\" WHERE login = ?)";
    private static final String UPDATE_REQUEST = "UPDATE public.historique SET historique = ? WHERE \"idP\" IN ( SELECT login FROM public.\"user\")";
    private static final String INSERT_REQUEST = "INSERT INTO public.historique(\"idP\", historique)\n" +
            "SELECT \"idP\", AVG(daily_sum)\n" +
            "FROM (\n" +
            "    SELECT journee.\"idP\", journee.\"Date\", SUM(journee.resultat) AS daily_sum\n" +
            "    FROM journee\n" +
            "    WHERE journee.\"Date\" >= CURRENT_DATE - INTERVAL '7 days'\n" +
            "      AND journee.\"Date\" <= CURRENT_DATE\n" +
            "      AND journee.\"idP\" = ?\n" +
            "    GROUP BY journee.\"idP\", journee.\"Date\"\n" +
            ") AS daily_results\n" +
            "GROUP BY \"idP\";\n";
    private static final String FIND_ALL_REQUEST = "select * from public.historique";

    @Autowired
    public HistoriqueDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Historique getById(String id) {
        try {
            return jdbcTemplate.queryForObject(FIND_REQUEST, new HistoriqueMapper(), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

    }

    @Override
    public Boolean create(Historique historique) {
        return jdbcTemplate.update(INSERT_REQUEST, historique.getidp()) > 0;
    }

    @Override
    public Boolean update(Historique historique) {
        return jdbcTemplate.update(UPDATE_REQUEST, historique.gethistorique()) > 0;
    }

    @Override
    public Boolean delete(Historique historique) {
        return jdbcTemplate.update(DELETE_REQUEST, historique.getidp()) > 0;
    }

    @Override
    public List<Historique> getAll() {
        return jdbcTemplate.query(FIND_ALL_REQUEST, new HistoriqueMapper());
    }

}
