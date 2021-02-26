package com.github.antoniazzi.inc.backend.commons.dq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cz.jirutka.rsql.parser.RSQLParser;

/**
 * Configuration Class for the Dynamic Queries
 * 
 * @version 1.0.0
 * @since 04.10.2019
 * @author Kristijan Georgiev
 */
@Configuration
public class DynamicQueriesConfiguration {

	private static final Logger log = LoggerFactory.getLogger(DynamicQueriesConfiguration.class);

	@Bean
	public RSQLParser dynamicParser() {
		log.info("Registering Dynamic Query Parser");

		return new RSQLParser(DynamicOperators.defaultOperators());
	}

}
