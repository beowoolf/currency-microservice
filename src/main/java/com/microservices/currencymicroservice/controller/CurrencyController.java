package com.microservices.currencymicroservice.controller;

import com.microservices.currencymicroservice.model.CurrencyResponse;
import com.microservices.currencymicroservice.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/{code}")
    public CurrencyResponse getCurrencyValue(@PathVariable String code) {
        return currencyService.getCurrencyValueByCurrencyCode(code);
    }

}
