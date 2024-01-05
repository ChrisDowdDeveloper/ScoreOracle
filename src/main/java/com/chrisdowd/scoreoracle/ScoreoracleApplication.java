package com.chrisdowd.scoreoracle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ScoreoracleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoreoracleApplication.class, args);
	}

}
