package com.github.antoniazzi.inc.backend.commons.eh;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.web.servlet.DispatcherServlet;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * Exception Handling Configuration
 * 
 * @version 1.0.0
 * @since 06.10.2019
 * @author Kristijan Georgiev
 */
@Configuration
@ConditionalOnProperty(value = PropPrefix.WPSOFT_ERROR_HANDLING_ENABLED, havingValue = "true", matchIfMissing = true)
public class ExceptionHandlingConfiguration {

	/**
	 * Expose DataIntegrityViolationException
	 */
	@Configuration
	@ConditionalOnClass(PersistenceExceptionTranslationPostProcessor.class)
	public static class Hibernate5ModuleConfiguration {

		@Bean
		public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
			return new PersistenceExceptionTranslationPostProcessor();
		}

	}

	@Bean
	public DispatcherServlet dispatcherServlet() {
		DispatcherServlet dispatcherServlet = new DispatcherServlet();
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		return dispatcherServlet;
	}

}
