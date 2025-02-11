package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.counterparty.CreateCounterpartyRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CounterpartyCreateMapperTest {

    CounterpartyCreateMapper mapper = Mappers.getMapper(CounterpartyCreateMapper.class);

    @Test
    void shouldMapAllFields() {
        var request = CreateCounterpartyRequest.builder()
                .fullName("fullName")
                .name("name")
                .inn("1234567890")
                .email("test@mail.ru")
                .phone("+7521456789")
                .build();

        var result = mapper.apply(request);

        assertNotNull(result);
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("orders", "id", "contracts")
                .isEqualTo(request);
    }
}
