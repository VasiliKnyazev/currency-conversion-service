package com.luxoft.education.microservices.currencyconversionservice.service;

import com.luxoft.education.microservices.currencyconversionservice.model.CurrencyConversionResult;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//use without ribbon
//@FeignClient(name = "currency-exchange-service", url = "localhost:8000")
//use with ribbon, but without zuul
//@FeignClient(name = "currency-exchange-service")
//use with ribbon and with zuul
@FeignClient(name = "netflix-zuul-api-gateway-server")
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

    //use with or without ribbon and without zuul
    //@GetMapping("/currency-exchange/from/{from}/to/{to}")
    //use with ribbon and with zuul
    @GetMapping("currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    CurrencyConversionResult retrieveExchangeValue(@PathVariable("from") String from,
                                                   @PathVariable("to") String to);

}
