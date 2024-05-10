package com.virtusa.currency.rate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.currency.rate.enums.CurrencyEnum;
import com.virtusa.currency.rate.service.CurrencyRateService;

@RestController
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class CurrencyRateController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyRateController.class);
	
	@Autowired
	private CurrencyRateService currencyRateService;
	
	@GetMapping(path="/api/rates/{source_currency}/{target_currency}",
			consumes = MediaType.ALL_VALUE,
			produces = MediaType.ALL_VALUE)
	
	public String getCurrencyDetails(@PathVariable(name = "source_currency") CurrencyEnum sourceCurrency,
			@PathVariable(name = "target_currency") CurrencyEnum targetCurrency) {
		
		LOGGER.info("Input SourceCurrency :"+sourceCurrency+" TargetCurrency:"+targetCurrency);
		
		String response = currencyRateService.getCurrencyRates(sourceCurrency,targetCurrency);
		
		LOGGER.info("Currency-Rate-Service Final response :"+response);
		
		return response;
	}

}
