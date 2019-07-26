package br.com.eskinfotechweb.eskfinpessoal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import br.com.eskinfotechweb.eskfinpessoal.config.properties.EskfinpessoalProperty;

@SpringBootApplication
@EnableConfigurationProperties(EskfinpessoalProperty.class)
public class EskfinpessoalApplication {

	public static void main(String[] args) {
		SpringApplication.run(EskfinpessoalApplication.class, args);
	}

}
