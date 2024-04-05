package com.greentracer.app.controllers;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greentracer.app.internal.DefaultUser;
import com.greentracer.app.responses.GreenTracerResponse;

import jakarta.websocket.server.PathParam;

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
     * @param def
     */
    public UserAPI(DefaultUser def) {
        this.def = def;
    }

    /**
     * Méthode POST pour le login utilisateur.
     * @param body
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String body) {
        Map<Boolean, GreenTracerResponse> response = def.defaultLogin(body);
        Iterator<Map.Entry<Boolean, GreenTracerResponse>> iterator = response.entrySet().iterator();
        Map.Entry<Boolean, GreenTracerResponse> isAccepted = iterator.next();
        if (!isAccepted.getKey()) {
            return ResponseEntity.badRequest().body(isAccepted.getValue());
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Méthode POST pour l'enregistrement de l'utilisateur.
     * @param body
     * @return une réponse JSON.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody String body) {
        Map<Boolean, GreenTracerResponse> response = def.defaultRegister(body);
        Iterator<Map.Entry<Boolean, GreenTracerResponse>> iterator = response.entrySet().iterator();
        Map.Entry<Boolean, GreenTracerResponse> isAccepted = iterator.next();
        if (!isAccepted.getKey()) {
            return ResponseEntity.badRequest().body(isAccepted.getValue());
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Méthode POST pour la déconnexion de l'utilisateur.
     * @param body
     * @return une réponse JSON.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody String body) {
        return ResponseEntity.noContent().build();
    }

    /**
     * Méthode GET retourne les informations de l'utilisateur en paramètre.
     * @param id
     * @return une réponse JSON.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        return ResponseEntity.ok().build();
    }

    /**
     * Méthode GET retourne l'historique de l'utilisateur en paramètre.
     * @param id
     * @return redirection vers l'api carbon.
     */
    @GetMapping("/{id}/history")
    public ResponseEntity<?> getUserHistory(@PathVariable String id) {
        return ResponseEntity.ok().build();
    }
}
