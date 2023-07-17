<h3 align="center">Quotes App</h3>

<div align="center">

[![Status](https://img.shields.io/badge/status-active-badge)]()
[![Issues](https://img.shields.io/github/issues/rizwan171/quotes-app?color=blue)](https://github.com/rizwan171/quotes-app/issues)
[![Pull Requests](https://img.shields.io/github/issues-pr/rizwan171/quotes-app?color=blue)](https://github.com/rizwan171/quotes-app/pulls)
[![Repository Size](https://img.shields.io/github/repo-size/rizwan171/quotes-app)]()

</div>

---

## 📝 Table of Contents
- [About](#about)
- [Prerequisites](#prerequisites)
- [Running the app](#running-the-app)
- [Running the tests](#running-the-tests)
- [Built Using](#built-using)

## About
A single page quote generator app. Uses the [Animechan API](https://animechan.xyz/) to retrieve quotes. 

## Prerequisites
- Docker
- Node

## Running the app
### To start the backend:
- Run `docker compose up --detach` in terminal from the root of this project. 
  - This will create a mongodb database container needed for the app, as well as a mongo express container to view and query the database. 
- Run the main method of `QuotesAppApplication.java`
- The backend will be available on `http://localhost:8080` to receive requests.

### To start the frontend:
- Navigate to `src/frontend`
- Run `npm install`.
- Run `npm run dev` to start the frontend. 
  - This will typically start on `http://localhost:5173`, but may change depending on your Node configuration. The address should be displayed in your terminal after running the above.

## Accessing the database via mongo express
- The docker compose file starts up a mongo express container alongside the database.
- This can be accessed on `localhost:8888`, with username `dev` and password `dev`.
- When you first start up the app, the `quotesdb` database will not be present. It will be created once a quote has been saved or created via a POST request.

### Tearing down the database
- To reset the database, using mongo express, view the `quotesdb` database and select the `quotes` collection.
  - NOTE: the `quotesdb` and `quotes` collection will only be present if you've saved some quotes
- Delete all the documents in the `quotes` collection to reset the state.

## Running the tests
This project features 2 types of tests: unit and integration tests.<br>
Further tests will be added in the future, namely Jest tests for Vue frontend components and End-to-End testing using Cypress.

### Running Java Unit Tests
Java unit tests are located under `src/test/java`. To run, either:
- Run via the line gutter in your IDE.
- Run `./gradlew test` in your terminal to run all the unit tests.

### Running Java Integration Tests
Java integration tests are located under `src/integrationTest/java`. To run, either:
- Run via the line gutter in your IDE.
- Run `./gradlew integrationTest` in your terminal to run all the integration tests.

## Built Using
- [Spring Boot](https://spring.io/projects/spring-boot) - Backend
- [Vue.js](https://vuejs.org/) - Frontend Framework
- [Typescript](https://www.typescriptlang.org/) - syntactic superset of JavaScript for typing
- [MongoDB](https://www.mongodb.com/) - Database
- [Docker](https://www.docker.com/) - Containerisation
