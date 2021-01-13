package com.luxoft.education.microservices.currencyconversionservice.controller;

import com.luxoft.education.microservices.currencyconversionservice.model.CurrencyConversionResult;
import com.luxoft.education.microservices.currencyconversionservice.service.CurrencyExchangeServiceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    public CurrencyConversionController(CurrencyExchangeServiceProxy currencyExchangeServiceProxy) {
        this.currencyExchangeServiceProxy = currencyExchangeServiceProxy;
    }

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionResult convertCurrency(@PathVariable String from,
                                                    @PathVariable String to,
                                                    @PathVariable BigDecimal quantity) {

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversionResult> responseEntity = new RestTemplate().
                getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionResult.class, uriVariables);
        CurrencyConversionResult response = responseEntity.getBody();
        return new CurrencyConversionResult(response.getId(), from, to, response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionResult convertCurrencyFeign(@PathVariable String from,
                                                    @PathVariable String to,
                                                    @PathVariable BigDecimal quantity) {

        CurrencyConversionResult response = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);
        logger.info("LOGGER: response: {}", response);
        return new CurrencyConversionResult(response.getId(), from, to, response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
    }

}
