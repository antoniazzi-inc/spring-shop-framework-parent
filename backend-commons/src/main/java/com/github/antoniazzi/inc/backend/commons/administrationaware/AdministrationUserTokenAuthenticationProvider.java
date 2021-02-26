package com.github.antoniazzi.inc.backend.commons.administrationaware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * User Authentication Provider for Custom Authentication
 * 
 * @version 1.0.0
 * @since 08.11.2019
 * @author Kristijan Georgiev
 */

@Component
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
@ConditionalOnProperty(value = PropPrefix.WPSOFT_ADMINISTRATION_AWARE_TOKEN_AUTHENTICATION_PROVIDER, havingValue = "true")
public class AdministrationUserTokenAuthenticationProvider implements AuthenticationProvider {

	private static final Logger log = LoggerFactory.getLogger(AdministrationUserTokenAuthenticationProvider.class);

	{
		log.info("Registering Administration User Token Authentication Provider");
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		AdministrationUserAuthentication administrationAwareUserAuthentication = (AdministrationUserAuthentication) authentication;

		if (administrationAwareUserAuthentication.isFromToken()) {
			return administrationAwareUserAuthentication;
		} else {
			throw new UsernameNotFoundException("");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(AdministrationUserAuthentication.class);
	}

}
