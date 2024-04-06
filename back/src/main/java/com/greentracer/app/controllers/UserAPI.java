package com.greentracer.app.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greentracer.app.internal.DefaultUser;
import com.greentracer.app.responses.GreenTracerResponse;
import com.greentracer.app.responses.UserResponse;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contrôleur API dédié aux utilisateurs.
 */
@RestController
@RequestMapping("/users")
public class UserAPI {
    // TODO: CONNEXION, INSCRIPTION, DECONNEXION, MODIFICATION PRENOM / NOM / MDP.
    // ACCES HISTORIQUE (=> REDIRECTION).

    private static Logger logger = LoggerFactory.getLogger(UserAPI.class);

    private final DefaultUser def;

    /**
     * Constructeur par défaut.
     * 
     * @param def
     */
    public UserAPI(DefaultUser def) {
        this.def = def;
    }

    /**
     * Méthode POST pour le login utilisateur.
     * 
     * @param body
     * @return une réponse avec le header Authorization contenant un token
     *         nécessaire à l'accès des autresMap resMapsources.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String body) {
        Map<Boolean, GreenTracerResponse> resMapponse = def.defaultLogin(body);
        Iterator<Map.Entry<Boolean, GreenTracerResponse>> iterator = resMapponse.entrySet().iterator();
        Map.Entry<Boolean, GreenTracerResponse> res = iterator.next();
        if (!res.getKey()) {
            return ResponseEntity.badRequest().body(res.getValue());
        }
        String token = res.getValue().getMessage();
        return ResponseEntity.noContent().header(HttpHeaders.AUTHORIZATION, token).build();
    }

    /**
     * Méthode POST pour l'enregistrement de l'utilisateur.
     * 
     * @param body
     * @return une réponse JSON.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody String body) {
        Map<Boolean, GreenTracerResponse> resMapponse = def.defaultRegister(body);
        Iterator<Map.Entry<Boolean, GreenTracerResponse>> iterator = resMapponse.entrySet().iterator();
        Map.Entry<Boolean, GreenTracerResponse> res = iterator.next();
        if (!res.getKey()) {
            return ResponseEntity.badRequest().body(res.getValue());
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Méthode POST pour la déconnexion de l'utilisateur.
     * 
     * @return une réponse vide (204).
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Map<Boolean, GreenTracerResponse> resMapp = def.defaultLogout();
        return ResponseEntity.noContent().build();
    }

    /**
     * Méthode GET retourne les informations de l'utilisateur en paramètre.
     * 
     * @param id
     * @return une réponse JSON.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        Map<Boolean, GreenTracerResponse> resMap = def.defaultGetUser(id);
        Iterator<Map.Entry<Boolean, GreenTracerResponse>> iterator = resMap.entrySet().iterator();
        Map.Entry<Boolean, GreenTracerResponse> res = iterator.next();
        if (!res.getKey()) {
            switch (res.getValue().getStatus()) {
                case 404:
                    return ResponseEntity.status(404).body(res.getValue());
                case 400:
                    return ResponseEntity.status(400).body(res.getValue());
                default:
                    break;
            }
        }
        UserResponse uRes = (UserResponse) res.getValue();
        return ResponseEntity.ok().body(uRes);
    }

    /**
     * Méthode GET retourne l'historique de l'utilisateur en paramètre.
     * 
     * @param id
     * @return redirection vers l'api carbon (302).
     */
    @GetMapping("/{id}/history")
    public ResponseEntity<?> getUserHistory(@PathVariable String id) {
        String redirect = "/carbon/" + id + "/history";
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirect).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody String body) {
        Map<Boolean, GreenTracerResponse> resMap = def.defaultUpdateUser(id, body);
        Iterator<Map.Entry<Boolean, GreenTracerResponse>> iterator = resMap.entrySet().iterator();
        Map.Entry<Boolean, GreenTracerResponse> res = iterator.next();
        if (!res.getKey()) {
            return ResponseEntity.status(400).body(res.getValue());
        }
        UserResponse uRes = (UserResponse) res.getValue();
        return ResponseEntity.ok().body(uRes);
    }
}
