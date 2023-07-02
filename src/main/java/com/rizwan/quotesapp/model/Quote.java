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

  public void setQuoteText(String quoteText) {
    this.quoteText = quoteText;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public CreationType getCreationType() {
    return creationType;
  }

  public void setCreationType(CreationType creationType) {
    this.creationType = creationType;
  }
}
