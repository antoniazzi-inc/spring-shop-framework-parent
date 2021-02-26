package com.github.antoniazzi.inc.backend.commons.dq;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.query.criteria.internal.path.PluralAttributePath;
import org.hibernate.query.criteria.internal.path.SingularAttributePath;
import org.springframework.data.jpa.domain.Specification;

import com.github.antoniazzi.inc.backend.commons.eh.BadRequestException;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;

/**
 * Builds a Dynamic Specification based on provided Query
 * 
 * @version 1.0.0
 * @since 28.09.2019
 * @author Kristijan Georgiev
 *
 * @param <T> The Class Type
 */
public class DynamicSpecification<T> implements Specification<T> {

	private static final long serialVersionUID = -7991958746215354321L;

	private String property;
	private ComparisonOperator operator;
	private List<String> arguments;
	private AggregateData aggregateData;

	public DynamicSpecification() {

	}

	public DynamicSpecification(String property, ComparisonOperator operator, List<String> arguments) {
		this.property = property;
		this.operator = operator;
		this.arguments = arguments;
	}

	public DynamicSpecification(String property, ComparisonOperator operator, List<String> arguments, AggregateData aggregateData) {
		this.property = property;
		this.operator = operator;
		this.arguments = arguments;
		this.aggregateData = aggregateData;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public ComparisonOperator getOperator() {
		return operator;
	}

	public void setOperator(ComparisonOperator operator) {
		this.operator = operator;
	}

	public List<String> getArguments() {
		return arguments;
	}

	public void setArguments(List<String> arguments) {
		this.arguments = arguments;
	}

	public AggregateData getAggregateData() {
		return aggregateData;
	}

	public void setAggregateData(AggregateData aggregateData) {
		this.aggregateData = aggregateData;
	}

	/**
	 * Builds and returns a Predicate based on the property names, values and the operators used
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Predicate toPredicate(Root<T> r, CriteriaQuery<?> query, CriteriaBuilder builder) {
		String aggregateOperator = null;

		if (aggregateData != null) {
			if (DynamicOperators.SUM.equals(aggregateData.getOperator())) {
				if (!property.contains(".")) {
					throw new BadRequestException();
				}

				if (!(parseProperty(property.substring(0, property.lastIndexOf('.')), r) instanceof PluralAttributePath)) {
					throw new BadRequestException();
				}

				aggregateOperator = aggregateData.getOperator();
			}
		}

		Path<String> root = parseProperty(property, r);

		if (operator.equals(DynamicOperators.NULL)) {
			if (Boolean.valueOf(arguments.get(0).toString())) {
				return builder.isNull(root);
			} else {
				return builder.isNotNull(root);
			}
		} else if (operator.equals(DynamicOperators.EMPTY)) {
			Expression e = pluralOrRoot(root);

			if (Boolean.valueOf(arguments.get(0).toString())) {
				return builder.isEmpty(e);
			} else {
				return builder.isNotEmpty(e);
			}
		}

		List<Object> args = castArguments(root);
		Object arg = args.get(0);

		if (operator.equals(DynamicOperators.EQUAL)) {
			if (DynamicOperators.SUM.equals(aggregateOperator)) {
				query.where(builder.equal(sumSubquery(root, r, query, builder), castNumber(arg)));
				query.distinct(true);
				return query.getRestriction();
			} else if (arg instanceof String) {
				return builder.like(root, arg.toString().replace('*', '%'));
			} else {
				return builder.equal(root, arg);
			}
		} else if (operator.equals(DynamicOperators.NOT_EQUAL)) {
			if (DynamicOperators.SUM.equals(aggregateOperator)) {
				query.where(builder.notEqual(sumSubquery(root, r, query, builder), castNumber(arg)));
				query.distinct(true);
				return query.getRestriction();
			} else if (arg instanceof String) {
				return builder.notLike(root, arg.toString().replace('*', '%'));
			} else {
				return builder.notEqual(root, arg);
			}
		} else if (operator.equals(DynamicOperators.GREATER_THAN)) {
			if (DynamicOperators.SUM.equals(aggregateOperator)) {
				query.where(builder.gt(sumSubquery(root, r, query, builder), castNumber(arg)));
				query.distinct(true);
				return query.getRestriction();
			} else if (arg instanceof Instant) {
				return builder.greaterThan(root.as(Instant.class), (Instant) arg);
			} else if (arg instanceof ZonedDateTime) {
				return builder.greaterThan(root.as(ZonedDateTime.class), (ZonedDateTime) arg);
			} else if (arg instanceof LocalDate) {
				return builder.greaterThan(root.as(LocalDate.class), (LocalDate) arg);
			} else if (root.getJavaType().equals(Set.class)) {
				return builder.gt(builder.size(root.as(Set.class)), Long.parseLong(arg.toString()));
			} else if (root.getJavaType().equals(List.class)) {
				return builder.gt(builder.size(root.as(List.class)), Long.parseLong(arg.toString()));
			} else if (root.getJavaType().equals(Collection.class)) {
				return builder.gt(builder.size(root.as(Collection.class)), Long.parseLong(arg.toString()));
			} else if (arg instanceof String) {
				return builder.gt(builder.length(root.as(String.class)), Long.parseLong(arg.toString()));
			} else {
				return builder.greaterThan(root, arg.toString());
			}
		} else if (operator.equals(DynamicOperators.GREATER_THAN_OR_EQUAL)) {
			if (DynamicOperators.SUM.equals(aggregateOperator)) {
				query.where(builder.ge(sumSubquery(root, r, query, builder), castNumber(arg)));
				query.distinct(true);
				return query.getRestriction();
			} else if (arg instanceof Instant) {
				return builder.greaterThanOrEqualTo(root.as(Instant.class), (Instant) arg);
			} else if (arg instanceof LocalDate) {
				return builder.greaterThanOrEqualTo(root.as(LocalDate.class), (LocalDate) arg);
			} else if (arg instanceof ZonedDateTime) {
				return builder.greaterThanOrEqualTo(root.as(ZonedDateTime.class), (ZonedDateTime) arg);
			} else if (root.getJavaType().equals(Set.class)) {
				return builder.ge(builder.size(root.as(Set.class)), Long.parseLong(arg.toString()));
			} else if (root.getJavaType().equals(List.class)) {
				return builder.ge(builder.size(root.as(List.class)), Long.parseLong(arg.toString()));
			} else if (root.getJavaType().equals(Collection.class)) {
				return builder.ge(builder.size(root.as(Collection.class)), Long.parseLong(arg.toString()));
			} else if (arg instanceof String) {
				return builder.ge(builder.length(root.as(String.class)), Long.parseLong(arg.toString()));
			} else {
				return builder.greaterThanOrEqualTo(root, arg.toString());
			}
		} else if (operator.equals(DynamicOperators.LESS_THAN)) {
			if (DynamicOperators.SUM.equals(aggregateOperator)) {
				query.where(builder.lt(sumSubquery(root, r, query, builder), castNumber(arg)));
				query.distinct(true);
				return query.getRestriction();
			} else if (arg instanceof Instant) {
				return builder.lessThan(root.as(Instant.class), (Instant) arg);
			} else if (arg instanceof LocalDate) {
				return builder.lessThan(root.as(LocalDate.class), (LocalDate) arg);
			} else if (arg instanceof ZonedDateTime) {
				return builder.lessThan(root.as(ZonedDateTime.class), (ZonedDateTime) arg);
			} else if (root.getJavaType().equals(Set.class)) {
				return builder.lt(builder.size(root.as(Set.class)), Long.parseLong(arg.toString()));
			} else if (root.getJavaType().equals(List.class)) {
				return builder.lt(builder.size(root.as(List.class)), Long.parseLong(arg.toString()));
			} else if (root.getJavaType().equals(Collection.class)) {
				return builder.lt(builder.size(root.as(Collection.class)), Long.parseLong(arg.toString()));
			} else if (arg instanceof String) {
				return builder.lt(builder.length(root.as(String.class)), Long.parseLong(arg.toString()));
			} else {
				return builder.lessThan(root, arg.toString());
			}
		} else if (operator.equals(DynamicOperators.LESS_THAN_OR_EQUAL)) {
			if (DynamicOperators.SUM.equals(aggregateOperator)) {
				query.where(builder.le(sumSubquery(root, r, query, builder), castNumber(arg)));
				query.distinct(true);
				return query.getRestriction();
			} else if (arg instanceof Instant) {
				return builder.lessThanOrEqualTo(root.as(Instant.class), (Instant) arg);
			} else if (arg instanceof LocalDate) {
				return builder.lessThanOrEqualTo(root.as(LocalDate.class), (LocalDate) arg);
			} else if (arg instanceof ZonedDateTime) {
				return builder.lessThanOrEqualTo(root.as(ZonedDateTime.class), (ZonedDateTime) arg);
			} else if (root.getJavaType().equals(Set.class)) {
				return builder.le(builder.size(root.as(Set.class)), Long.parseLong(arg.toString()));
			} else if (root.getJavaType().equals(List.class)) {
				return builder.le(builder.size(root.as(List.class)), Long.parseLong(arg.toString()));
			} else if (root.getJavaType().equals(Collection.class)) {
				return builder.le(builder.size(root.as(Collection.class)), Long.parseLong(arg.toString()));
			} else if (arg instanceof String) {
				return builder.le(builder.length(root.as(String.class)), Long.parseLong(arg.toString()));
			} else {
				return builder.lessThanOrEqualTo(root, arg.toString());
			}
		} else if (operator.equals(DynamicOperators.IN)) {
			return root.in(args);
		} else if (operator.equals(DynamicOperators.NOT_IN)) {
			return builder.not(root.in(args));
		}

		return null;
	}

	/**
	 * Parses property from query. If it contains ".", the property will be split to multiple sub properties, chaining joins
	 * on the way
	 * 
	 * @param root
	 * @return path
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Path<String> parseProperty(String property, Root<?> root) {
		Path<String> path;
		if (property.contains(".")) {
			String[] pathSteps = property.split("\\.");
			String step = pathSteps[0];

			path = root.get(step);
			From<?, ?> lastFrom = root;

			for (int i = 1; i <= pathSteps.length - 1; i++) {
				if (path instanceof PluralAttributePath) {
					PluralAttribute<?, ?, ?> attr = ((PluralAttributePath<?>) path).getAttribute();
					Join<?, ?> join = getJoin(attr, lastFrom);
					path = join.get(pathSteps[i]);
					lastFrom = join;
				} else if (path instanceof SingularAttributePath) {
					SingularAttribute attr = ((SingularAttributePath<?>) path).getAttribute();
					if (attr.getPersistentAttributeType() != Attribute.PersistentAttributeType.BASIC) {
						Join<?, ?> join = lastFrom.join(attr, JoinType.LEFT);
						path = join.get(pathSteps[i]);
						lastFrom = join;
					} else {
						path = path.get(pathSteps[i]);
					}
				} else {
					path = path.get(pathSteps[i]);
				}
			}
		} else {
			path = root.get(property);
		}
		return path;
	}

	/**
	 * Checks for collections mappings and returns a Collection Type accordingly
	 * 
	 * @param attr
	 * @param from
	 * @return Join
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Join<?, ?> getJoin(PluralAttribute<?, ?, ?> attr, From<?, ?> from) {
		switch (attr.getCollectionType()) {
		case SET:
			return from.join((SetAttribute) attr);
		case LIST:
			return from.join((ListAttribute) attr);
		case MAP:
			return from.join((MapAttribute) attr);
		case COLLECTION:
			return from.join((CollectionAttribute) attr);
		default:
			return null;
		}
	}

	/**
	 * Checks for specific types and returns a parsed value accordingly
	 * 
	 * @param propertyExpression name of property in the object
	 * @return arguments
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Object> castArguments(final Path<?> propertyExpression) {
		Class<? extends Object> type = propertyExpression.getJavaType();

		List<Object> args = arguments.stream().map(arg -> {
			if (type.equals(Boolean.class)) {
				return Boolean.valueOf(arg);
			} else if (type.equals(Byte.class)) {
				return Byte.parseByte(arg);
			} else if (type.equals(Short.class)) {
				return Short.parseShort(arg);
			} else if (type.equals(Integer.class)) {
				return Integer.parseInt(arg);
			} else if (type.equals(Long.class)) {
				return Long.parseLong(arg);
			} else if (type.equals(Float.class)) {
				return Float.parseFloat(arg);
			} else if (type.equals(Double.class)) {
				return Double.parseDouble(arg);
			} else if (type.equals(BigDecimal.class)) {
				return new BigDecimal(arg);
			} else if (type.equals(Character.class)) {
				if (arg.length() > 1) {
					return arg;
				}
				return arg.charAt(0);
			} else if (type.isEnum()) {
				return (Enum) Enum.valueOf((Class<Enum>) type, arg);
			} else if (type.equals(Instant.class)) {
				return Instant.parse(arg);
			} else if (type.equals(LocalDate.class)) {
				return LocalDate.parse(arg);
			} else if (type.equals(ZonedDateTime.class)) {
				return ZonedDateTime.parse(arg);
			} else {
				return arg;
			}
		}).collect(Collectors.toList());

		return args;
	}

	@SuppressWarnings("rawtypes")
	private Expression pluralOrRoot(Path<String> root) {
		Expression e = null;

		if (root.getJavaType().equals(List.class)) {
			e = root.as(List.class);
		} else if (root.getJavaType().equals(Set.class)) {
			e = root.as(Set.class);
		} else if (root.getJavaType().equals(Map.class)) {
			e = root.as(Map.class);
		} else if (root.getJavaType().equals(Collection.class)) {
			e = root.as(Collection.class);
		} else {
			e = root;
		}

		return e;
	}

	private Subquery<? extends Number> sumSubquery(Path<String> root, Root<T> r, CriteriaQuery<?> query, CriteriaBuilder builder) {
		Subquery<? extends Number> sq;

		if (root.getJavaType().equals(Byte.class)) {
			sq = query.subquery(Byte.class).select(builder.sum(root.as(Byte.class)));
		} else if (root.getJavaType().equals(Short.class)) {
			sq = query.subquery(Short.class).select(builder.sum(root.as(Short.class)));
		} else if (root.getJavaType().equals(Integer.class)) {
			sq = query.subquery(Integer.class).select(builder.sum(root.as(Integer.class)));
		} else if (root.getJavaType().equals(Long.class)) {
			sq = query.subquery(Long.class).select(builder.sum(root.as(Long.class)));
		} else if (root.getJavaType().equals(Float.class)) {
			sq = query.subquery(Float.class).select(builder.sum(root.as(Float.class)));
		} else if (root.getJavaType().equals(Double.class)) {
			sq = query.subquery(Double.class).select(builder.sum(root.as(Double.class)));
		} else if (root.getJavaType().equals(BigDecimal.class)) {
			sq = query.subquery(BigDecimal.class).select(builder.sum(root.as(BigDecimal.class)));
		} else {
			throw new BadRequestException();
		}

		String rField = null;

		if (aggregateData.getArguments().size() == 2) {
			aggregateData.getArguments().get(1);
		}

		String sqField = aggregateData.getArguments().get(0);

		Root<?> sr = sq.from(root.getParentPath().getJavaType());

		sq.where(builder.equal(parseProperty(sqField, sr), rField == null ? r : parseProperty(rField, r)));

		return sq;
	}

	private Number castNumber(Object obj) {
		if (obj.getClass().equals(Byte.class)) {
			return Byte.valueOf(obj.toString());
		} else if (obj.getClass().equals(Short.class)) {
			return Short.valueOf(obj.toString());
		} else if (obj.getClass().equals(Integer.class)) {
			return Integer.valueOf(obj.toString());
		} else if (obj.getClass().equals(Long.class)) {
			return Long.valueOf(obj.toString());
		} else if (obj.getClass().equals(Float.class)) {
			return Float.valueOf(obj.toString());
		} else if (obj.getClass().equals(Double.class)) {
			return Double.valueOf(obj.toString());
		} else if (obj.getClass().equals(BigDecimal.class)) {
			return new BigDecimal(obj.toString());
		} else {
			throw new BadRequestException();
		}
	}

}
