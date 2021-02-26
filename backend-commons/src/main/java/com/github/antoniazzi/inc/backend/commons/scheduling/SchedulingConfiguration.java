package com.github.antoniazzi.inc.backend.commons.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * General Scheduling Configuration Class
 * 
 * @version 1.0.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(value = PropPrefix.WPSOFT_SCHEDULING, havingValue = "true")
public class SchedulingConfiguration {

	private static final Logger log = LoggerFactory.getLogger(SchedulingConfiguration.class);

	static {
		log.info("Scheduling Enabled");
	}

}
