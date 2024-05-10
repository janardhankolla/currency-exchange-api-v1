package com.virtusa.currency.auth.exception;

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
		LOGGER.error("user-authentication-service - Exception in Currency Authenticaton Service Details:"+e.getMessage());
        return ResponseEntity
                .badRequest()
                .body("user-authentication-service - Unable to process your request at this time. Kindly try after some time or contact support team... Error Details:"+e.getMessage());
    }

}
