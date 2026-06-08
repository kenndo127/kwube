package com.kenneth.kwube;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Kwube API",
				version = "1.0",
				description = "Demo web version of Igbo Language AI-Powered Voice Assistant"
		)
)
public class KwubeApplication {

	public static void main(String[] args) {
		SpringApplication.run(KwubeApplication.class, args);
	}

}
