package com.eyes.solstory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.eyes.solstory.domain")
@SpringBootApplication
public class SOLStoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SOLStoryApplication.class, args);
	}

}
