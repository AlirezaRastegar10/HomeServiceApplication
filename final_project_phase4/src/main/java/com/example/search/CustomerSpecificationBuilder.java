package com.example.search;

import com.example.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecificationBuilder {

    private final List<SearchCriteria> params;

    public CustomerSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final CustomerSpecificationBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public final CustomerSpecificationBuilder with(SearchCriteria searchCriteria){
        params.add(searchCriteria);
        return this;
    }

    public Specification<Customer> build(){
        if(params.size() == 0){
            return null;
        }

        Specification<Customer> result = new CustomerSpecification(params.get(0));
        for (int idx = 1; idx < params.size(); idx++){
            SearchCriteria criteria = params.get(idx);
            result = SearchOperation.getDataOption(criteria.getDataOption()) == SearchOperation.ALL
                    ? Specification.where(result).and(new CustomerSpecification(criteria))
                    : Specification.where(result).or(new CustomerSpecification(criteria));
        }

        return result;
    }
}
