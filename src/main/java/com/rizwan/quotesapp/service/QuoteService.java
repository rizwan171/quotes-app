package com.rizwan.quotesapp.service;

import com.rizwan.quotesapp.model.Quote;
import com.rizwan.quotesapp.model.json.QuoteJson;
import com.rizwan.quotesapp.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuoteService {

  @Autowired
  private final QuoteRepository quoteRepository;

  @Autowired
  public QuoteService(QuoteRepository quoteRepository) {
    this.quoteRepository = quoteRepository;
  }

  public List<QuoteJson> getAllQuotesJson() {
    return Streamable.of(quoteRepository.findAll()).stream()
      .map(QuoteJson::fromEntity)
      .toList();
  }

  public Quote saveQuote(QuoteJson quoteJson) {
    var quote = new Quote()
      .setQuoteText(quoteJson.quoteText())
      .setAuthor(quoteJson.author())
      .setOrigin(quoteJson.origin())
      .setCreationType(quoteJson.creationType());

    return quoteRepository.save(quote);
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
}
