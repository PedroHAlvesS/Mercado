package br.com.compass.Sprint05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class Sprint05Application {

	public static void main(String[] args) {
		SpringApplication.run(Sprint05Application.class, args);
	}

}
