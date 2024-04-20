package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UiCounterpartyMapperTest {

    UiCounterpartyMapper mapper = Mappers.getMapper(UiCounterpartyMapper.class);

    @Test
    void shouldMapAllFields() {
        var counterparty = new CounterpartyEntity()
                .setId(UUID.randomUUID())
                .setFullName("fullName")
                .setName("name")
                .setInn("1234567897")
                .setEmail("email");

        var result = mapper.apply(counterparty);

        assertNotNull(result);
        assertThat(counterparty).usingRecursiveComparison()
                .ignoringFields("orders")
                .isEqualTo(result);
    }
}
