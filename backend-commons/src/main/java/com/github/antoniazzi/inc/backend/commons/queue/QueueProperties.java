package com.github.antoniazzi.inc.backend.commons.queue;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

@Configuration
@ConfigurationProperties(prefix = PropPrefix.WPSOFT_QUEUE, ignoreUnknownFields = true)
public class QueueProperties {

	private Boolean enabled;

	private Boolean aspect;

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getAspect() {
		return aspect;
	}

	public void setAspect(Boolean aspect) {
		this.aspect = aspect;
	}

}