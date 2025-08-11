package com.airtribe.NewsAggregatorAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class NewsAggregatorApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsAggregatorApiApplication.class, args);
		
		
		
	}

}
