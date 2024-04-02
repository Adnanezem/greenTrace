package com.greentracer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main de l'application Spring.
 */
@SpringBootApplication
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class GreenTracerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenTracerApplication.class, args);
	}

}
