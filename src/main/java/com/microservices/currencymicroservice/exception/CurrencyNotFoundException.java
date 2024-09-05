package com.microservices.currencymicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CurrencyNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1236996191743114729L;

    public CurrencyNotFoundException(String code) {
        super(String.format("code: %s is not available", code));
    }

}
