package com.example.search;

import com.example.entity.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class TransactionSpecification implements Specification<Transaction> {

    private final SearchCriteria searchCriteria;

    public TransactionSpecification(final SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Transaction> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {

        String strToSearch = searchCriteria.getValue().toString().toLowerCase();

        switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
            case CONTAINS:
                if (searchCriteria.getFilterKey().equals("subService")) {
                    return cb.like(cb.lower(subServiceJoin(root).get("name")), "%" + strToSearch + "%");
                }
                if (searchCriteria.getFilterKey().equals("service")) {
                    return cb.like(cb.lower(servicesJoin(root).get("name")), "%" + strToSearch + "%");
                }
                if (searchCriteria.getFilterKey().equals("customer")) {
                    return cb.like(cb.lower(customerJoin(root).get("email")), "%" + strToSearch + "%");
                }
                if (searchCriteria.getFilterKey().equals("expert")) {
                    return cb.like(cb.lower(expertJoin(root).get("email")), "%" + strToSearch + "%");
                }
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");

            case DOES_NOT_CONTAIN:
                if (searchCriteria.getFilterKey().equals("subService")) {
                    return cb.notLike(cb.lower(subServiceJoin(root).get("name")), "%" + strToSearch + "%");
                }
                if (searchCriteria.getFilterKey().equals("service")) {
                    return cb.notLike(cb.lower(servicesJoin(root).get("name")), "%" + strToSearch + "%");
                }
                if (searchCriteria.getFilterKey().equals("customer")) {
                    return cb.notLike(cb.lower(customerJoin(root).get("email")), "%" + strToSearch + "%");
                }
                if (searchCriteria.getFilterKey().equals("expert")) {
                    return cb.notLike(cb.lower(expertJoin(root).get("email")), "%" + strToSearch + "%");
                }
                return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");

            case BEGINS_WITH:
                if (searchCriteria.getFilterKey().equals("subService")) {
                    return cb.like(cb.lower(subServiceJoin(root).get("name")), strToSearch + "%");
                }
                if (searchCriteria.getFilterKey().equals("service")) {
                    return cb.like(cb.lower(servicesJoin(root).get("name")), strToSearch + "%");
                }
                if (searchCriteria.getFilterKey().equals("customer")) {
                    return cb.like(cb.lower(customerJoin(root).get("email")), strToSearch + "%");
                }
                if (searchCriteria.getFilterKey().equals("expert")) {
                    return cb.like(cb.lower(expertJoin(root).get("email")), strToSearch + "%");
                }
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), strToSearch + "%");

            case DOES_NOT_BEGIN_WITH:
                if (searchCriteria.getFilterKey().equals("subService")) {
                    return cb.notLike(cb.lower(subServiceJoin(root).get("name")), strToSearch + "%");
                }
                if (searchCriteria.getFilterKey().equals("service")) {
                    return cb.notLike(cb.lower(servicesJoin(root).get("name")), strToSearch + "%");
                }
                if (searchCriteria.getFilterKey().equals("customer")) {
                    return cb.notLike(cb.lower(customerJoin(root).get("email")), strToSearch + "%");
                }
                if (searchCriteria.getFilterKey().equals("expert")) {
                    return cb.notLike(cb.lower(expertJoin(root).get("email")), strToSearch + "%");
                }
                return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), strToSearch + "%");

            case ENDS_WITH:
                if (searchCriteria.getFilterKey().equals("subService")) {
                    return cb.like(cb.lower(subServiceJoin(root).get("name")), "%" + strToSearch);
                }
                if (searchCriteria.getFilterKey().equals("service")) {
                    return cb.like(cb.lower(servicesJoin(root).get("name")), "%" + strToSearch);
                }
                if (searchCriteria.getFilterKey().equals("customer")) {
                    return cb.like(cb.lower(customerJoin(root).get("email")), "%" + strToSearch);
                }
                if (searchCriteria.getFilterKey().equals("expert")) {
                    return cb.like(cb.lower(expertJoin(root).get("email")), "%" + strToSearch);
                }
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch);

            case DOES_NOT_END_WITH:
                if (searchCriteria.getFilterKey().equals("subService")) {
                    return cb.notLike(cb.lower(subServiceJoin(root).get("name")), "%" + strToSearch);
                }
                if (searchCriteria.getFilterKey().equals("service")) {
                    return cb.notLike(cb.lower(servicesJoin(root).get("name")), "%" + strToSearch);
                }
                if (searchCriteria.getFilterKey().equals("customer")) {
                    return cb.notLike(cb.lower(customerJoin(root).get("email")), "%" + strToSearch);
                }
                if (searchCriteria.getFilterKey().equals("expert")) {
                    return cb.notLike(cb.lower(expertJoin(root).get("email")), "%" + strToSearch);
                }
                return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch);

            case EQUAL:
                if (searchCriteria.getFilterKey().equals("subService")) {
                    System.out.println(searchCriteria.getValue());
                    return cb.equal(subServiceJoin(root).<String>get("name"), searchCriteria.getValue());
                }
                if (searchCriteria.getFilterKey().equals("service")) {
                    System.out.println(searchCriteria.getValue());
                    return cb.equal(servicesJoin(root).<String>get("name"), searchCriteria.getValue());
                }
                if (searchCriteria.getFilterKey().equals("customer")) {
                    System.out.println(searchCriteria.getValue());
                    return cb.equal(customerJoin(root).<String>get("email"), searchCriteria.getValue());
                }
                if (searchCriteria.getFilterKey().equals("expert")) {
                    System.out.println(searchCriteria.getValue());
                    return cb.equal(expertJoin(root).<String>get("email"), searchCriteria.getValue());
                }
                return cb.equal(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue());

            case NOT_EQUAL:
                if (searchCriteria.getFilterKey().equals("subService")) {
                    return cb.notEqual(subServiceJoin(root).<String>get("name"), searchCriteria.getValue());
                }
                if (searchCriteria.getFilterKey().equals("service")) {
                    return cb.notEqual(servicesJoin(root).<String>get("name"), searchCriteria.getValue());
                }
                if (searchCriteria.getFilterKey().equals("customer")) {
                    return cb.notEqual(customerJoin(root).<String>get("email"), searchCriteria.getValue());
                }
                if (searchCriteria.getFilterKey().equals("expert")) {
                    return cb.notEqual(expertJoin(root).<String>get("email"), searchCriteria.getValue());
                }
                return cb.notEqual(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue());

            case NUL:
                return cb.isNull(root.get(searchCriteria.getFilterKey()));

            case NOT_NULL:
                return cb.isNotNull(root.get(searchCriteria.getFilterKey()));

            case GREATER_THAN:
                return cb.greaterThan(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());

            case GREATER_THAN_EQUAL:
                return cb.greaterThanOrEqualTo(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());

            case LESS_THAN:
                return cb.lessThan(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());

            case LESS_THAN_EQUAL:
                return cb.lessThanOrEqualTo(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());

            case MAX:
                Subquery<Number> subquery = query.subquery(Number.class);
                Root<Transaction> subroot = subquery.from(Transaction.class);
                subquery.select(cb.max(subroot.get(searchCriteria.getFilterKey())));
                return cb.equal(root.get(searchCriteria.getFilterKey()), subquery);

            case MIN:
                Subquery<Number> subQuery = query.subquery(Number.class);
                Root<Transaction> subRoot = subQuery.from(Transaction.class);
                subQuery.select(cb.min(subRoot.get(searchCriteria.getFilterKey())));
                return cb.equal(root.get(searchCriteria.getFilterKey()), subQuery);

            case BETWEEN:
                String value = searchCriteria.getValue().toString();
                String[] str = value.split(",", 2);
                if (searchCriteria.getFilterKey().equals("registerDate")) {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime start = LocalDateTime.parse(str[0], dateTimeFormatter);
                    LocalDateTime end = LocalDateTime.parse(str[1], dateTimeFormatter);
                    return cb.between(root.get(searchCriteria.getFilterKey()), start, end);
                }
                return cb.between(root.get(searchCriteria.getFilterKey()), str[0], str[1]);
        }
        return null;
    }

    private Join<Transaction, SubService> subServiceJoin(Root<Transaction> root) {
        return root.join("subService");
    }

    private Join<Transaction, Services> servicesJoin(Root<Transaction> root) {
        return root.join("services");
    }

    private Join<Transaction, Expert> expertJoin(Root<Transaction> root) {
        return root.join("expert");
    }

    private Join<Transaction, Customer> customerJoin(Root<Transaction> root) {
        return root.join("customer");
    }
}
