package com.greentracer.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(properties = "spring.main.lazy-initialization=false",
classes = {GreenTracerApplication.class})
class GreenTracerApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("AAAAAAA");
	}

}
