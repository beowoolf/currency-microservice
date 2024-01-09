# currency-microservice

A service written in Spring Boot, which at the address /currency/{code} returns the value of the given currency with the
code given in the URL.

Available currencies: {code} = PLN, USD, EUR

For example, calling:

1. /currency/PLN returns 3.55
2. /currency/USD returns 5.55
3. /currency/EUR returns 4.55

To simplify the code, the values are hardcoded in the map, and in the absence of a currency with a given code, exception
handling is performed.

If you enter a currency code that does not exist in the application by making one of these example calls:

- /currency/CHF
- /currency/xyz
- /currency/ADASDcc

then an exception is handled and the microservice, instead of returning the value of this currency, returns a message
that such a {code} does not exist.

## Main commands

```bash
# image creation
$ docker build -f Dockerfile -t currency-service .

# starting the image
$ docker run -d -p 8001:8080 currency-service

# image composing
$ docker-compose up -d

# sample call to validate the process of building the image and running it
$ curl http://localhost:8001/currency/PLN
```
