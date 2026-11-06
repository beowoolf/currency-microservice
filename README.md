# currency-microservice

[![Java](https://img.shields.io/badge/Java-25-orange)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.16-brightgreen)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8.6-C71A36)](https://maven.apache.org/)
[![Version](https://img.shields.io/badge/version-0.0.1--SNAPSHOT-blue)](pom.xml)
[![License](https://img.shields.io/badge/license-not%20specified-lightgrey)](#license)

REST microservice that returns a hardcoded value for a currency code. It is a small Spring Boot project for practicing happy-path and negative-path tests.

## Table of contents

- [Project description](#project-description)
- [Tech stack](#tech-stack)
- [Getting started locally](#getting-started-locally)
- [Available scripts](#available-scripts)
- [Project scope](#project-scope)
- [Project status](#project-status)
- [License](#license)

## Project description

`GET /currency/{code}` looks up a currency code and returns its value as JSON. Supported codes and values are stored in an in-memory map:

| Code | Value |
| ---- | ----- |
| PLN  | 3.55  |
| USD  | 5.55  |
| EUR  | 4.55  |

Example:

```bash
curl http://localhost:8080/currency/PLN
```

```json
{
  "currencyValue": 3.55
}
```

The same shape is returned for `USD` (`5.55`) and `EUR` (`4.55`). Matching is case-sensitive: `eur` is not treated as `EUR`.

Unknown codes such as `CHF`, `xyz`, or `ADASDcc` are rejected. `CurrencyNotFoundException` is mapped to **HTTP 400 Bad Request** with the message `code: {code} is not available`.

## Tech stack

| Component | Version / notes |
| --------- | --------------- |
| Java | 25 |
| Spring Boot | 3.4.16 (`spring-boot-starter-parent`) |
| Packaging | WAR (`currency-microservice` 0.0.1-SNAPSHOT) |
| Web | `spring-boot-starter-web` |
| Embedded server | Tomcat (`spring-boot-starter-tomcat`, `provided`) |
| Boilerplate | Lombok (optional, excluded from the packaged artifact) |
| Tests | `spring-boot-starter-test` (JUnit 5, MockMvc) |
| Build | Maven Wrapper 3.1.0, Apache Maven 3.8.6 |
| Containers | Multi-stage image on `eclipse-temurin:25-jdk-alpine` / `eclipse-temurin:25-jre-alpine` |

Group and artifact: `com.microservices:currency-microservice`.

There is no `application.properties` or `application.yml`. The process uses Spring Boot defaults, including port **8080**.

## Getting started locally

### Prerequisites

- JDK 25
- Docker, only if you want to run the container image

The Maven Wrapper is in the repository (`mvnw`, `mvnw.cmd`), so a separate Maven installation is not required.

### Run with Maven

From the repository root:

```bash
# Linux / macOS / Git Bash
./mvnw spring-boot:run
```

```bat
:: Windows
mvnw.cmd spring-boot:run
```

Check a known code:

```bash
curl http://localhost:8080/currency/EUR
```

Check an unknown code (expect HTTP 400):

```bash
curl -i http://localhost:8080/currency/CHF
```

### Run with Docker

The Dockerfile builds the WAR with tests skipped, then starts it with `java -jar`.

```bash
docker build -f Dockerfile -t currency-service .
docker run -d -p 8001:8080 --name currency-service currency-service
curl http://localhost:8001/currency/PLN
```

### Run with Docker Compose

`docker-compose.yml` builds the same image, names the container `currency-service`, restarts it automatically, and publishes **8080**.

```bash
docker compose up -d
curl http://localhost:8080/currency/PLN
```

`docker-compose up -d` works with the Compose V1 CLI.

## Available scripts

| Command | What it does |
| ------- | ------------ |
| `./mvnw test` or `mvnw.cmd test` | Runs the unit and Spring Boot tests |
| `./mvnw spring-boot:run` or `mvnw.cmd spring-boot:run` | Starts the application on port 8080 |
| `./mvnw clean package` or `mvnw.cmd clean package` | Compiles, tests, and builds `target/*.war` |
| `./mvnw clean package -DskipTests` | Builds the WAR without tests (same goal the Docker build uses) |
| `docker build -f Dockerfile -t currency-service .` | Builds the runtime image |
| `docker run -d -p 8001:8080 --name currency-service currency-service` | Runs the image and maps host port 8001 to container port 8080 |
| `docker compose up -d` | Builds and starts the Compose service on host port 8080 |

On Windows, replace `./mvnw` with `mvnw.cmd`.

## Project scope

The service exposes one read endpoint and a fixed set of rates. It does not call an external exchange-rate API, persist data, or authenticate callers.

**Request**

```http
GET /currency/{code}
```

`{code}` is a path variable. `CurrencyController` delegates to `CurrencyFacade`, implemented by `SimpleCurrencyService`.

**Success — HTTP 200**

Body type: `CurrencyResponse`.

```json
{
  "currencyValue": 4.55
}
```

**Unknown code — HTTP 400**

`CurrencyNotFoundException` (`@ResponseStatus(HttpStatus.BAD_REQUEST)`) is thrown when the code is missing from the map. The exception message is `code: {code} is not available`.

**Layers**

- `CurrencyMicroserviceApplication` — Spring Boot entry point
- `ServletInitializer` — allows the WAR to be deployed to an external servlet container
- `CurrencyController` — `GET /currency/{code}`
- `CurrencyFacade` / `SimpleCurrencyService` — in-memory lookup
- `CurrencyResponse` — `currencyValue` field
- `CurrencyNotFoundException` — unknown-code handling

**Tests**

- `CurrencyMicroserviceApplicationTests` — application context loads
- `CurrencyControllerTest` — HTTP 200 for `EUR`; HTTP 400 for `CHF`, `xaz`, and `ADASDcc`
- `SimpleCurrencyServiceTest` — values for `EUR`, `PLN`, and `USD`; `CurrencyNotFoundException` for `xaz`, `CHF`, an empty string, and `ADASDcc`

## Project status

**Version:** 0.0.1-SNAPSHOT

This is a practice service for happy-path and other tests. Rates are hardcoded. There is no database, no live rate provider, no security, and no CI workflow in the repository.

The artifact can run as an executable WAR (`java -jar` / `spring-boot:run`) or be deployed to an external servlet container through `ServletInitializer`.

## License

This repository does not include a license file. All rights are reserved by the copyright holder until a license is added.
