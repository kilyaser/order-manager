package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.entities.PaymentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UiPaymentShortMapperTest {

    @Mock
    UiCounterpartyShortMapper counterpartyShortMapper;
    @InjectMocks
    UiPaymentShortMapperImpl mapper ;

    @Test
    void shouldMapAllFields() {
        var payment = new PaymentEntity()
                .setPaymentId(UUID.randomUUID())
                .setPaymentSum(BigDecimal.valueOf(5000))
                .setPaymentDate(LocalDateTime.now());

        var result = mapper.apply(payment);

        assertNotNull(result);
        assertThat(payment).usingRecursiveComparison()
                .ignoringFields("counterparty", "order", "modifiedDate")
                .isEqualTo(result);
    }
}
