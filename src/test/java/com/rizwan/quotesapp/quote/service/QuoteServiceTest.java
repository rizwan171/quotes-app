package com.rizwan.quotesapp.quote.service;

import com.rizwan.quotesapp.quote.model.Quote;
import com.rizwan.quotesapp.quote.model.enumeration.CreationType;
import com.rizwan.quotesapp.quote.model.json.QuoteJson;
import com.rizwan.quotesapp.quote.repository.QuoteRepository;
import com.rizwan.quotesapp.quote.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class QuoteServiceTest {

  @Mock
  private QuoteRepository quoteRepository;

  @InjectMocks
  private QuoteService quoteService;

  @Captor
  private ArgumentCaptor<Quote> argumentCaptor;

  @Test
  void getAllQuotesJson() {
    var quote1 = new Quote()
      .setId(UUID.randomUUID())
      .setQuoteText("Quote Text 1")
      .setAuthor("Author 1")
      .setOrigin("Origin 1")
      .setCreationType(CreationType.MANUAL);
    var quote2 = new Quote()
      .setId(UUID.randomUUID())
      .setQuoteText("Quote Text 2")
      .setAuthor("Author 2")
      .setOrigin("Origin 2")
      .setCreationType(CreationType.SAVED);
    when(quoteRepository.findAll()).thenReturn(List.of(quote1, quote2));

    var quoteJson1 = QuoteJson.fromEntity(quote1);
    var quoteJson2 = QuoteJson.fromEntity(quote2);
    assertThat(quoteService.getAllQuotesJson()).contains(quoteJson1, quoteJson2);
  }

  @Test
  void saveQuote() {
    var quote = new Quote()
      .setId(UUID.randomUUID())
      .setQuoteText("Quote Text 1")
      .setAuthor("Author 1")
      .setOrigin("Origin 1")
      .setCreationType(CreationType.MANUAL);
    var quoteJson = QuoteJson.fromEntity(quote);

    when(quoteRepository.save(any(Quote.class))).thenReturn(quote);
    assertThat(quoteService.saveQuote(quoteJson)).isEqualTo(quote);
    verify(quoteRepository).save(argumentCaptor.capture());
    assertThat(argumentCaptor.getValue())
      .usingRecursiveComparison()
      .ignoringFields("id")
      .isEqualTo(quote);
  }

  @Test
  void validateQuote_quoteTextNull() {
    var invalidQuoteJson = new QuoteJson(null, null, null, null, CreationType.MANUAL);
    assertThat(quoteService.validateQuote(invalidQuoteJson))
      .containsEntry("quoteText", "Quote text must not be null");
  }

  @Test
  void validateQuote_quoteTextEmpty() {
    var invalidQuoteJson = new QuoteJson(null, "    ", null, null, CreationType.MANUAL);
    assertThat(quoteService.validateQuote(invalidQuoteJson))
      .containsEntry("quoteText", "Quote text must not be null");
  }

  @Test
  void validateQuote_creationTypeNull() {
    var invalidQuoteJson = new QuoteJson(null, "Quote", null, null, null);
    assertThat(quoteService.validateQuote(invalidQuoteJson))
      .containsEntry("creationType", "Creation type must not be null");

  }
}