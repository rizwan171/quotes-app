package com.rizwan.quotesapp.controller;

import com.rizwan.quotesapp.model.enumeration.CreationType;
import com.rizwan.quotesapp.model.json.QuoteJson;
import com.rizwan.quotesapp.repository.QuoteRepository;
import com.rizwan.quotesapp.service.QuoteService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationTest")
@Testcontainers
class QuoteApiSmokeTest {

  @LocalServerPort
  private int port;

  @Container
  private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
    .withUsername("dev")
    .withPassword("dev")
    .withDatabaseName("qadb");

  private static final String API_URL = "/api/v1/quotes";

  @Autowired
  private QuoteRepository quoteRepository;

  @Autowired
  private QuoteService quoteService;

  @BeforeAll
  static void init() {
    postgreSQLContainer.start();
  }

  @BeforeEach
  void setup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;

    quoteRepository.deleteAll();
  }

  @AfterAll
  static void teardown() {
    postgreSQLContainer.stop();
  }

  @Test
  void getAllQuotes() {
    quoteService.saveQuote(new QuoteJson(null, "Random Quote",
      "Random Author", "Random Origin", CreationType.MANUAL));

    var quotesReturned = given()
      .when()
      .get(API_URL)
      .then()
      .statusCode(200)
      .extract()
      .as(QuoteJson[].class);
    assertThat(quotesReturned).extracting(QuoteJson::quoteText, QuoteJson::author, QuoteJson::origin, QuoteJson::creationType)
      .containsExactly(tuple("Random Quote", "Random Author", "Random Origin", CreationType.MANUAL));
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
