package com.microservices.currencymicroservice.service;

import com.microservices.currencymicroservice.model.CurrencyResponse;

public interface CurrencyFacade {

    CurrencyResponse getCurrencyValueByCurrencyCode(String code);

}
