package com.example.search;

import com.example.entity.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ExpertSpecification implements Specification<Expert> {

    private final SearchCriteria searchCriteria;

    public ExpertSpecification(final SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Expert> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {

        String strToSearch = searchCriteria.getValue().toString().toLowerCase();

        switch(Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))){
            case CONTAINS:
                if(searchCriteria.getFilterKey().equals("subService")){
                    return cb.like(cb.lower(subServiceJoin(root).get("name")), "%" + strToSearch + "%");
                }
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");

            case DOES_NOT_CONTAIN:
                if(searchCriteria.getFilterKey().equals("subService")){
                    return cb.notLike(cb.lower(subServiceJoin(root).get("name")), "%" + strToSearch + "%");
                }
                return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");

            case BEGINS_WITH:
                if(searchCriteria.getFilterKey().equals("subService")){
                    return cb.like(cb.lower(subServiceJoin(root).get("name")), strToSearch + "%");
                }
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), strToSearch + "%");

            case DOES_NOT_BEGIN_WITH:
                if(searchCriteria.getFilterKey().equals("subService")){
                    return cb.notLike(cb.lower(subServiceJoin(root).get("name")), strToSearch + "%");
                }
                return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), strToSearch + "%");

            case ENDS_WITH:
                if(searchCriteria.getFilterKey().equals("subService")){
                    return cb.like(cb.lower(subServiceJoin(root).get("name")), "%" + strToSearch);
                }
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch);

            case DOES_NOT_END_WITH:
                if(searchCriteria.getFilterKey().equals("subService")){
                    return cb.notLike(cb.lower(subServiceJoin(root).get("name")), "%" + strToSearch);
                }
                return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch);

            case EQUAL:
                if(searchCriteria.getFilterKey().equals("subService")){
                    System.out.println(searchCriteria.getValue());
                    return cb.equal(subServiceJoin(root).<String>get("name"), searchCriteria.getValue());
                }
                if (searchCriteria.getFilterKey().equals("registerDate")) {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
                    LocalDateTime localDateTime = LocalDateTime.parse(searchCriteria.getValue().toString(), dateTimeFormatter);
                    return cb.equal(root.get(searchCriteria.getFilterKey()), localDateTime);
                }
                return cb.equal(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue());

            case NOT_EQUAL:
                if(searchCriteria.getFilterKey().equals("subService")){
                    return cb.notEqual(subServiceJoin(root).<String>get("name"), searchCriteria.getValue() );
                }
                if (searchCriteria.getFilterKey().equals("registerDate")) {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
                    LocalDateTime localDateTime = LocalDateTime.parse(searchCriteria.getValue().toString(), dateTimeFormatter);
                    return cb.notEqual(root.get(searchCriteria.getFilterKey()), localDateTime);
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
                Root<Expert> subroot = subquery.from(Expert.class);
                subquery.select(cb.max(subroot.get(searchCriteria.getFilterKey())));
                return cb.equal(root.get(searchCriteria.getFilterKey()), subquery);

            case MIN:
                Subquery<Number> subQuery = query.subquery(Number.class);
                Root<Expert> subRoot = subQuery.from(Expert.class);
                subQuery.select(cb.min(subRoot.get(searchCriteria.getFilterKey())));
                return cb.equal(root.get(searchCriteria.getFilterKey()), subQuery);

            case COUNT:
                if (searchCriteria.getFilterKey().equals("order")) {
                    query.groupBy(root.get("id"));
                    Join<Expert, Transaction> expertTransactionJoin = root.join("transactions", JoinType.LEFT);
                    Expression<Long> orderCountExpression = cb.count(expertTransactionJoin.get("id"));
                    query.having(cb.equal(orderCountExpression, searchCriteria.getValue()));
                    return cb.conjunction();
                }
        }
        return null;
    }

    private Join<Expert, SubService> subServiceJoin(Root<Expert> root){
        return root.join("subServiceSet");
    }
}
