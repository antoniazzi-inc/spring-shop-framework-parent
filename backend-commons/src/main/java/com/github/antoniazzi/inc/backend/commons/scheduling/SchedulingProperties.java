package com.github.antoniazzi.inc.backend.commons.scheduling;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * Properties under "wpsoft" namespace in .yml files
 * 
 * @author Kristijan Georgiev
 * 
 */
@Configuration
@ConfigurationProperties(prefix = PropPrefix.WPSOFT, ignoreUnknownFields = true)
public class SchedulingProperties {

	private Boolean scheduling = true;

	public Boolean getScheduling() {
		return scheduling;
	}

	public void setScheduling(Boolean scheduling) {
		this.scheduling = scheduling;
	}

}
