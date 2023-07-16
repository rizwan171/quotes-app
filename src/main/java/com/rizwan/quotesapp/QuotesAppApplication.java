package com.rizwan.quotesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class QuotesAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuotesAppApplication.class, args);
	}

}
