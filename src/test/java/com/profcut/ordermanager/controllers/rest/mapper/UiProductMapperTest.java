package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.entities.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UiProductMapperTest {

    UiProductMapper mapper = Mappers.getMapper(UiProductMapper.class);

    @Test
    void shouldMapAllFields() {
        var product = new ProductEntity()
                .setProductId(UUID.randomUUID())
                .setProductName("productFromDb");

        var result = mapper.apply(product);

        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
        assertEquals(product.getProductName(), result.getProductName());
    }
}
