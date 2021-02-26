package com.github.antoniazzi.inc.backend.commons.dq;

import java.util.List;

/**
 * Aggregate Data Definitions
 * 
 * @version 1.0.0
 * @since 02.10.2019
 * @author Kristijan Georgiev
 */
public class AggregateData {

	private final String operator;

	private final List<String> arguments;

	public AggregateData(String operator, List<String> arguments) {
		this.operator = operator;
		this.arguments = arguments;
	}

	public String getOperator() {
		return operator;
	}

	public List<String> getArguments() {
		return arguments;
	}

}
