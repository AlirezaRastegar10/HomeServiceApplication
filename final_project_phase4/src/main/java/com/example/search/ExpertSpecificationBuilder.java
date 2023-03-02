package com.example.search;

import com.example.entity.Expert;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ExpertSpecificationBuilder {

    private final List<SearchCriteria> params;

    public ExpertSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final ExpertSpecificationBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public final ExpertSpecificationBuilder with(SearchCriteria searchCriteria){
        params.add(searchCriteria);
        return this;
    }

    public Specification<Expert> build(){
        if(params.size() == 0){
            return null;
        }

        Specification<Expert> result = new ExpertSpecification(params.get(0));
        for (int idx = 1; idx < params.size(); idx++){
            SearchCriteria criteria = params.get(idx);
            result = SearchOperation.getDataOption(criteria.getDataOption()) == SearchOperation.ALL
                    ? Specification.where(result).and(new ExpertSpecification(criteria))
                    : Specification.where(result).or(new ExpertSpecification(criteria));
        }

        return result;
    }
}
