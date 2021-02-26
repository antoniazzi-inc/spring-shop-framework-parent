package com.github.antoniazzi.inc.backend.commons.eh;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.violations.ConstraintViolationProblem;

import com.github.antoniazzi.inc.backend.commons.PropPrefix;

/**
 * Intercepts all Problems and Exceptions and handles them accordingly
 * 
 * @version 0.1.0
 * @since 27.09.2019
 * @author Kristijan Georgiev
 */
@ControllerAdvice
@ConditionalOnClass(ProblemHandling.class)
@ConditionalOnMissingBean(DynamicSecurityExceptionHandler.class)
@ConditionalOnProperty(value = PropPrefix.WPSOFT_ERROR_HANDLING_ENABLED, havingValue = "true", matchIfMissing = true)
public class DynamicExceptionHandler implements ProblemHandling {

	protected static final String EXCEPTION_KEY = "exception:";

	private static final String VALIDATIONS_KEY = "validations";

	/**
	 * Checks if problem is of defined type and returns a new ResponseEntity or proxies the call to Spring
	 *
	 */
	@Override
	public ResponseEntity<Problem> process(ResponseEntity<Problem> entity, NativeWebRequest request) {
		if (entity == null) {
			return entity;
		}

		Problem problem = entity.getBody();

		ProblemBuilder builder = Problem.builder();

		if (problem instanceof ConstraintViolationProblem) {
			builder.withType(buildUri(ConstraintViolationProblem.class)).with(VALIDATIONS_KEY, ((ConstraintViolationProblem) problem).getViolations());
		} else if (problem instanceof DynamicProblem) {
			builder.withType(buildUri(problem.getClass()));
		} else if (problem instanceof DefaultProblem) {
			typeBuilder(builder, problem);
		} else {
			return entity;
		}

		// @formatter:off
		builder.withTitle(problem.getTitle()).withStatus(problem.getStatus()).withDetail(problem.getDetail());
		// @formatter:on

		return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
	}

	/**
	 * Handles custom BadRequestException
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler
	public ResponseEntity<Problem> handleBadRequestException(BadRequestException ex, NativeWebRequest request) {
		return create(ex, request);
	}

	/**
	 * Handle DataIntegrityViolationException. ExceptionTranslation bean must be provided.
	 * 
	 * @param ex
	 * @param request
	 * @return
	 * @throws URISyntaxException
	 */
	@ExceptionHandler
	public ResponseEntity<Problem> handleDataIntegrityViolationException(DataIntegrityViolationException ex, NativeWebRequest request)
			throws URISyntaxException {

		String cause = "Check for unique constraints";

		Problem problem = Problem.builder().withTitle("Database Constraints Problem").withDetail(cause)
				.withType(new URI(EXCEPTION_KEY + DataIntegrityViolationException.class.getSimpleName())).withStatus(Status.BAD_REQUEST).build();
		return create(ex, problem, request);
	}

	/**
	 * Handle NoSuchElementException
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler
	public ResponseEntity<Problem> handleNoSuchElementException(NoSuchElementException ex, NativeWebRequest request) {
		Problem problem = Problem.builder().withStatus(Status.NOT_FOUND).build();
		return create(ex, problem, request);
	}

	/**
	 * Handle InvalidDataAccessApiUsageException
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler
	public ResponseEntity<Problem> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex, NativeWebRequest request) {
		Problem problem = Problem.builder().withStatus(Status.BAD_REQUEST).build();
		return create(ex, problem, request);
	}

	/**
	 * Handle InvalidDataAccessResourceUsageException
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler
	public ResponseEntity<Problem> handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException ex, NativeWebRequest request) {
		Problem problem = Problem.builder().withStatus(Status.BAD_REQUEST).build();
		return create(ex, problem, request);
	}

	/**
	 * Override NoHandlerFoundException with NotFoundException
	 */
	@Override
	@ExceptionHandler
	public ResponseEntity<Problem> handleNoHandlerFound(NoHandlerFoundException ex, NativeWebRequest request) {
		return create(ex, new NotFoundException(request), request);
	}

	/**
	 * Handle TransactionSystemException
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler
	public ResponseEntity<Problem> handleInvalidDataAccessApiUsageException(TransactionSystemException ex, NativeWebRequest request) {
		Problem problem = Problem.builder().withStatus(Status.BAD_REQUEST).build();
		return create(ex, problem, request);
	}

	/**
	 * Overrides generic URI from library
	 * 
	 * @param pb ProblemBuilder
	 * @param p  Problem
	 */
	private void typeBuilder(ProblemBuilder pb, Problem p) {
		if (p.getType().toString().startsWith(EXCEPTION_KEY)) {
			pb.withType(p.getType());
		} else {
			// GeneralAdviceTrait
			if (p.getStatus().equals(Status.NOT_IMPLEMENTED)) {
				pb.withType(buildUri(ExceptionConst.NOT_IMPLEMENTED_EX));
			}

			// HttpAdviceTrait
			if (p.getStatus().equals(Status.NOT_ACCEPTABLE)) {
				pb.withType(buildUri(ExceptionConst.NOT_ACCEPTABLE_EX));
			}

			if (p.getStatus().equals(Status.UNSUPPORTED_MEDIA_TYPE)) {
				pb.withType(buildUri(ExceptionConst.UNSUPPORTED_MEDIA_TYPE_EX));
			}

			if (p.getStatus().equals(Status.METHOD_NOT_ALLOWED)) {
				pb.withType(buildUri(ExceptionConst.METHOD_NOT_ALLOWED_EX));
			}

			// NetworkAdviceTrait
			if (p.getStatus().equals(Status.GATEWAY_TIMEOUT)) {
				pb.withType(buildUri(ExceptionConst.GATEWAY_TIMEOUT_EX));
			}

			// RoutingAdviceTrait
			if (p.getStatus().equals(Status.NOT_FOUND)) {
				pb.withType(buildUri(ExceptionConst.NOT_FOUND_EX));
			}

			// SecurityAdviceTrait
			if (p.getStatus().equals(Status.UNAUTHORIZED)) {
				pb.withType(buildUri(ExceptionConst.UNAUTHORIZED_EX));
			}

			if (p.getStatus().equals(Status.FORBIDDEN)) {
				pb.withType(buildUri(ExceptionConst.FORBIDDEN_EX));
			}

			// Other
			if (p.getStatus().equals(Status.BAD_REQUEST)) {
				pb.withType(buildUri(ExceptionConst.BAD_REQUEST_EX));
			}
		}
	}

	/**
	 * Builds URI with defined EXCEPTION_KEY as prefix
	 * 
	 * @param clazz exception class
	 * @return URI
	 */
	public static URI buildUri(Class<?> clazz) {
		return buildUri(clazz.getSimpleName());
	}

	/**
	 * Builds URI with defined EXCEPTION_KEY as prefix
	 * 
	 * @param string
	 * @return URI
	 */
	public static URI buildUri(String string) {
		try {
			return new URI(EXCEPTION_KEY + string);
		} catch (URISyntaxException e) {
			return null;
		}
	}

}
