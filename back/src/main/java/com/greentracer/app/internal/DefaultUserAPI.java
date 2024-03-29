package com.greentracer.app.internal;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greentracer.app.UserAPI;
import com.greentracer.app.responses.Error;
import com.greentracer.app.responses.GreenTracerResponse;
import com.greentracer.app.utils.JSONUtils;

public class DefaultUserAPI {

    private static Logger logger = LoggerFactory.getLogger(UserAPI.class);

    public Map<Boolean, GreenTracerResponse> defaultLogin(String body) {
        Map<Boolean, GreenTracerResponse> res = new HashMap<>();
        if (body.isEmpty()) {
            Error err = new Error("Corps de requête vide.", 401);
            res.put(false, err);
            return res;
        }
        logger.info("body: {}", body);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode json = mapper.readTree(body);
            String login = JSONUtils.getStringField(json, "login");
            String password = JSONUtils.getStringField(json, "password");
            if (login.isBlank() || password.isBlank()) {
                logger.error("Le champ login ou password est vide.");
                Error err = new Error("Le champ login ou password est vide.", 401);
                res.put(false, err);
                return res;
            }
            logger.info("login: {}, password: {}", login, password);
            // on doit requete la bd ici..
            if (true /* REMPLACER PAR RETOUR DE LA BD */) {
                res.put(true, null);
            }
        } catch (JsonProcessingException e) {
            logger.error("Format JSON non respecté : {}", body);
            Error err = new Error("Format JSON non respecté.", 401);
            res.put(false, err);
            return res;
        }
        return res;
    }

    public Map<Boolean, GreenTracerResponse> defaultRegister(String body) {
        Map<Boolean, GreenTracerResponse> res = new HashMap<>();
        if (body.isEmpty()) {
            Error err = new Error("Corps de requête vide.", 401);
            res.put(false, err);
            return res;
        }
        logger.info("body: {}", body);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode json = mapper.readTree(body);
            String login = JSONUtils.getStringField(json, "login");
            String password = JSONUtils.getStringField(json, "password");
            String mail = JSONUtils.getStringField(json, "mail");
            String fname = JSONUtils.getStringField(json, "fname");
            String lname = JSONUtils.getStringField(json, "lname");
            Boolean isBlank = login.isBlank() || password.isBlank() ||
                    mail.isBlank() || fname.isBlank() || lname.isBlank();
            if (isBlank) {
                String err_msg = "Il manque des champs à la requête." +
                "La requête comporte les champs login, password, mail, fname, lname." +
                " Tous les champs sont des strings.";
                logger.error(err_msg);
                Error err = new Error(err_msg, 401);
                res.put(false, err);
                return res;
            }
            // on doit requete la bd ici..
            if (true /* REMPLACER PAR RETOUR DE LA BD */) {
                res.put(true, null);
            }
        } catch (JsonProcessingException e) {
            logger.error("Format JSON non respecté : {}", body);
            Error err = new Error("Format JSON non respecté.", 401);
            res.put(false, err);
            return res;
        }
        return res;
    }
}
