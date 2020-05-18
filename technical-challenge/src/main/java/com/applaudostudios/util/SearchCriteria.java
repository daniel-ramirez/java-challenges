package com.applaudostudios.util;

import java.io.Serializable;

/**
 * Is a class that contain search criteria by field where key is field name,
 * value is the search value, orPredicate define an OR condition and operation
 * is the operation type like equal, grated than, etc.
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
public class SearchCriteria implements Serializable {

	private static final long serialVersionUID = 1L;

	private String key;

	private SearchOperation operation;

	private Object value;

	private boolean orPredicate;

	public SearchCriteria() {
	}

	public SearchCriteria(final String key, final SearchOperation operation, final Object value) {
		super();
		this.key = key;
		this.operation = operation;
		this.value = value;
	}

	public SearchCriteria(final String orPredicate, final String key, final SearchOperation operation,
			final Object value) {
		super();
		this.orPredicate = orPredicate != null && orPredicate.equals(SearchOperation.OR_PREDICATE_FLAG);
		this.key = key;
		this.operation = operation;
		this.value = value;
	}

	public SearchCriteria(String key, String operation, String prefix, String value, String suffix) {
		SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
		if (op != null && op == SearchOperation.EQUALITY) { // the operation may be complex operation
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
		this.key = key;
		this.operation = op;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public SearchOperation getOperation() {
		return operation;
	}

	public void setOperation(final SearchOperation operation) {
		this.operation = operation;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public boolean isOrPredicate() {
		return orPredicate;
	}

	public void setOrPredicate(boolean orPredicate) {
		this.orPredicate = orPredicate;
	}

}