package com.rizwan.quotesapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.rizwan.quotesapp.quote.repository")
public class MongoConfig {
}
