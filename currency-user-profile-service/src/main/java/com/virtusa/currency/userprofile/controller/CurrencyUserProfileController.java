package com.virtusa.currency.userprofile.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.currency.userprofile.entity.CurrencyRateDetailsEntity;
import com.virtusa.currency.userprofile.service.CurrencyUserProfileService;

@RestController
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class CurrencyUserProfileController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyUserProfileController.class);
	
	@Autowired
	private CurrencyUserProfileService currencyUserProfileService;
	
	@GetMapping(value="/api/users/{user_id}")
	public List<CurrencyRateDetailsEntity> getUserProfiles(@PathVariable(name="user_id") String userName) {
		LOGGER.info("Currency-UserProfile-Service UserName :"+userName);
		return currencyUserProfileService.getCurrencyUserProfileDetails(userName);
	}

}
