package com.greentracer.app.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greentracer.app.internal.DefaultCarbon;
import com.greentracer.app.responses.GreenTracerResponse;
import com.greentracer.app.responses.HistoriqueResponse;
import com.greentracer.app.responses.JourneesResponse;
import com.greentracer.app.utils.JSONUtils;

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
     * 
     * @param body
     * @return un résultat sous forme de json.
     */
    @PostMapping("/compute/{isConnected}")
    public ResponseEntity<GreenTracerResponse> compute(@RequestBody String body, @PathVariable String isConnected) {
        boolean hasConnection = isConnected.equals("connect");
        URI uri;
        Map<Boolean, GreenTracerResponse> resMap = new HashMap<>();
        try {
            resMap = def.defaultCompute(body, hasConnection);
            Iterator<Map.Entry<Boolean, GreenTracerResponse>> iterator = resMap.entrySet().iterator();
            Map.Entry<Boolean, GreenTracerResponse> res = iterator.next();
            if (!res.getKey()) {
                return ResponseEntity.badRequest().build();
            }
            if(hasConnection) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode json = mapper.readTree(body);
                String login = JSONUtils.getStringField(json, "login");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date d = new Date(System.currentTimeMillis());
                String utilDate = dateFormat.format(d);
                logger.info("date URI : {}", utilDate);
                uri = new URI("/" + login + "/history/" + utilDate);
                return ResponseEntity.created(uri).body(res.getValue());
            }
            return ResponseEntity.ok().body(res.getValue());
        } catch (URISyntaxException e) {
            logger.info(body);
            return ResponseEntity.badRequest().build();
        } catch (JsonMappingException e) {
            logger.error("request body malformed: {}", body);
            return ResponseEntity.badRequest().body(new GreenTracerResponse("Malformed JSON.", 400));
        } catch (JsonProcessingException e) {
            logger.error("request body process failed: {}", body);
            return ResponseEntity.badRequest().body(new GreenTracerResponse("JSON Process failed in CarbonAPI.compute.", 400));
        }
    }

    /**
     * Retourne l'historique des empreintes carbonnes journalière avec une limite de
     * 7 jours.
     * 
     * @return une réponse json.
     */
    @GetMapping("/{id}/history")
    public ResponseEntity<GreenTracerResponse> getHistory(@PathVariable String id) {
        Map<Boolean, GreenTracerResponse> resMap = new HashMap<>();
        resMap = def.defaultGetHistory(id);
        Iterator<Map.Entry<Boolean, GreenTracerResponse>> iterator = resMap.entrySet().iterator();
        Map.Entry<Boolean, GreenTracerResponse> res = iterator.next();
        if (!res.getKey()) {
            if (res.getValue() != null) {
                return ResponseEntity.status(res.getValue().getStatus()).body(res.getValue());
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        HistoriqueResponse response = (HistoriqueResponse) resMap.get(true);
        return ResponseEntity.ok().body(response);
    }

    /**
     * Retourne le bilan d'un jour détaillé.
     * 
     * @param id   l'id de l'utilisateur.
     * @param date une date sous forme de String.
     * @return une réponse json.
     */
    @GetMapping("/{id}/history/{date}")
    public ResponseEntity<GreenTracerResponse> getDetailledHistory(@PathVariable String id, @PathVariable String date) {
        Map<Boolean, GreenTracerResponse> resMap = new HashMap<>();
        resMap = def.defaultGetDetailledHistory(id, date);
        Iterator<Map.Entry<Boolean, GreenTracerResponse>> iterator = resMap.entrySet().iterator();
        Map.Entry<Boolean, GreenTracerResponse> res = iterator.next();

        if (res.getValue() != null) {
            if (!res.getKey()) {
                return ResponseEntity.status(res.getValue().getStatus()).body(res.getValue());
            }
            JourneesResponse response = (JourneesResponse) res.getValue();
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/average")
    public ResponseEntity<GreenTracerResponse> getAvgCarbonPrint() {
        Map<Boolean, GreenTracerResponse> resMap = new HashMap<>();
        resMap = def.defaultGetAvgCarbonPrint();
        Iterator<Map.Entry<Boolean, GreenTracerResponse>> iterator = resMap.entrySet().iterator();
        Map.Entry<Boolean, GreenTracerResponse> res = iterator.next();
        if(!res.getKey()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(res.getValue());
    }



}
