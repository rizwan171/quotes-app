package com.rizwan.quotesapp.model.json;

import com.fasterxml.jackson.annotation.JsonView;
import com.rizwan.quotesapp.model.Quote;
import com.rizwan.quotesapp.model.enumeration.CreationType;
import com.rizwan.quotesapp.views.Views;

import java.util.UUID;

public record QuoteJson(
  @JsonView(Views.Get.class)
  UUID id,
  @JsonView({Views.Get.class, Views.Post.class})
  String quoteText,
  @JsonView({Views.Get.class, Views.Post.class})
  String author,
  @JsonView({Views.Get.class, Views.Post.class})
  String origin,
  @JsonView({Views.Get.class, Views.Post.class})
  CreationType creationType
) {
  public static QuoteJson fromEntity(Quote quote) {
    return new QuoteJson(
      quote.getId(),
      quote.getQuoteText(),
      quote.getAuthor(),
      quote.getOrigin(),
      quote.getCreationType()
    );
  }
}
