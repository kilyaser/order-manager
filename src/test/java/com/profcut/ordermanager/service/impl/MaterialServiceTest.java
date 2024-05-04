package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.domain.entities.MaterialEntity;
import com.profcut.ordermanager.domain.exceptions.MaterialNotFoundException;
import com.profcut.ordermanager.domain.repository.MaterialRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MaterialServiceTest {

    @Mock
    MaterialRepository materialRepository;
    @InjectMocks
    MaterialServiceImpl materialService;

    @Test
    @DisplayName("Получение материала по id. Успех")
    void findById() {
        var id = UUID.randomUUID();
        var entity = new MaterialEntity().setId(id).setMaterialType("медь");

        when(materialRepository.findById(id)).thenReturn(Optional.ofNullable(entity));

        assertThatCode(() -> {
            var result = materialService.findById(id);
            assertNotNull(result);
            assertEquals(id, result.getId());
        }).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Получение материала по id. Ошибка получения")
    void findById_exception() {
        var id = UUID.randomUUID();

        when(materialRepository.findById(id)).thenReturn(Optional.empty());

        assertThatCode(() -> materialService.findById(id)).isInstanceOf(MaterialNotFoundException.class);
    }
}
