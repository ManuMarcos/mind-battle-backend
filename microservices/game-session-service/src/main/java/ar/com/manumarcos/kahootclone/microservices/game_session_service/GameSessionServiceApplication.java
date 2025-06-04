package ar.com.manumarcos.kahootclone.microservices.game_session_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GameSessionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameSessionServiceApplication.class, args);
	}

}
