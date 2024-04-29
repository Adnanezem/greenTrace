package com.greentracer.app.internal;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greentracer.app.controllers.UserAPI;
import com.greentracer.app.dao.UserDao;
import com.greentracer.app.models.User;
import com.greentracer.app.responses.ErrorResponse;
import com.greentracer.app.responses.GreenTracerResponse;
import com.greentracer.app.responses.UserResponse;
import com.greentracer.app.utils.JSONUtils;
import com.greentracer.app.utils.JwtTokenUtil;

/**
 * Classe réalisant les opérations internes de l'endpoint "users".
 */
@Component
public class DefaultUser {

    private final UserDao userDao;
    private final JwtTokenUtil jwt;

    private static final String FORMAT_JSON_NON_RESPECTE = "Format JSON non respecté";

    /**
     * Constructeur par défaut.
     * 
     * @param userDao
     */
    @Autowired
    public DefaultUser(UserDao userDao, JwtTokenUtil jwt) {
        this.userDao = userDao;
        this.jwt = jwt;
    }

    private static Logger logger = LoggerFactory.getLogger(UserAPI.class);

    /**
     * Vérifie les données fournies et construit la réponse en conséquence.
     * 
     * @param body
     * @return une Map contenant vrai ou faux en clé (vrai si l'authentification est
     *         correcte faux sinon),
     *         et un objet de réponse en valeur (utilisé pour retourner le message
     *         d'erreur spécifique uniquement).
     */
    public Map<Boolean, GreenTracerResponse> defaultLogin(String body) {
        Map<Boolean, GreenTracerResponse> res = new HashMap<>();
        if (body.isEmpty()) {
            ErrorResponse err = new ErrorResponse("Corps de requête vide.", 401);
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
                ErrorResponse err = new ErrorResponse("Le champ login ou password est vide.", 400);
                res.put(false, err);
                return res;
            }
            logger.info("login: {}, password: {}", login, password);
            User u = userDao.getById(login);
            if (u.getPassword().equals(password)) {
                String token = jwt.generateToken(login);
                logger.debug(token);
                GreenTracerResponse response = new GreenTracerResponse(token, null);
                res.put(true, response);
            } else {
                ErrorResponse err = new ErrorResponse("Mot de passe erronée.", 403);
                res.put(false, err);
            }
        } catch (JsonProcessingException e) {
            logger.error(FORMAT_JSON_NON_RESPECTE + " : {}", body);
            ErrorResponse err = new ErrorResponse(FORMAT_JSON_NON_RESPECTE + ".", 400);
            res.put(false, err);
            return res;
        } catch (IllegalArgumentException e) {
            logger.error("L'utilisateur définit par {} n'existe pas dans la base.", body);
            ErrorResponse err = new ErrorResponse("Cet utilisateur n'existe pas ", 401);
            res.put(false, err);
            return res;
        }
        return res;
    }

    /**
     * Vérifie les données fournies et construit la réponse en conséquence.
     * 
     * @param body
     * @return une Map contenant vrai ou faux en clé (vrai si l'authentification est
     *         correcte faux sinon),
     *         et un objet de réponse en valeur (utilisé pour retourner le message
     *         d'erreur spécifique uniquement).
     */
    public Map<Boolean, GreenTracerResponse> defaultRegister(String body) {
        Map<Boolean, GreenTracerResponse> res = new HashMap<>();
        if (body.isEmpty()) {
            ErrorResponse err = new ErrorResponse("Corps de requête vide.", 400);
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
                ErrorResponse err = new ErrorResponse(errMsg, 400);
                res.put(false, err);
                return res;
            }
            User u = new User(login, password, lname, fname);
            Boolean b = userDao.create(u);
            if (Boolean.TRUE.equals(b)) {
                res.put(true, null);
            } else {
                logger.error("Problème lors de la création d'un user.");
                ErrorResponse err = new ErrorResponse("Problème lors de la création d'un user.", 400);
                res.put(false, err);
                return res;
            }
        } catch (JsonProcessingException e) {
            logger.error(FORMAT_JSON_NON_RESPECTE + " : {}", body);
            ErrorResponse err = new ErrorResponse(FORMAT_JSON_NON_RESPECTE + ".", 400);
            res.put(false, err);
            return res;
        }
        return res;
    }

    /**
     * Retourne les informations de l'utilisateur si il existe.
     * 
     * @param login
     * @return un objet utilisateur ou une erreur 404.
     */
    public Map<Boolean, GreenTracerResponse> defaultGetUser(String login) {
        Map<Boolean, GreenTracerResponse> res = new HashMap<>();
        if (login.isBlank()) {
            logger.error("Le login est vide.");
            ErrorResponse err = new ErrorResponse("Le login est vide.", 400);
            res.put(false, err);
            return res;
        }
        logger.info("login: {}", login);
        try {
            User u = userDao.getById(login);
            UserResponse uResp = new UserResponse("Informations de l'utilisateur.", 200, u);
            res.put(true, uResp);
        } catch (IllegalArgumentException e) {
            logger.error("erreur : {}", e.getMessage());
            ErrorResponse err = new ErrorResponse("Une erreur est survenue lors de la récupération des données.", 404);
            res.put(false, err);
        }
        return res;
    }

    /**
     * Met à jour les informations de l'utilisateur.
     * @param login
     * @param body Contient les paramètres possiblement mis à jour de l'utilisateur.
     * @return une représentation JSON des informations mis à jour ou une erreur.
     */
    public Map<Boolean, GreenTracerResponse> defaultUpdateUser(String login, String body) {
        Map<Boolean, GreenTracerResponse> res = new HashMap<>();
        if (login.isBlank()) {
            logger.error("Le login est vide.");
            ErrorResponse err = new ErrorResponse("Le login est vide.", 400);
            res.put(false, err);
            return res;
        }
        logger.info("login: {}", login);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(body);
            String password = JSONUtils.getStringField(json, "password");
            String mail = JSONUtils.getStringField(json, "mail");
            String fname = JSONUtils.getStringField(json, "fname");
            String lname = JSONUtils.getStringField(json, "lname");
            Boolean isBlank = password.isBlank() || mail.isBlank() ||
                    fname.isBlank() || lname.isBlank();
            if(isBlank) {
                String errMsg = "Il manque des champs à la requête." +
                        "La requête comporte les champs password, mail, fname, lname." +
                        " Tous les champs sont des strings.";
                logger.error(errMsg);
                ErrorResponse err = new ErrorResponse(errMsg, 400);
                res.put(false, err);
                return res;
            }
            User updateU = new User(login, password, lname, fname);
            Boolean hasChanged = userDao.update(updateU);
            if(!hasChanged) {
                logger.error("erreur : la mise à jour a échoué.");
                ErrorResponse err = new ErrorResponse("La mise à jour a échoué.", 400);
                res.put(false, err);
            }
            UserResponse uResp = new UserResponse("Informations de l'utilisateur mis à jour", 200, updateU);
            res.put(true, uResp);
        } catch (IllegalArgumentException e) {
            logger.error("erreur : {}", e.getMessage());
            ErrorResponse err = new ErrorResponse("Une erreur est survenue lors de la mise à jour de l'utilisateur.", 400);
            res.put(false, err);
        } catch (JsonMappingException e) {
            logger.error("Le JSON n'a pas pu être mappé correctement : {}", body);
            ErrorResponse err = new ErrorResponse("Le JSON n'a pas pu être mappé correctement.", 400);
            res.put(false, err);
            return res;
        } catch (JsonProcessingException e) {
            logger.error(FORMAT_JSON_NON_RESPECTE + " : {}", body);
            ErrorResponse err = new ErrorResponse(FORMAT_JSON_NON_RESPECTE + ".", 400);
            res.put(false, err);
            return res;
        }
        return res;
    }

    /**
     * Ne fait rien :).
     * 
     * @return toujours vrai.
     */
    public Map<Boolean, GreenTracerResponse> defaultLogout() {
        Map<Boolean, GreenTracerResponse> res = new HashMap<>();
        res.put(true, null);
        return res;
    }
}
