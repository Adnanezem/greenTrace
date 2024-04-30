package com.greentracer.app.internal;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.greentracer.app.responses.JourneesResponse;
import com.greentracer.app.utils.CarbonCalculator;
import com.greentracer.app.utils.JSONUtils;

/**
 * Classe réalisant les opérations nécessaire pour l'API carbon.
 */
@Component
public class DefaultCarbon {
    private final HistoriqueDao histDao;
    private final JourneeDao journeeDao;

    private static Logger logger = LoggerFactory.getLogger(DefaultCarbon.class);

    @Autowired
    public DefaultCarbon(HistoriqueDao histDao, JourneeDao journeeDao) {
        this.histDao = histDao;
        this.journeeDao = journeeDao;
    }

    public Map<Boolean, GreenTracerResponse> defaultCompute(String body, boolean hasConnection)  {
        Map<Boolean, GreenTracerResponse> res = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode json = mapper.readTree(body);
            JsonNode form = json.get("form");
            float resultat = 0;
            if (form.isArray()) {
                for (JsonNode node : form) {
                    resultat += computeCarbonEmission(node);
                }
            }
            if(hasConnection) {
                String login = JSONUtils.getStringField(json, "login");
                Date currentDate = new Date(System.currentTimeMillis());
                Journee newJ = new Journee(0, login, currentDate, resultat);
                journeeDao.create(newJ);
                JourneeResponse resp = new JourneeResponse("journée resp", 201, newJ);
                Historique h = histDao.getById(login);
                if(h == null) {
                    Historique newH = new Historique(0, login, 0); // calcul auto du resultat.
                    histDao.create(newH);
                } else {
                    float newRes = h.gethistorique() + resultat;
                    Historique newH = new Historique(0, login, newRes);
                    histDao.delete(h);
                    histDao.create(newH);
                }
                res.put(true, resp);
            } else {
                Journee newJ = new Journee(0, null, null, resultat);
                JourneeResponse resp = new JourneeResponse("journée resp", 200, newJ);
                res.put(true, resp);
            }
        } catch (JsonProcessingException e) {
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse(date);
            Date sqlDate = new Date(utilDate.getTime());
            List<Journee> journees = journeeDao.getByDate(userId, sqlDate);
            JourneesResponse responses = new JourneesResponse("journee resp", 200, journees);
            res.put(true, responses);
            return res;
        } catch (IllegalArgumentException e) {
            logger.error("erreur defaultGetDetailledHistory: ", e);
            res.put(false, new ErrorResponse("Aucun historique pour ce jour.", 404));
            return res;
        } catch (ParseException e) {
            logger.error("erreur defaultGetDetailledHistory: ", e);
            res.put(false, new ErrorResponse("error", 400));
            return res;
        }
    }

    /**
     * Calcule les émissions carbonnes.
     * 
     * @param node la node json à traiter.
     * @return le résultat en float.
     */ 
    float computeCarbonEmission(final JsonNode node) {
        float resultat = 0;
        String category = JSONUtils.getStringField(node, "category");
        String fuel = JSONUtils.getStringField(node, "fuel type");
        String distance = JSONUtils.getStringField(node, "distance traveled");
        String vehicule = JSONUtils.getStringField(node, "vehicle type");
        String meal = JSONUtils.getStringField(node, "meal type");
        String restaurant  = JSONUtils.getStringField(node, "restaurant type");

        switch (category) {
            case "transport":
                String transportType = JSONUtils.getStringField(node, "type");
                switch (transportType) {
                    case "Trajet en voiture":
                        resultat += CarbonCalculator.computeCarEmissions(fuel, Integer.parseInt(distance));
                        break;
                    case "Trajet en vélo":
                        resultat += CarbonCalculator.computeVeloEmissions(vehicule, Integer.parseInt(distance));
                        break;
                    case "Trajet en bus":
                        resultat += CarbonCalculator.computeBusEmissions(fuel, Integer.parseInt(distance));
                        break;
                    case "Trajet en avion":
                        resultat += CarbonCalculator.computeAvionEmissions(vehicule, Integer.parseInt(distance));
                        break;
                    default:
                        break;
                }

                
                break;
            case "repas":
            String repasType = JSONUtils.getStringField(node, "type");
            switch (repasType) {
                case "Repas au restaurant":
                resultat += CarbonCalculator.computeRepasResto(meal, restaurant );
            }
            default:
                break;
        }

        return resultat;
    }
}
