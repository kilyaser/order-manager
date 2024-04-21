package com.profcut.ordermanager.utils.jpa.specification;

import com.profcut.ordermanager.domain.entities.PaymentEntity;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

@UtilityClass
public class PaymentSpecification {

    public static Specification<PaymentEntity> byCounterpartyId(UUID counterpartyId) {
        return (root, query, criteriaBuilder) -> {
            var counterpartyJoin = root.join("counterparty");
            return criteriaBuilder.equal(counterpartyJoin.get("id"), counterpartyId);
        };
    }
}
