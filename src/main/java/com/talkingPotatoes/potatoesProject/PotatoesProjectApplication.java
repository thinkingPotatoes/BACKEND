package com.talkingPotatoes.potatoesProject;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableBatchProcessing
@EnableConfigurationProperties
@EnableAspectJAutoProxy // AOP
public class PotatoesProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PotatoesProjectApplication.class, args);
	}

}
