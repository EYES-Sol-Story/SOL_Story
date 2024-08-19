package com.eyes.solstory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//스케줄링 사용
@EnableScheduling
@SpringBootApplication
public class SOLStoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SOLStoryApplication.class, args);
	}

}
