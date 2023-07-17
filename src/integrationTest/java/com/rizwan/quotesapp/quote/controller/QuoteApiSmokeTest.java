package com.rizwan.quotesapp.quote.controller;

import com.rizwan.quotesapp.quote.model.enumeration.CreationType;
import com.rizwan.quotesapp.quote.model.json.QuoteJson;
import com.rizwan.quotesapp.quote.repository.QuoteRepository;
import com.rizwan.quotesapp.quote.service.QuoteService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class QuoteApiSmokeTest {

  @LocalServerPort
  private int port;

  @Container
  private static final MongoDBContainer mongoDbContainer = new MongoDBContainer(DockerImageName.parse("mongo:7.0.0-rc7"));

  @DynamicPropertySource
  static void mongoDbProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl);
  }

  private static final String API_URL = "/api/v1/quotes";

  @Autowired
  private QuoteRepository quoteRepository;

  @Autowired
  private QuoteService quoteService;

  @BeforeAll
  static void init() {
    mongoDbContainer.start();
  }

  @BeforeEach
  void setup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;

    quoteRepository.deleteAll();
  }

  @AfterAll
  static void teardown() {
    mongoDbContainer.stop();
  }

  @Test
  void getAllUserSavedQuotes() {
    quoteService.saveQuote(new QuoteJson(null, "Random Quote 1",
      "Random Author 1", "Random Origin 1", CreationType.MANUAL));
    quoteService.saveQuote(new QuoteJson(null, "Random Quote 2",
      "Random Author 2", "Random Origin 2", CreationType.SAVED));
    quoteService.saveQuote(new QuoteJson(null, "Random Quote 3",
      "Random Author 3", "Random Origin 3", CreationType.GENERATED));

    var quotesReturned = given()
      .when()
      .get(API_URL)
      .then()
      .statusCode(200)
      .extract()
      .as(QuoteJson[].class);
    assertThat(quotesReturned).extracting(QuoteJson::quoteText, QuoteJson::author, QuoteJson::origin, QuoteJson::creationType)
      .containsExactly(
        tuple("Random Quote 1", "Random Author 1", "Random Origin 1", CreationType.MANUAL),
        tuple("Random Quote 2", "Random Author 2", "Random Origin 2", CreationType.SAVED)
      );
  }

  @Test
  void saveQuote() {
    var response = given()
      .when()
      .contentType(ContentType.JSON)
      .body("""
        {
        	"quoteText": "New Quote",
        	"author": "Author",
        	"origin": "Test Origin",
        	"creationType": "MANUAL"
        }
        """)
      .post(API_URL)
      .then()
      .statusCode(201)
      .extract();
    assertThat(response.header("Location")).isNotNull();
  }

  @Test
  void saveQuote_validationError() {
    var response = given()
      .when()
      .contentType(ContentType.JSON)
      .body("""
        {
        	"quoteText": "",
        	"creationType": null
        }
        """)
      .post(API_URL)
      .then()
      .statusCode(400)
      .body("quoteText", equalTo("Quote text must not be null"))
      .body("creationType", equalTo("Creation type must not be null"));
  }
}
