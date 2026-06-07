package com.kenneth.kwube;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class KwubeApplication {

	public static void main(String[] args) {
		SpringApplication.run(KwubeApplication.class, args);
	}

}
