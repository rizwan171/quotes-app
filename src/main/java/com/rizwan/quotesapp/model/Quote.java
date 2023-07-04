package com.rizwan.quotesapp.model;

import com.rizwan.quotesapp.model.enumeration.CreationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Quote {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  private String quoteText;

  private String author;

  private String origin;

  private CreationType creationType;

  public UUID getId() {
    return id;
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
