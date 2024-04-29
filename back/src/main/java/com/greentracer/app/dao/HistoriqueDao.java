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

    private final String findRequest = "SELECT * FROM public.historique h INNER JOIN public.\"user\" u ON h.\"idP\" = u.login WHERE u.login = ?";
    private final String deleteRequest = "DELETE FROM public.historique WHERE \"idP\" IN (SELECT \"idP\" FROM public.\"user\" WHERE login = ?)";
    private final String updateRequest = "UPDATE public.historique SET historique = ? WHERE \"idP\" IN ( SELECT login FROM public.\"user\")";
    private final String insertRequest = "INSERT INTO public.historique(\"idP\", historique)\n" +
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
    private final String findAllRequest = "select * from public.historique";

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
            return null;
        }

    }

    @Override
    public Boolean create(Historique historique) {
        return jdbcTemplate.update(insertRequest, historique.getidp()) > 0;
    }

    @Override
    public Boolean update(Historique historique) {
        return jdbcTemplate.update(updateRequest, historique.gethistorique()) > 0;
    }

    @Override
    public Boolean delete(Historique historique) {
        return jdbcTemplate.update(deleteRequest, historique.getidp()) > 0;
    }

    @Override
    public List<Historique> getAll() {
        return jdbcTemplate.query(findAllRequest, new HistoriqueMapper());
    }

}
