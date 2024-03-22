package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UiCounterpartyShortMapperTest {

    UiCounterpartyShortMapper mapper = Mappers.getMapper(UiCounterpartyShortMapper.class);

    @Test
    void shouldMapAllFields() {
        var counterparty = new CounterpartyEntity().setId(UUID.randomUUID())
                .setFullName("fullName")
                .setName("name")
                .setInn("1234567897");

        var result = mapper.apply(counterparty);

        assertNotNull(result);
        assertEquals(counterparty.getId(), result.getId());
        assertEquals(counterparty.getInn(), result.getInn());
        assertEquals(counterparty.getName(), result.getName());
    }
}
