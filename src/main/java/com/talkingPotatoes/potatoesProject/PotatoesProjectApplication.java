package com.talkingPotatoes.potatoesProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PotatoesProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PotatoesProjectApplication.class, args);
	}

}
