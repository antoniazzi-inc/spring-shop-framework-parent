package com.github.antoniazzi.inc.backend.commons.administrationaware;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * Administration Aware Properties
 * 
 * @version 1.0.0
 * @since 27.09.2019
 * @author Kristijan Georgiev
 */
@Configuration
@ConfigurationProperties(prefix = PropPrefix.WPSOFT_ADMINISTRATION_AWARE, ignoreUnknownFields = false)
public class AdministrationAwareProperties {

	private Boolean aspect = false;
	private Boolean securityService = false;
	private Boolean tokenProvider = false;
	private Boolean authenticationProvider = false;
	private Boolean tokenAuthenticationProvider = false;
	private Boolean authenticationController = false;

	public Boolean getAspect() {
		return aspect;
	}

	public void setAspect(Boolean aspect) {
		this.aspect = aspect;
	}

	public Boolean getSecurityService() {
		return securityService;
	}

	public void setSecurityService(Boolean securityService) {
		this.securityService = securityService;
	}

	public Boolean getTokenProvider() {
		return tokenProvider;
	}

	public void setTokenProvider(Boolean tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	public Boolean getAuthenticationProvider() {
		return authenticationProvider;
	}

	public void setAuthenticationProvider(Boolean authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	public Boolean getTokenAuthenticationProvider() {
		return tokenAuthenticationProvider;
	}

	public void setTokenAuthenticationProvider(Boolean tokenAuthenticationProvider) {
		this.tokenAuthenticationProvider = tokenAuthenticationProvider;
	}

	public Boolean getAuthenticationController() {
		return authenticationController;
	}

	public void setAuthenticationController(Boolean authenticationController) {
		this.authenticationController = authenticationController;
	}

}