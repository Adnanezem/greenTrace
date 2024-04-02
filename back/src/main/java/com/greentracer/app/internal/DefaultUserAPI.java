package com.greentracer.app.internal;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greentracer.app.UserAPI;
import com.greentracer.app.dao.UserDao;
import com.greentracer.app.models.User;
import com.greentracer.app.responses.Error;
import com.greentracer.app.responses.GreenTracerResponse;
import com.greentracer.app.utils.JSONUtils;

/**
 * Classe réalisant les opérations internes de l'endpoint "users".
 */
@Component
public class DefaultUserAPI {

    private final UserDao userDao;

    /**
     * Constructeur par défaut. 
     * @param userDao
     */
    @Autowired
    public DefaultUserAPI(UserDao userDao) {
        this.userDao = userDao;
    }

    private static Logger logger = LoggerFactory.getLogger(UserAPI.class);

    /**
     * Vérifie les données fournies et construit la réponse en conséquence. 
     * @param body
     * @return une Map contenant vrai ou faux en clé (vrai si l'authentification est correcte faux sinon),
     *  et un objet de réponse en valeur (utilisé pour retourner le message d'erreur spécifique uniquement).
     */
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
                Error err = new Error("Le champ login ou password est vide.", 400);
                res.put(false, err);
                return res;
            }
            logger.info("login: {}, password: {}", login, password);
            User u = userDao.getById(login);
            if (u.getPassword().equals(password)) {
                res.put(true, null);
            } else {
                Error err = new Error("Mot de passe erronée.", 403);
                res.put(false, err);
            }
        } catch (JsonProcessingException e) {
            logger.error("Format JSON non respecté : {}", body);
            Error err = new Error("Format JSON non respecté.", 400);
            res.put(false, err);
            return res;
        } catch (IllegalArgumentException e) {
            logger.error("L'utilisateur définit par {} n'existe pas dans la base.", body);
            Error err = new Error("Cet utilisateur n'existe pas ", 401);
            res.put(false, err);
            return res;
        }
        return res;
    }

    /**
     * Vérifie les données fournies et construit la réponse en conséquence. 
     * @param body
     * @return une Map contenant vrai ou faux en clé (vrai si l'authentification est correcte faux sinon),
     *  et un objet de réponse en valeur (utilisé pour retourner le message d'erreur spécifique uniquement).
     */
    public Map<Boolean, GreenTracerResponse> defaultRegister(String body) {
        Map<Boolean, GreenTracerResponse> res = new HashMap<>();
        if (body.isEmpty()) {
            Error err = new Error("Corps de requête vide.", 400);
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
                String errMsg = "Il manque des champs à la requête." +
                        "La requête comporte les champs login, password, mail, fname, lname." +
                        " Tous les champs sont des strings.";
                logger.error(errMsg);
                Error err = new Error(errMsg, 400);
                res.put(false, err);
                return res;
            }
            User u = new User(login, password, lname, fname);
            if (userDao.create(u)) {
                res.put(true, null);
            } else {
                logger.error("Problème lors de la création d'un user.");
                Error err = new Error("Problème lors de la création d'un user.", 400);
                res.put(false, err);
                return res;
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
