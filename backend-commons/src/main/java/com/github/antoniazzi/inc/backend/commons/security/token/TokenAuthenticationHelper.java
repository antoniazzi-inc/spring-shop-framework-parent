package com.github.antoniazzi.inc.backend.commons.security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnBean(AuthenticationProvider.class)
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
public class TokenAuthenticationHelper {

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private AuthenticationManagerBuilder authenticationManagerBuilder;

	public ResponseEntity<TokenResponseDto> authorize(AbstractAuthenticationToken authenticationToken) {
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		TokenResponseDto response = tokenProvider.createToken(authentication);

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.add(TokenProvider.AUTHORIZATION_HEADER_KEY, "Bearer " + response.getAccessToken());

		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}

}
