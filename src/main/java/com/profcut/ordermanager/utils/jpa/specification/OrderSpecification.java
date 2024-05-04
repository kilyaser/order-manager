package com.profcut.ordermanager.utils.jpa.specification;

import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import jakarta.persistence.criteria.Join;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

@UtilityClass
public class OrderSpecification {

    public static Specification<OrderEntity> byCounterpartyId(UUID counterpartyId) {
        return (root, query, criteriaBuilder) -> {
            Join<OrderEntity, CounterpartyEntity> join = root.join("counterparty");
            return criteriaBuilder.equal(join.get("id"), counterpartyId);
        };
    }

    public static Specification<OrderEntity> byIsDeletedFalse() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("isDeleted"));
    }
}
