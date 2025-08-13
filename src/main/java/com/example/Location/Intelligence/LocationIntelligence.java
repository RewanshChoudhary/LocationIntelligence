package com.example.Location.Intelligence;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableScheduling
@EnableKafka
@RequiredArgsConstructor


public class LocationIntelligence  {









	public static void main(String[] args) {
		SpringApplication.run(LocationIntelligence.class, args);



	}


}
