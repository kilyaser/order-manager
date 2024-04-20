package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.entities.MaterialEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UiMaterialMapperTest {

    UiMaterialMapper mapper = Mappers.getMapper(UiMaterialMapper.class);

    @Test
    void shouldMapAllFields() {
        var id = UUID.randomUUID();
        var type = "MaterialDb";
        var entity = new MaterialEntity().setId(id).setMaterialType(type);

        var result = mapper.apply(entity);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(type, result.getMaterialType());
    }
}
