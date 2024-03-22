package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.product.CreateProductRequest;
import com.profcut.ordermanager.domain.entities.ProductEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper
public interface ProductCreateMapper extends Function<CreateProductRequest, ProductEntity> {

    @Override
    ProductEntity apply(CreateProductRequest createProductRequest);
}
