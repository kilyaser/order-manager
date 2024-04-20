package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.product.CreateProductRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ProductCreateMapperTest {

    ProductCreateMapper mapper = Mappers.getMapper(ProductCreateMapper.class);

    @Test
    void shouldMapAllFields() {
        var request = new CreateProductRequest("newProduct");

        var result = mapper.apply(request);

        assertNotNull(result);
        assertEquals(request.getProductName(), result.getProductName());
    }
}
