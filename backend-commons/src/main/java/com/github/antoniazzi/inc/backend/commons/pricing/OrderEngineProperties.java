package com.github.antoniazzi.inc.backend.commons.pricing;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * Error Handling Properties
 * 
 * @version 1.0.0
 * @since 05.10.2019
 * @author Kristijan Georgiev
 */
@Configuration
@ConfigurationProperties(prefix = PropPrefix.WPSOFT_ORDER_ENGINE, ignoreUnknownFields = false)
public class OrderEngineProperties {

	private Boolean enabled = true;

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
