package com.greentracer.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greentracer.app.internal.DefaultGreenTracer;
import com.greentracer.app.responses.testRecord;


@RestController
public class GreenTracer {

    private final DefaultGreenTracer def;

    public GreenTracer(DefaultGreenTracer def) {
        this.def = def;
    }

    @GetMapping("/")
	public testRecord greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return def.greeting(name);
	}
}
