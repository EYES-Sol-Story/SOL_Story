package com.eyes.solstory;

//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.eyes.solstory.domain.financial.repository", "com.eyes.solstory.domain.challenge.repository", "com.eyes.solstory.domain.user.repository"})
@EntityScan(basePackages = "com.eyes.solstory.domain")
@SpringBootApplication
public class SOLStoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SOLStoryApplication.class, args);
	}

}
