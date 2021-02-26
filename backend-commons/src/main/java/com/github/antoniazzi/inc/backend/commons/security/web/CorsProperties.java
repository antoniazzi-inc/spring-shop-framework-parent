package com.github.antoniazzi.inc.backend.commons.security.web;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * CORS properties from .yml
 * 
 * If enabled and no configuration specified, defaults to default xD
 * 
 * @version 1.0.0
 * @since 05.10.2019
 * @author Kristijan Georgiev
 */
@Configuration
@ConfigurationProperties(prefix = PropPrefix.WPSOFT_SECURITY_WEB_CORS, ignoreUnknownFields = false)
public class CorsProperties {

	private Boolean enabled = true;

	private List<String> paths;

	private CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<String> getPaths() {
		return paths;
	}

	public void setPaths(List<String> paths) {
		this.paths = paths;
	}

	public CorsConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(CorsConfiguration configuration) {
		this.configuration = configuration;
	}

}
