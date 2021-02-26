package com.github.antoniazzi.inc.backend.commons.eh;

import java.net.URISyntaxException;
import java.util.NoSuchElementException;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * Intercepts all Problems and Exceptions and handles them accordingly. Includes Spring Security
 * 
 * @version 0.1.0
 * @since 27.09.2019
 * @author Kristijan Georgiev
 */
@ControllerAdvice
@AutoConfigureBefore(DynamicExceptionHandler.class)
@ConditionalOnClass({ WebSecurityConfigurerAdapter.class, ProblemHandling.class })
@ConditionalOnProperty(value = PropPrefix.WPSOFT_ERROR_HANDLING_ENABLED, havingValue = "true", matchIfMissing = true)
public class DynamicSecurityExceptionHandler extends DynamicExceptionHandler implements SecurityAdviceTrait {

	@Override
	public ResponseEntity<Problem> process(ResponseEntity<Problem> entity, NativeWebRequest request) {
		return super.process(entity, request);
	}

	@Override
	public ResponseEntity<Problem> handleBadRequestException(BadRequestException ex, NativeWebRequest request) {
		return super.handleBadRequestException(ex, request);
	}

	@Override
	public ResponseEntity<Problem> handleDataIntegrityViolationException(DataIntegrityViolationException ex, NativeWebRequest request)
			throws URISyntaxException {
		return super.handleDataIntegrityViolationException(ex, request);
	}

	@Override
	public ResponseEntity<Problem> handleNoSuchElementException(NoSuchElementException ex, NativeWebRequest request) {
		return super.handleNoSuchElementException(ex, request);
	}

	@Override
	public ResponseEntity<Problem> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex, NativeWebRequest request) {
		return super.handleInvalidDataAccessApiUsageException(ex, request);
	}

	@Override
	public ResponseEntity<Problem> handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException ex, NativeWebRequest request) {
		return super.handleInvalidDataAccessResourceUsageException(ex, request);
	}

	@Override
	public ResponseEntity<Problem> handleNoHandlerFound(NoHandlerFoundException ex, NativeWebRequest request) {
		return super.handleNoHandlerFound(ex, request);
	}

}