package com.rizwan.quotesapp.quote.controller;

import com.rizwan.quotesapp.quote.model.Quote;
import com.rizwan.quotesapp.quote.model.enumeration.CreationType;
import com.rizwan.quotesapp.quote.model.json.QuoteJson;
import com.rizwan.quotesapp.quote.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(QuoteRestApiController.class)
class QuoteRestApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private QuoteService quoteService;

  @Test
  void getAllUserSavedQuotes() throws Exception {
    var quoteJson1 = generateQuoteJson();
    var quoteJson2 = generateQuoteJson();
    when(quoteService.getAllUserSavedQuotesJson()).thenReturn(List.of(quoteJson1, quoteJson2));

    mockMvc.perform(get("/api/v1/quotes")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().json("""
        [
          {
            "id": %s,
            "quoteText": "Random Quote",
            "author": "Random Author",
            "origin": "Random Origin",
            "creationType": "MANUAL"
          },
          {
            "id": %s,
            "quoteText": "Random Quote",
            "author": "Random Author",
            "origin": "Random Origin",
            "creationType": "MANUAL"
          }
        ]
        """.formatted(quoteJson1.id(), quoteJson2.id())));
  }

  @Test
  void saveQuote() throws Exception {
    var quote = new Quote()
      .setQuoteText("Quote")
      .setAuthor("Author")
      .setOrigin("Origin")
      .setCreationType(CreationType.MANUAL);
    var quoteJson = QuoteJson.fromEntity(quote);
    when(quoteService.validateQuote(quoteJson)).thenReturn(Map.of());

    var savedQuoteJson = QuoteJson.fromEntity(quote.setId(UUID.randomUUID()));
    when(quoteService.saveQuote(quoteJson)).thenReturn(savedQuoteJson);

    mockMvc.perform(post("/api/v1/quotes")
      .contentType(MediaType.APPLICATION_JSON)
      .content("""
        {
          "quoteText": "Quote",
          "author": "Author",
          "origin": "Origin",
          "creationType": "MANUAL"
        }
        """))
      .andExpect(status().isCreated())
      .andExpect(header().exists("Location"))
      .andExpect(content().json("""
        {
          "id": %s,
          "quoteText": "Quote",
          "author": "Author",
          "origin": "Origin",
          "creationType": "MANUAL"
        }
        """.formatted(savedQuoteJson.id())));
  }

  @Test
  void saveQuote_validationError() throws Exception {
    when(quoteService.validateQuote(any(QuoteJson.class))).thenReturn(Map.of("error", "Error"));
    mockMvc.perform(post("/api/v1/quotes")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

  @Test
  void updateQuote() throws Exception {
    var quote = new Quote()
      .setId(UUID.randomUUID())
      .setQuoteText("Updated Quote")
      .setAuthor("Updated Author")
      .setOrigin("Updated Origin")
      .setCreationType(CreationType.MANUAL);
    var quoteJson = QuoteJson.fromEntity(quote);

    when(quoteService.doesQuoteExist(quoteJson)).thenReturn(true);
    when(quoteService.validateQuote(quoteJson)).thenReturn(Map.of());

    mockMvc.perform(patch("/api/v1/quotes")
        .contentType(MediaType.APPLICATION_JSON)
        .content("""
        {
          "id": "%s",
          "quoteText": "Updated Quote",
          "author": "Updated Author",
          "origin": "Updated Origin",
          "creationType": "MANUAL"
        }
        """.formatted(quote.getId())))
      .andExpect(status().isOk());
  }

  @Test
  void updateQuote_nonExistentQuote() throws Exception {
    when(quoteService.doesQuoteExist(any(QuoteJson.class))).thenReturn(false);
    mockMvc.perform(patch("/api/v1/quotes")
      .contentType(MediaType.APPLICATION_JSON)
      .content("""
        {
          "id": "%s",
          "quoteText": "Updated Quote",
          "author": "Updated Author",
          "origin": "Updated Origin",
          "creationType": "MANUAL"
        }
        """.formatted(UUID.randomUUID())))
      .andExpect(status().isNotFound());
  }

  @Test
  void updateQuote_validationError() throws Exception {
    when(quoteService.validateQuote(any(QuoteJson.class))).thenReturn(Map.of("error", "Error"));
    mockMvc.perform(patch("/api/v1/quotes")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isBadRequest());
  }

  private QuoteJson generateQuoteJson() {
    return new QuoteJson(UUID.randomUUID(), "Random Quote", "Random Author",
      "Random Origin", CreationType.MANUAL);
  }
}