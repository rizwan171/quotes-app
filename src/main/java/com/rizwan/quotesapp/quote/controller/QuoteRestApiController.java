package com.rizwan.quotesapp.quote.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.rizwan.quotesapp.quote.model.json.QuoteJson;
import com.rizwan.quotesapp.quote.service.QuoteService;
import com.rizwan.quotesapp.quote.views.Views;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/quotes")
public class QuoteRestApiController {

  @Autowired
  private final QuoteService quoteService;

  public QuoteRestApiController(QuoteService quoteService) {
    this.quoteService = quoteService;
  }

  @JsonView(Views.Get.class)
  @GetMapping
  public ResponseEntity<List<QuoteJson>> getAllUserSavedQuotes() {
    return ResponseEntity.ok().body(quoteService.getAllUserSavedQuotesJson());
  }

  @PostMapping
  public ResponseEntity<?> saveQuote(@JsonView(Views.Post.class) @RequestBody QuoteJson quoteJson, HttpServletRequest request) {
    var validationErrors = quoteService.validateQuote(quoteJson);
    if (!validationErrors.isEmpty()) {
      return ResponseEntity.badRequest().body(validationErrors);
    }

    var savedQuoteJson = quoteService.saveQuote(quoteJson);
    var location = URI.create(request.getRequestURL().append("/").append(savedQuoteJson.id()).toString());

    return ResponseEntity.created(location).body(savedQuoteJson);
  }

  @PatchMapping
  public ResponseEntity<?> updateQuote(@JsonView(Views.Patch.class) @RequestBody QuoteJson quoteJson) {
    if (quoteService.doesQuoteExist(quoteJson)) {
      return ResponseEntity.notFound().build();
    }

    var validationErrors = quoteService.validateQuote(quoteJson);
    if (!validationErrors.isEmpty()) {
      return ResponseEntity.badRequest().body(validationErrors);
    }

    quoteService.updateQuote(quoteJson);

    return ResponseEntity.ok().build();
  }

}
