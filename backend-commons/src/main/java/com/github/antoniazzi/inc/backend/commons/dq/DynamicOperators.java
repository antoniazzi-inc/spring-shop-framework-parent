package com.github.antoniazzi.inc.backend.commons.dq;

import static java.util.Arrays.asList;

import java.util.Set;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;

/**
 * Additional Operators Definitions
 * 
 * @version 1.0.0
 * @since 02.10.2019
 * @author Kristijan Georgiev
 */
public abstract class DynamicOperators extends RSQLOperators {

	public static final ComparisonOperator NULL = new ComparisonOperator("=null=", false);
	public static final ComparisonOperator EMPTY = new ComparisonOperator("=empty=", false);

	public static final String AVG = "avg";
	public static final String MAX = "max";
	public static final String MIN = "min";
	public static final String SUM = "sum";

	public static Set<ComparisonOperator> defaultOperators() {
		Set<ComparisonOperator> operators = RSQLOperators.defaultOperators();
		operators.addAll(asList(NULL, EMPTY));
		return operators;
	}

}
