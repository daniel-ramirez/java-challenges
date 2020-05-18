package com.applaudostudios.repositories.querybuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.jpa.domain.Specification;

import com.applaudostudios.util.SearchCriteria;
import com.applaudostudios.util.SearchOperation;

/**
 * GenericSpecificationsBuilder is a implementation from Specification interface
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
public class GenericSpecificationsBuilder<U> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final List<SearchCriteria> params;

	public GenericSpecificationsBuilder() {
		params = new ArrayList<>();
	}

	public final GenericSpecificationsBuilder<U> with(SearchCriteria criteria) {
		params.add(criteria);
		return this;
	}

	public final GenericSpecificationsBuilder<U> with(GenericSpecification<U> spec) {
		params.add(spec.getCriteria());
		return this;
	}

	public final GenericSpecificationsBuilder<U> with(final String key, final String operation, final Object value,
			final String prefix, final String suffix) {
		return with(null, key, operation, value, prefix, suffix);
	}

	public final GenericSpecificationsBuilder<U> with(final String orPredicate, final String key,
			final String operation, final Object value, final String prefix, final String suffix) {
		SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
		if (op != null) {
			if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
				final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
				final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

				if (startWithAsterisk && endWithAsterisk) {
					op = SearchOperation.CONTAINS;
				} else if (startWithAsterisk) {
					op = SearchOperation.ENDS_WITH;
				} else if (endWithAsterisk) {
					op = SearchOperation.STARTS_WITH;
				}
			}
			params.add(new SearchCriteria(orPredicate, key, op, value));
		}
		return this;
	}

	public Specification<U> build() {

		if (params.isEmpty()) {
			return null;
		}

		Specification<U> result = new GenericSpecification<U>(params.get(0));

		for (int i = 1; i < params.size(); i++) {
			result = params.get(i).isOrPredicate()
					? Specification.where(result).or(new GenericSpecification<U>(params.get(i)))
					: Specification.where(result).and(new GenericSpecification<U>(params.get(i)));
		}

		return result;
	}

	/**
	 * Creates a specification of WHERE clause for a query of the referenced entity .
	 *
	 * @param postFixedExprStack must not be null.
	 * @param converter          must not be null.
	 * @return a specification.
	 */
	public Specification<U> build(Deque<?> postFixedExprStack, Function<SearchCriteria, Specification<U>> converter) {

		Deque<Specification<U>> specStack = new LinkedList<>();

		Collections.reverse((List<?>) postFixedExprStack);

		while (!postFixedExprStack.isEmpty()) {
			Object mayBeOperand = postFixedExprStack.pop();

			if (!(mayBeOperand instanceof String)) {
				specStack.push(converter.apply((SearchCriteria) mayBeOperand));
			} else {
				Specification<U> operand1 = specStack.pop();
				Specification<U> operand2 = specStack.pop();
				if (mayBeOperand.equals(SearchOperation.AND_OPERATOR)) {
					specStack.push(Specification.where(operand1).and(operand2));
				} else if (mayBeOperand.equals(SearchOperation.OR_OPERATOR)) {
					specStack.push(Specification.where(operand1).or(operand2));
				}
			}

		}
		return specStack.pop();

	}

}
