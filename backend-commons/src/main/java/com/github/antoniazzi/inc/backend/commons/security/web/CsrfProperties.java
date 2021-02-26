package com.github.antoniazzi.inc.backend.commons.security.web;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * CSRF related properties. Enables with defaults and disables for now.
 * 
 * @version 1.0.0
 * @since 05.10.2019
 * @author Kristijan Georgiev
 */
@Configuration
@ConfigurationProperties(prefix = PropPrefix.WPSOFT_SECURITY_WEB_CSRF, ignoreUnknownFields = false)
public class CsrfProperties {

	private Boolean enabled = true;

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}