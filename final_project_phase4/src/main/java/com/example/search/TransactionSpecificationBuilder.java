package com.example.search;

import com.example.entity.Transaction;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TransactionSpecificationBuilder {

    private final List<SearchCriteria> params;

    public TransactionSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final TransactionSpecificationBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public final TransactionSpecificationBuilder with(SearchCriteria searchCriteria){
        params.add(searchCriteria);
        return this;
    }

    public Specification<Transaction> build(){
        if(params.size() == 0){
            return null;
        }

        Specification<Transaction> result = new TransactionSpecification(params.get(0));
        for (int idx = 1; idx < params.size(); idx++){
            SearchCriteria criteria = params.get(idx);
            result = SearchOperation.getDataOption(criteria.getDataOption()) == SearchOperation.ALL
                    ? Specification.where(result).and(new TransactionSpecification(criteria))
                    : Specification.where(result).or(new TransactionSpecification(criteria));
        }

        return result;
    }
}
