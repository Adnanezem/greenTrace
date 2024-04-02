package com.greentracer.app;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greentracer.app.internal.DefaultUserAPI;
import com.greentracer.app.responses.GreenTracerResponse;

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

    private final DefaultUserAPI def;

    /**
     * Constructeur par défaut.
     * @param def
     */
    public UserAPI(DefaultUserAPI def) {
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
     * @return
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
}
