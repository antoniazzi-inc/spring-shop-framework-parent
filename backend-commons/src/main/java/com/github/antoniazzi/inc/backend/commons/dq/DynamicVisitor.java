package com.github.antoniazzi.inc.backend.commons.dq;

import org.springframework.data.jpa.domain.Specification;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;

/**
 * Checks Nodes and builds a Specification for each Node
 * 
 * @version 1.0.0
 * @since 28.09.2019
 * @author Kristijan Georgiev
 *
 * @param <T> The Class Type
 */
public class DynamicVisitor<T> implements RSQLVisitor<Specification<T>, Void> {

	private DynamicSpecificationBuilder<T> builder;

	public DynamicVisitor() {
		builder = new DynamicSpecificationBuilder<T>();
	}

	@Override
	public Specification<T> visit(AndNode node, Void param) {
		return builder.createSpecification(node);
	}

	@Override
	public Specification<T> visit(OrNode node, Void param) {
		return builder.createSpecification(node);
	}

	@Override
	public Specification<T> visit(ComparisonNode node, Void params) {
		return builder.createSpecification(node);
	}

}
