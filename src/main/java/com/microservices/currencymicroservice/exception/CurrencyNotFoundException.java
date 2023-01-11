package com.microservices.currencymicroservice.exception;

public class CurrencyNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1236996191743114729L;

    public CurrencyNotFoundException(String code) {
        super(String.format("code: %s is not available", code));
    }

}
