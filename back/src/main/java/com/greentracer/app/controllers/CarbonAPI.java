package com.greentracer.app.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import com.greentracer.app.responses.GreenTracerResponse;
import com.greentracer.app.responses.HistoriqueResponse;
import com.greentracer.app.responses.JourneeResponse;

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
        Map<Boolean, GreenTracerResponse> res = new HashMap<>();
        try {
            res = def.defaultCompute(body);
            uri = new URI("uriadefinir");
            return ResponseEntity.created(uri).body(res.get(true));
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
    public ResponseEntity<?> getHistory(@PathVariable String id) {
        Map<Boolean, GreenTracerResponse> resMap = new HashMap<>();
        resMap = def.defaultGetHistory(id);
        Iterator<Map.Entry<Boolean, GreenTracerResponse>> iterator = resMap.entrySet().iterator();
        Map.Entry<Boolean, GreenTracerResponse> res = iterator.next();
        if(!res.getKey()) {
            return ResponseEntity.status(res.getValue().getStatus()).body(res.getValue());
        }
        HistoriqueResponse response = (HistoriqueResponse) resMap.get(true);
        return ResponseEntity.ok().body(response);
    }

    /**
     * Retourne le bilan d'un jour détaillé. 
     * @param id l'id de l'utilisateur.
     * @param date une date sous forme de String.
     * @return une réponse json.
     */
    @GetMapping("/{id}/history/{date}")
    public ResponseEntity<?> getDetailledHistory(@PathVariable String id, @PathVariable String date) {
        Map<Boolean, GreenTracerResponse> resMap = new HashMap<>();
        resMap = def.defaultGetDetailledHistory(id, date);
        Iterator<Map.Entry<Boolean, GreenTracerResponse>> iterator = resMap.entrySet().iterator();
        Map.Entry<Boolean, GreenTracerResponse> res = iterator.next();
        if(!res.getKey()) {
            return ResponseEntity.status(res.getValue().getStatus()).body(res.getValue());
        }
        JourneeResponse response = (JourneeResponse) resMap.get(true);
        return ResponseEntity.ok().body(response);
    }

}
