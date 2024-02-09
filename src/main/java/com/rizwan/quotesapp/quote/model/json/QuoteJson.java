package com.rizwan.quotesapp.quote.model.json;

import com.fasterxml.jackson.annotation.JsonView;
import com.rizwan.quotesapp.quote.model.Quote;
import com.rizwan.quotesapp.quote.model.enumeration.CreationType;
import com.rizwan.quotesapp.quote.views.Views;

import java.util.UUID;

public record QuoteJson(
  @JsonView({Views.Get.class, Views.Patch.class})
  UUID id,
  @JsonView({Views.Get.class, Views.Post.class, Views.Patch.class})
  String quoteText,
  @JsonView({Views.Get.class, Views.Post.class, Views.Patch.class})
  String author,
  @JsonView({Views.Get.class, Views.Post.class, Views.Patch.class})
  String origin,
  @JsonView({Views.Get.class, Views.Post.class, Views.Patch.class})
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
