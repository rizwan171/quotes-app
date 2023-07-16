package com.rizwan.quotesapp.quote.repository;

import com.rizwan.quotesapp.quote.model.Quote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuoteRepository extends MongoRepository<Quote, UUID> {
}