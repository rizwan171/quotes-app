package com.rizwan.quotesapp.repository;

import com.rizwan.quotesapp.model.Quote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, UUID> {
}