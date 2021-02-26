package com.github.antoniazzi.inc.backend.commons.dq;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import com.github.antoniazzi.inc.backend.commons.eh.BadRequestException;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.LogicalOperator;
import cz.jirutka.rsql.parser.ast.Node;

/**
 * Dynamic Specification Builder
 * 
 * @version 1.0.0
 * @since 28.09.2019
 * @author Kristijan Georgiev
 *
 * @param <T> The Class Type
 */
public class DynamicSpecificationBuilder<T> {

	public Specification<T> createSpecification(Node node) {
		if (node instanceof LogicalNode) {
			return createSpecification((LogicalNode) node);
		}
		if (node instanceof ComparisonNode) {
			return createSpecification((ComparisonNode) node);
		}
		return null;
	}

	public Specification<T> createSpecification(LogicalNode logicalNode) {
		List<Specification<T>> specs = logicalNode.getChildren().stream().map(node -> createSpecification(node)).filter(Objects::nonNull)
				.collect(Collectors.toList());

		Specification<T> result = specs.get(0);
		if (logicalNode.getOperator() == LogicalOperator.AND) {
			for (int i = 1; i < specs.size(); i++) {
				result = Specification.where(result).and(specs.get(i));
			}
		} else if (logicalNode.getOperator() == LogicalOperator.OR) {
			for (int i = 1; i < specs.size(); i++) {
				result = Specification.where(result).or(specs.get(i));
			}
		}

		return result;
	}

	public Specification<T> createSpecification(ComparisonNode comparisonNode) {
		String selector = comparisonNode.getSelector();

		if (selector.contains("|")) {
			String[] aggregates = selector.split("\\|");

			if (aggregates[0].equals(DynamicOperators.SUM)) {
				if (!(comparisonNode.getOperator().equals(DynamicOperators.EQUAL) || comparisonNode.getOperator().equals(DynamicOperators.NOT_EQUAL)
						|| comparisonNode.getOperator().equals(DynamicOperators.GREATER_THAN)
						|| comparisonNode.getOperator().equals(DynamicOperators.GREATER_THAN_OR_EQUAL)
						|| comparisonNode.getOperator().equals(DynamicOperators.LESS_THAN)
						|| comparisonNode.getOperator().equals(DynamicOperators.LESS_THAN_OR_EQUAL))) {
					throw new BadRequestException();
				}

				if (!(aggregates.length == 3 || aggregates.length == 4)) {
					throw new BadRequestException();
				}

				List<String> arguments = new ArrayList<>();

				for (int i = 1; i < aggregates.length - 1; i++) {
					arguments.add(aggregates[i]);
				}

				return Specification.where(new DynamicSpecification<T>(aggregates[aggregates.length - 1], comparisonNode.getOperator(),
						comparisonNode.getArguments(), new AggregateData(DynamicOperators.SUM, arguments)));
			} else {
				throw new BadRequestException();
			}
		}

		return Specification.where(new DynamicSpecification<T>(selector, comparisonNode.getOperator(), comparisonNode.getArguments()));
	}
}