package com.greentracer.app;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import com.greentracer.app.internal.DefaultGreenTracer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = "spring.main.lazy-initialization=false", classes = { GreenTracerApplication.class })
class GreenTracerApplicationTests {
	private static Logger logger = LoggerFactory.getLogger(GreenTracerApplicationTests.class);

	@Autowired
	private Environment environment;

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Test
	void contextLoads() {
		logger.info("driverClassName: {}", driverClassName);
		assertEquals(url, environment.getProperty("spring.datasource.url"));
		assertEquals(username, environment.getProperty("spring.datasource.username"));
		assertEquals(password, environment.getProperty("spring.datasource.password"));
		assertEquals(driverClassName, environment.getProperty("spring.datasource.driver-class-name"));
	}
}
