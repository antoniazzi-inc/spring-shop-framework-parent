package com.github.antoniazzi.inc.backend.commons.eh;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.StatusType;

/**
 * Dynamic Problem Class to be extended by all Custom Problems and Exceptions
 * 
 * @version 1.0.0
 * @since 25.09.2019
 * @author Kristijan Georgiev
 */
public class DynamicProblem extends AbstractThrowableProblem {

	private static final long serialVersionUID = 8292334939250367868L;

	public DynamicProblem(String title, StatusType status) {
		super(null, title, status);
	}

	public DynamicProblem(String title, StatusType status, String details) {
		super(null, title, status, details);
	}

}
