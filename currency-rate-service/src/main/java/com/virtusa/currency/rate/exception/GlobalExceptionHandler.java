package com.virtusa.currency.rate.exception;

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
		e.printStackTrace();
		LOGGER.error("Currency-Rate-Service Exception Details :"+e.toString());
		
        return ResponseEntity
                .badRequest()
                .body("Unable to process your request at this time. Kindly try after some time or contact support team :"+e.getMessage());
    }

}
