package com.rizwan.quotesapp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.rizwan.quotesapp.model.json.QuoteJson;
import com.rizwan.quotesapp.service.QuoteService;
import com.rizwan.quotesapp.views.Views;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/quotes")
public class QuoteRestApiController {

  @Autowired
  private final QuoteService quoteService;

  @Autowired
  public QuoteRestApiController(QuoteService quoteService) {
    this.quoteService = quoteService;
  }

  @JsonView(Views.Get.class)
  @GetMapping
  public ResponseEntity<List<QuoteJson>> getAllQuotes() {
    return ResponseEntity.ok().body(quoteService.getAllQuotesJson());
  }

  @JsonView(Views.Post.class)
  @PostMapping
  public ResponseEntity<?> saveQuote(@RequestBody QuoteJson quoteJson,
                                     HttpServletRequest request) {
    var validationErrors = quoteService.validateQuote(quoteJson);
    if (!validationErrors.isEmpty()) {
      return ResponseEntity.badRequest().body(validationErrors);
    }

    var savedQuote = quoteService.saveQuote(quoteJson);
    var location = URI.create(request.getRequestURL().append("/").append(savedQuote.getId()).toString());

    return ResponseEntity.created(location).build();
  }

}
