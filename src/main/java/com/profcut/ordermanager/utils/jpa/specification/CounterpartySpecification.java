package com.profcut.ordermanager.utils.jpa.specification;

import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class CounterpartySpecification {

    public static Specification<CounterpartyEntity> byCounterpartyNameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                "%%" + name.toLowerCase() + "%%");
    }
}
