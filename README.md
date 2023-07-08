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
- [Tests](#running-the-tests)
- [Built Using](#built-using)

## About
A single page quote generator app. Uses the [Animechan API](https://animechan.xyz/) to retrieve quotes. 

## Prerequisites
- Docker
- Node

## Running the app
### To start the backend:
- Run `docker compose up --detach` in terminal from the root of this project. 
  - This will create a postgres database container needed for the app, as well as a pgAdmin container to query the database. 
- Run the main method of `QuotesAppApplication.java`
- The app will be available on `http://localhost:8080` to receive requests.

### To start the frontend:
- Navigate to `src/frontend`
- Run `npm install`.
- Run `npm run dev` to start the frontend. 
  - This will typically start on `http://localhost:5173`, but may change depending on your Node configuration. The address should be displayed in your terminal after running the above.

## Accessing the database via pgAdmin
- The docker compose file starts up a pgAdmin container alongside the postgres database.
- This can be accessed on `localhost:5050`, with email `dev@dev.co.uk` and password `dev`
- To set up the database within pgAdmin:
    - Run `docker compose up --detach` in terminal from the root of this project
    - Login and choose `Object -> Register -> Server...`
    - In the General tab:
        - Name: set to anything you wish i.e. Quotes App DB
    - In the Connection tab:
        - Host: quotes-app-db
        - Port: 5432
        - Username: dev
        - Password: dev
        - Toggle Save Password
        - Press Save
- The database should be viewable in the left-hand side browser under Servers

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
- [PostgreSQL](https://www.postgresql.org/) - Database
- [Docker](https://www.docker.com/) - Containerisation
