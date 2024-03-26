package com.greentracer.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.greentracer.app.internal.DefaultGreenTracer;
import com.greentracer.app.records.testRecord;


@RestController
public class GreenTracer {

    DefaultGreenTracer def = new DefaultGreenTracer();

    @GetMapping("/")
	public testRecord greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        String msg = "Hello " + name + " !";
		return def.greeting(msg);
	}
}
