package ru.test.jpastreams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaRepositories("ru.test.jpastreams.repository")
@EnableHypermediaSupport(type= {EnableHypermediaSupport.HypermediaType.HAL})
@EnableAsync
public class JpaStreamsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaStreamsApplication.class, args);
	}

}

