package com.github.antoniazzi.inc.backend.commons.eh;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Status;

/**
 * Generic Not Found Exception
 * 
 * @version 1.0.0
 * @since 03.10.2019
 * @author Kristijan Georgiev
 */
public class NotFoundException extends DynamicProblem {

	public static final String TITLE = "Not Found";
	public static final String DETAILS_PREFIX = "No handler found for ";

	private static final long serialVersionUID = 1L;

	public NotFoundException() {
		super(TITLE, Status.NOT_FOUND);
	}

	public NotFoundException(NativeWebRequest request) {
		super(TITLE, Status.NOT_FOUND, DETAILS_PREFIX + request.getNativeRequest(HttpServletRequest.class).getMethod() + " "
				+ request.getNativeRequest(HttpServletRequest.class).getRequestURI());
	}

}
