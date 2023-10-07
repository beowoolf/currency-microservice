package com.microservices.currencymicroservice.service;

import com.microservices.currencymicroservice.model.CurrencyResponse;

public interface CurrencyService {

    CurrencyResponse getCurrencyValueByCurrencyCode(String code);

}
