package com.microservices.currencymicroservice.service;

import com.microservices.currencymicroservice.exception.CurrencyNotFoundException;
import com.microservices.currencymicroservice.model.CurrencyResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class SimpleCurrencyService implements CurrencyFacade {

    private static final Map<String, Double> currencies = new HashMap<>();

    public SimpleCurrencyService() {
        currencies.put("EUR", 4.55);
        currencies.put("USD", 5.55);
        currencies.put("PLN", 3.55);
    }

    @Override
    public CurrencyResponse getCurrencyValueByCurrencyCode(String code) throws CurrencyNotFoundException {
        String currencyCode = findCurrencyCodeInCurrencies(code)
                .orElseThrow(() -> new CurrencyNotFoundException(code));
        Double currencyValue = currencies.get(currencyCode);
        return new CurrencyResponse(currencyValue);
    }

    private Optional<String> findCurrencyCodeInCurrencies(String code) {
        return currencies.keySet()
                .stream()
                .filter(areCurrencyCodesEqual(code))
                .findFirst();
    }

    private Predicate<String> areCurrencyCodesEqual(String code) {
        return currency -> currency.equals(code);
    }

}
