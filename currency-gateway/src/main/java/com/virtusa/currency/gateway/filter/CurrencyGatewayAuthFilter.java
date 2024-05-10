package com.virtusa.currency.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.virtusa.currency.gateway.util.JwtTokenValidator;

@Component
public class CurrencyGatewayAuthFilter extends AbstractGatewayFilterFactory<CurrencyGatewayAuthFilter.Config> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyGatewayAuthFilter.class);

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtTokenValidator jwtTokenValidator;

    public CurrencyGatewayAuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
               
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                	LOGGER.warn("Missing authorization header");
                    throw new RuntimeException("Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                LOGGER.info("Header Token :"+authHeader);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                    LOGGER.info("Modified Header Token :"+authHeader);
                }
                try {
                	LOGGER.info("Validating Header Token :"+authHeader);
                	jwtTokenValidator.validateToken(authHeader);
                	LOGGER.info("Successfully validated header token :"+authHeader);
                } catch (Exception e) {
                    LOGGER.error("Exception while validating header token in filter level :"+e.toString());
                    throw new RuntimeException("Un authorized access to currency application service");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}

