package com.greentracer.app.internal;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greentracer.app.dao.HistoriqueDao;
import com.greentracer.app.dao.JourneeDao;
import com.greentracer.app.models.Historique;
import com.greentracer.app.models.Journee;
import com.greentracer.app.responses.ErrorResponse;
import com.greentracer.app.responses.GreenTracerResponse;
import com.greentracer.app.responses.HistoriqueResponse;
import com.greentracer.app.responses.JourneeResponse;
import com.greentracer.app.utils.JSONUtils;

/**
 * Classe réalisant les opérations nécessaire pour l'API carbon.
 */
@Component
public class DefaultCarbon {
    private final HistoriqueDao histDao;
    private final JourneeDao journeeDao;

    @Autowired
    public DefaultCarbon(HistoriqueDao histDao, JourneeDao journeeDao) {
        this.histDao = histDao;
        this.journeeDao = journeeDao;
    }

    public Map<Boolean, GreenTracerResponse> defaultCompute(String body) {
        Map<Boolean, GreenTracerResponse> res = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode json = mapper.readTree(body);
            JsonNode form = json.get("form");
            float resultat = 0;
            if(form.isArray()) {
                for(JsonNode node : form) {
                    String category = JSONUtils.getStringField(node, "category");
                    String fuel = JSONUtils.getStringField(node, "fuel type");
                    String distance = JSONUtils.getStringField(node, "distance traveled");
                    switch (category) {
                        case "transport":
                        /*
                         * https://avenirclimatique.org/calculer-empreinte-carbone-trajet/
                         */
                        String transportType = JSONUtils.getStringField(node, "transport type");
                        switch (transportType) {
                            case "Trajet en voiture":
                                /*
                                 * https://calculis.net/co2
                                 */
                                resultat += CarbonCalculator.computeCarEmissions(fuel, Integer.parseInt(distance));
                            break; 
                            case "Trajet en bus":
                            /*
                             * https://ekwateur.fr/blog/enjeux-environnementaux/empreinte-carbone-bus/
                             */
                            resultat += CarbonCalculator.computeBusEmissions(fuel, Integer.parseInt(distance));
                            break;
                            default:
                                break;
                        }
                            break;
                        default:
                            break;
                    }
                }
            }
            String login = JSONUtils.getStringField(json, "login");
            Date currentDate = new Date(System.currentTimeMillis());
            Journee newJ = new Journee(0, login, currentDate, resultat);
            journeeDao.create(newJ);
            JourneeResponse resp = new JourneeResponse("journée resp", 201, newJ);
            res.put(true, resp);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            res.put(false, new ErrorResponse("Error in compute", 400));
        }
        return res;
    }

    public Map<Boolean, GreenTracerResponse> defaultGetHistory(String userId) {
        Map<Boolean, GreenTracerResponse> res = new HashMap<>();
        try {
            Historique h = histDao.getById(userId);
            HistoriqueResponse resp = new HistoriqueResponse("hist resp", 200, h);
            res.put(true, resp);
            return res;
        } catch (IllegalArgumentException e) {
            res.put(false, null);
            return res;
        }
    }

    public Map<Boolean, GreenTracerResponse> defaultGetDetailledHistory(String userId, String date) {
        Map<Boolean, GreenTracerResponse> res = new HashMap<>();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date utilDate = dateFormat.parse(date);
            Date sqlDate = new Date(utilDate.getTime());
            Journee j = journeeDao.getByDate(userId, sqlDate);
            JourneeResponse resp = new JourneeResponse("journee resp", 200, j);
            res.put(true, resp);
            return res;
        } catch (IllegalArgumentException e) {
            res.put(false, new ErrorResponse("error", 400));
            return res;
        } catch (ParseException e) {
            res.put(false, new ErrorResponse("error", 400));
            e.printStackTrace();
            return res;
        }
    }

}
