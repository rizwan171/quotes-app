package com.rizwan.quotesapp.quote.service;

import com.rizwan.quotesapp.quote.model.Quote;
import com.rizwan.quotesapp.quote.model.enumeration.CreationType;
import com.rizwan.quotesapp.quote.model.json.QuoteJson;
import com.rizwan.quotesapp.quote.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class QuoteService {

  @Autowired
  private final QuoteRepository quoteRepository;

  public QuoteService(QuoteRepository quoteRepository) {
    this.quoteRepository = quoteRepository;
  }

  public List<QuoteJson> getAllUserSavedQuotesJson() {
    return Streamable.of(quoteRepository.findAll()).stream()
        .filter(quote -> quote.getCreationType() != CreationType.GENERATED)
        .map(QuoteJson::fromEntity)
        .toList();
  }

  public QuoteJson saveQuote(QuoteJson quoteJson) {
    var quote = new Quote()
        .setId(UUID.randomUUID())
        .setQuoteText(quoteJson.quoteText())
        .setAuthor(quoteJson.author())
        .setOrigin(quoteJson.origin())
        .setCreationType(quoteJson.creationType());

    return QuoteJson.fromEntity(quoteRepository.save(quote));
  }

  public Map<String, String> validateQuote(QuoteJson quoteJson) {
    var errors = new HashMap<String, String>();

    if (quoteJson.quoteText() == null || quoteJson.quoteText().isBlank()) {
      errors.put("quoteText", "Quote text must not be null");
    }

    if (quoteJson.creationType() == null) {
      errors.put("creationType", "Creation type must not be null");
    }

    return errors;
  }

  public boolean doesQuoteExist(QuoteJson quoteJson) {
    return quoteRepository.findById(quoteJson.id()).isPresent();
  }

  public void updateQuote(QuoteJson quoteJson) {
    var quote = quoteRepository.findById(quoteJson.id()).orElseThrow();
    quote.setQuoteText(quoteJson.quoteText());
    quote.setOrigin(quoteJson.origin());
    quote.setAuthor(quote.getAuthor());
    quote.setCreationType(quoteJson.creationType());

    quoteRepository.save(quote);
  }
}
