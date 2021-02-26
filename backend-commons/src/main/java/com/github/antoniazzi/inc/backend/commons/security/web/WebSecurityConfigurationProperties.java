package com.github.antoniazzi.inc.backend.commons.security.web;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * General Security Properties
 * 
 * @version 1.0.0
 * @since 27.09.2019
 * @author Kristijan Georgiev
 */
@Configuration
@ConfigurationProperties(prefix = PropPrefix.WPSOFT_SECURITY_WEB, ignoreUnknownFields = true)
public class WebSecurityConfigurationProperties {

	private Boolean autoConfiguration = true;

	public Boolean getAutoConfiguration() {
		return autoConfiguration;
	}

	public void setAutoConfiguration(Boolean autoConfiguration) {
		this.autoConfiguration = autoConfiguration;
	}

}