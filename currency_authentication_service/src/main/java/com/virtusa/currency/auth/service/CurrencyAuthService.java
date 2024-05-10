package com.virtusa.currency.auth.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.virtusa.currency.auth.dto.UserCredentialsDTO;
import com.virtusa.currency.auth.entity.UserCredentialsEntity;
import com.virtusa.currency.auth.repository.CurrencyAuthRepository;

@Service
@Scope (value = BeanDefinition.SCOPE_PROTOTYPE)
public class CurrencyAuthService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyAuthService.class);
	
	@Autowired
	private CurrencyAuthRepository currencyAuthRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
    private JwtTokenService jwtTokenService;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public String saveUser(UserCredentialsEntity userCredentialsEntity) {
		
		userCredentialsEntity.setPassword(passwordEncoder.encode(userCredentialsEntity.getPassword()));
		
		LOGGER.info("Authentication Input Details :"+userCredentialsEntity);
		try {
			Optional<UserCredentialsEntity> details = currencyAuthRepository.findByUserName(userCredentialsEntity.getUserName());
			UserCredentialsEntity dbUserCredentials = details.get();
			
			if(dbUserCredentials != null && !dbUserCredentials.getUserName().toUpperCase().equals(userCredentialsEntity.getUserName())) {
				
				LOGGER.info("User "+userCredentialsEntity.getUserName()+" is already exists!");
				
				return "User "+userCredentialsEntity.getUserName()+" is already exists!";
			}
		}catch(Exception e) {
			LOGGER.error("Details are not avaialable ind DB or Unable to fetch the existing details:"+e.toString());
		}
		currencyAuthRepository.save(userCredentialsEntity);
		LOGGER.info("User credentials saved successfully into database :"+userCredentialsEntity);
		return "User credentials saved successfully into database";
	}
	
	public String generateJwtToken(UserCredentialsDTO userCredentialsDTO) 
	{
		
		try {
			
			Authentication authenctication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentialsDTO.userName(), userCredentialsDTO.password()));
			LOGGER.info("Is authenticated User :"+authenctication.isAuthenticated());
			if(authenctication.isAuthenticated()) {
				String token = jwtTokenService.generateToken(userCredentialsDTO.userName());
				LOGGER.info("Generated JWT Token :"+token);
				return token;
			}else {
				LOGGER.warn("Invalid access, Bad credentials");
				return "Invalid access, Bad credentials";
			}
		}catch(Exception e) {
			System.out.println();
			LOGGER.error("Exception while generating token:"+e.toString());
			if(e.getMessage().contains("BadCredentialsException")) {
				LOGGER.error("Invalid access, Bad credentials");
				return "Invalid access, Bad credentials";
			}
			return "Invalid access, Bad credentials";
		}
		
	}
	
	public void validateJwtToken(String jwtToken) {
		jwtTokenService.validateJwtToken(jwtToken);
    }
	

}
