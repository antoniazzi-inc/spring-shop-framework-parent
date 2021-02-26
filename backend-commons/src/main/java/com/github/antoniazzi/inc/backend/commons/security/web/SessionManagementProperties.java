package com.github.antoniazzi.inc.backend.commons.security.web;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * Properties for SessionManagementConfigurer
 * 
 * @version 1.0.0
 * @since 05.10.2019
 * @author Kristijan Georgiev
 */
@Configuration
@ConfigurationProperties(prefix = PropPrefix.WPSOFT_SECURITY_WEB_SESSION_MANAGEMENT, ignoreUnknownFields = false)
public class SessionManagementProperties {

	private SessionCreationPolicy sessionCreationPolicy = SessionCreationPolicy.STATELESS;

	public SessionCreationPolicy getSessionCreationPolicy() {
		return sessionCreationPolicy;
	}

	public void setSessionCreationPolicy(SessionCreationPolicy sessionCreationPolicy) {
		this.sessionCreationPolicy = sessionCreationPolicy;
	}

}