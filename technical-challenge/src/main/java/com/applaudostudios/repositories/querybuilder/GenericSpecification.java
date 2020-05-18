package com.applaudostudios.repositories.querybuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.applaudostudios.util.SearchCriteria;

/**
 * GenericSpecification is a implementation from Specification interface
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
public class GenericSpecification<T> implements Specification<T> {

	private static final long serialVersionUID = 1L;

	private SearchCriteria criteria;

	public GenericSpecification(final SearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	public SearchCriteria getCriteria() {
		return criteria;
	}

	/**
	 * Creates a WHERE clause for a query of the referenced entity in form of a
	 * predicate for the given root and criteriaQuery.
	 *
	 * @param root            must not be null.
	 * @param query           must not be null.
	 * @param criteriaBuilder must not be null.
	 * @return a predicate, may be null.
	 */
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		Predicate predicate = null;
		switch (criteria.getOperation()) {
		case EQUALITY:
			predicate = criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
			break;
		case NEGATION:
			predicate = criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue());
			break;
		case GREATER_THAN:
			predicate = criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
			break;
		case LESS_THAN:
			predicate = criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
			break;
		case LIKE:
			predicate = criteriaBuilder.like(root.get(criteria.getKey()), criteria.getValue().toString());
			break;
		case STARTS_WITH:
			predicate = criteriaBuilder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
			break;
		case ENDS_WITH:
			predicate = criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
			break;
		case CONTAINS:
			predicate = criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
		}
		return predicate;
	}

}
