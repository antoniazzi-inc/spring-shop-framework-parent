package com.github.antoniazzi.inc.backend.commons.jackson;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * Jackson Modules Properties
 * 
 * @version 1.0.0
 * @since 04.10.2019
 * @author Kristijan Georgiev
 */
@Configuration
@ConfigurationProperties(prefix = PropPrefix.WPSOFT_JACKSON_MODULES, ignoreUnknownFields = false)
public class JacksonModulesProperties {

	private Boolean enabled;

	private Boolean hibernate5;
	private Boolean hppc;
	private Boolean problem;
	private Boolean constraintViolationProblem;

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getHibernate5() {
		return hibernate5;
	}

	public void setHibernate5(Boolean hibernate5) {
		this.hibernate5 = hibernate5;
	}

	public Boolean getHppc() {
		return hppc;
	}

	public void setHppc(Boolean hppc) {
		this.hppc = hppc;
	}

	public Boolean getProblem() {
		return problem;
	}

	public void setProblem(Boolean problem) {
		this.problem = problem;
	}

	public Boolean getConstraintViolationProblem() {
		return constraintViolationProblem;
	}

	public void setConstraintViolationProblem(Boolean constraintViolationProblem) {
		this.constraintViolationProblem = constraintViolationProblem;
	}

}
