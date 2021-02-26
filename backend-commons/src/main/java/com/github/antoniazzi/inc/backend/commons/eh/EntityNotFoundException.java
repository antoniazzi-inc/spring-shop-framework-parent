package com.github.antoniazzi.inc.backend.commons.eh;

import org.zalando.problem.Status;

/**
 * Generic Entity Not Found Exception
 * 
 * @version 1.0.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 */
public class EntityNotFoundException extends DynamicProblem {

	public static final String TITLE = "Not Found";
	public static final String ID_KEY = "ID:";

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException() {
		super(TITLE, Status.NOT_FOUND);
	}

	public EntityNotFoundException(String details) {
		super(TITLE, Status.NOT_FOUND, ID_KEY + details);
	}

}
