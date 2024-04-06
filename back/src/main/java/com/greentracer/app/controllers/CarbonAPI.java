package com.greentracer.app.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greentracer.app.internal.DefaultCarbon;

/**
 * API Pour le calcul et la prise d'information sur les empreintes carbonnes.
 */
@RestController
@RequestMapping("/carbon")
public class CarbonAPI {

    private static Logger logger = LoggerFactory.getLogger(CarbonAPI.class);

    private DefaultCarbon def;

    /**
     * Constructeur par défaut.
     * 
     * @param def
     */
    public CarbonAPI(DefaultCarbon def) {
        this.def = def;
    }

    /**
     * Calcule l'empreinte carbonne.
     * @param body
     * @return un résultat sous forme de json.
     */
    @PostMapping("/compute")
    public ResponseEntity<?> compute(@RequestBody String body) {
        URI uri;
        try { // A FAIRE DANS DEFAULT
            uri = new URI("uriadefinir");
            return ResponseEntity.created(uri).build();
        } catch (URISyntaxException e) {
            logger.info(body);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Retourne l'historique des empreintes carbonnes journalière avec une limite de 30 jours.
     * @return une réponse json.
     */
    @GetMapping("/{id}/history")
    public ResponseEntity<?> getHistory() {
        return ResponseEntity.ok().build();
    }

    /**
     * Retourne le bilan d'un jour détaillé. 
     * @param date une date sous forme de String.
     * @return une réponse json.
     */
    @GetMapping("/{id}/history/{date}")
    public ResponseEntity<?> getDetailledHistory(@PathVariable String date) {
        return ResponseEntity.ok().build();
    }

}
