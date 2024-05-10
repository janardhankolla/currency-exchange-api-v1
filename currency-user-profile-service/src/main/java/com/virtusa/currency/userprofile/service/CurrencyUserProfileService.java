package com.virtusa.currency.userprofile.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.virtusa.currency.userprofile.entity.CurrencyRateDetailsEntity;
import com.virtusa.currency.userprofile.repository.CurrencyUserProfileRepository;

@Service
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class CurrencyUserProfileService {
	
	private static final Logger logger = LoggerFactory.getLogger(CurrencyUserProfileService.class);
	
	@Autowired
	private CurrencyUserProfileRepository currencyUserProfileRepository;
	
	public List<CurrencyRateDetailsEntity> getCurrencyUserProfileDetails(String userName) {
		
		List<CurrencyRateDetailsEntity> list = new ArrayList<>();
		try {
			list = currencyUserProfileRepository.findByUserName(userName);
			logger.info("Currnecy details from database UserName :"+userName+" No of transactions :"+list.size()+" Response List Details :"+list);
		} catch (Exception e) {
			logger.info("Exception while getting getting user profile details:"+e.toString()); 
		}
		
		return list;
		
	}

}
