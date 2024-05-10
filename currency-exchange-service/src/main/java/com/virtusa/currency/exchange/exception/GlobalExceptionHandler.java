package com.virtusa.currency.exchange.exception;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler({Exception.class})
    protected ResponseEntity<String> handleException(Exception e, Locale locale) {
		LOGGER.error("Exception in Currency Exchange Service...Exception Details:"+e.getMessage());
        return ResponseEntity
                .badRequest()
                .body("Currency-Exchange-Service - Unable to process your request at this time. Kindly try after some time or contact support team... Error Details:"+e.getMessage());
    }
	
	

}
