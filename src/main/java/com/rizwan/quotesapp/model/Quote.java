package com.rizwan.quotesapp.model;

import com.rizwan.quotesapp.model.enumeration.CreationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "quotes")
public class Quote {

  @Id
  private UUID id;

  private String quoteText;

  private String author;

  private String origin;

  private CreationType creationType;

  public UUID getId() {
    return id;
  }

  public Quote setId(UUID id) {
    this.id = id;
    return this;
  }

  public String getQuoteText() {
    return quoteText;
  }

  public Quote setQuoteText(String quoteText) {
    this.quoteText = quoteText;
    return this;
  }

  public String getAuthor() {
    return author;
  }

  public Quote setAuthor(String author) {
    this.author = author;
    return this;
  }

  public String getOrigin() {
    return origin;
  }

  public Quote setOrigin(String origin) {
    this.origin = origin;
    return this;
  }

  public CreationType getCreationType() {
    return creationType;
  }

  public Quote setCreationType(CreationType creationType) {
    this.creationType = creationType;
    return this;
  }
}
