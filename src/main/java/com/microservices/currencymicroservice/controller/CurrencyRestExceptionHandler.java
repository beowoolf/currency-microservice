package com.microservices.currencymicroservice.controller;

import com.microservices.currencymicroservice.exception.CurrencyNotFoundException;
import com.microservices.currencymicroservice.model.CurrencyApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class CurrencyRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CurrencyNotFoundException.class})
    public ResponseEntity<CurrencyApiError> handleCurrencyNotFoundException(CurrencyNotFoundException ex) {
        String message = ex.getMessage();
        log.error("ErrorMessage: {} , exceptionBody: {}", message, ex);
        final CurrencyApiError currencyApiError = new CurrencyApiError(HttpStatus.BAD_REQUEST, message, ex);
        return new ResponseEntity<>(currencyApiError, currencyApiError.getStatus());
    }

}
