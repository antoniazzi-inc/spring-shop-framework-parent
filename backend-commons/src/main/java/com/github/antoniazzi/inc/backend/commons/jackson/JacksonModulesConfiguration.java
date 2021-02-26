package com.github.antoniazzi.inc.backend.commons.jackson;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.hppc.HppcModule;
import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * Registers Jackson Modules
 * 
 * @version 1.0.0
 * @since 28.09.2019
 * @author Kristijan Georgiev
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(JsonFactory.class)
@ConditionalOnProperty(value = PropPrefix.WPSOFT_JACKSON_MODULES_ENABLED, havingValue = "true", matchIfMissing = true)
public class JacksonModulesConfiguration {

	private static final Logger log = LoggerFactory.getLogger(JacksonModulesConfiguration.class);

	/**
	 * Hibernate5 Module
	 */
	@Configuration
	@ConditionalOnClass({ Hibernate.class, Hibernate5Module.class })
	@ConditionalOnProperty(value = PropPrefix.WPSOFT_JACKSON_MODULES_HIBERNATE5, havingValue = "true", matchIfMissing = true)
	public static class Hibernate5ModuleConfiguration {

		@Bean
		public Hibernate5Module hibernate5Module() {
			log.info("Registering Hibernate5 Module");

			return new Hibernate5Module();
		}
	}

	/**
	 * Optimized Serialization/Deserialization
	 */
	@Configuration
	@ConditionalOnClass(HppcModule.class)
	@ConditionalOnProperty(value = PropPrefix.WPSOFT_JACKSON_MODULES_HPPC, havingValue = "true", matchIfMissing = true)
	public static class HppcModuleConfiguration {

		@Bean
		public HppcModule hppcModule() {
			log.info("Registering Hppc Module");

			return new HppcModule();
		}
	}

	/**
	 * Default Problem Module
	 */
	@Configuration
	@ConditionalOnClass(ProblemModule.class)
	@ConditionalOnProperty(value = PropPrefix.WPSOFT_JACKSON_MODULES_PROBLEM, havingValue = "true", matchIfMissing = true)
	public static class ProblemModuleConfiguration {

		@Bean
		public ProblemModule problemModule() {
			log.info("Registering Problem Module");

			return new ProblemModule();
		}
	}

	/**
	 * Register ConstraintViolation Problem Module
	 */
	@Configuration
	@ConditionalOnClass(ConstraintViolationProblemModule.class)
	@ConditionalOnProperty(value = PropPrefix.WPSOFT_JACKSON_MODULES_CONSTRAINT_PROBLEM_VIOLATION, havingValue = "true", matchIfMissing = true)
	public static class ConstraintViolationProblemModuleConfiguration {

		@Bean
		public ConstraintViolationProblemModule constraintViolationProblemModule() {
			log.info("Registering Constraint Violation Problem Module");

			return new ConstraintViolationProblemModule();
		}
	}

}
