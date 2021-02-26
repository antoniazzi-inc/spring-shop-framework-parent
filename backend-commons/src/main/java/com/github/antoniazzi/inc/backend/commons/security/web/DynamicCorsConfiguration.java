package com.github.antoniazzi.inc.backend.commons.security.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * Cors configuration class
 * 
 * @version 1.0.0
 * @since 05.10.2019
 * @author Kristijan Georgiev
 */
@Configuration
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
@ConditionalOnProperty(value = PropPrefix.WPSOFT_SECURITY_WEB_CORS_ENABLED, havingValue = "true", matchIfMissing = true)
public class DynamicCorsConfiguration {

	private static final Logger log = LoggerFactory.getLogger(DynamicCorsConfiguration.class);

	@Autowired
	private CorsProperties corsProperties;

	@Bean
	public CorsFilter corsFilter() {
		log.info("Registering CORS Filter");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		CorsConfiguration config = corsProperties.getConfiguration();

		if (corsProperties.getPaths() != null && !corsProperties.getPaths().isEmpty()) {
			corsProperties.getPaths().forEach(p -> {
				source.registerCorsConfiguration(p, config);
			});
		} else {
			source.registerCorsConfiguration("/**", config);
		}

		return new CorsFilter(source);
	}

}
