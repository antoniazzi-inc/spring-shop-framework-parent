package com.github.antoniazzi.inc.backend.commons.security.web;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * Http Basic Authentication related properties. Enables and disables defaults
 * for now.
 * 
 * It defaults to disabled.
 * 
 * @version 1.0.0
 * @since 05.10.2019
 * @author Kristijan Georgiev
 */
@Configuration
@ConfigurationProperties(prefix = PropPrefix.WPSOFT_SECURITY_WEB_HTTP_BASIC_AUTH, ignoreUnknownFields = false)
public class HttpBasicAuthProperties {

	private Boolean enabled = false;

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}