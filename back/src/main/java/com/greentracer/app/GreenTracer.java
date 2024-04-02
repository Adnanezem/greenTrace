package com.greentracer.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greentracer.app.internal.DefaultGreenTracer;
import com.greentracer.app.responses.TestRecord;

/**
 * Contrôleur REST de test permettant simplement l'affichage d'un Hello World.
 */
@RestController
public class GreenTracer {

    private final DefaultGreenTracer def;

    /**
     * Defaut construct.
     * @param def
     */
    public GreenTracer(DefaultGreenTracer def) {
        this.def = def;
    }

    /**
     * Méthode get permettant d'afficher Hello World custom.
     * @param name
     * @return
     */
    @GetMapping("/")
	public TestRecord greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return def.greeting(name);
	}
}
