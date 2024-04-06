package com.greentracer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main de l'application Spring.
 */
@SpringBootApplication
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@ComponentScan("com.greentracer.app")
public class GreenTracerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenTracerApplication.class, args);
	}

}
