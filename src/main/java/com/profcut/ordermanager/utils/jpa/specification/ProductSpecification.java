package com.profcut.ordermanager.utils.jpa.specification;

import com.profcut.ordermanager.domain.entities.ProductEntity;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class ProductSpecification {

    public static Specification<ProductEntity> byProductNameLike(String productNamePart) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("productName"), "%%%s%%".formatted(productNamePart));
    }
}
