package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.product.UiProduct;
import com.profcut.ordermanager.domain.entities.ProductEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper
public interface UiProductMapper extends Function<ProductEntity, UiProduct> {

    @Override
    UiProduct apply(ProductEntity entity);
}
