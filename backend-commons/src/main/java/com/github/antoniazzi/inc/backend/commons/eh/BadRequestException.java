package com.github.antoniazzi.inc.backend.commons.eh;

import org.zalando.problem.Status;

/**
 * Generic Bad Request Exception
 * 
 * @version 1.0.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 */
public class BadRequestException extends DynamicProblem {

	public static final String TITLE = "Bad Request";

	private static final long serialVersionUID = 1L;

	public BadRequestException() {
		super(TITLE, Status.BAD_REQUEST);
	}

	public BadRequestException(String details) {
		super(TITLE, Status.BAD_REQUEST, details);
	}

}
