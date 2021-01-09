package com.luxoft.education.microservices.currencyconversionservice.service;

import com.luxoft.education.microservices.currencyconversionservice.model.CurrencyConversionResult;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-exchange-service", url = "localhost:8000")
public interface CurrencyExchangeServiceProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    CurrencyConversionResult retrieveExchangeValue(@PathVariable("from") String from,
                                                   @PathVariable("to") String to);

}