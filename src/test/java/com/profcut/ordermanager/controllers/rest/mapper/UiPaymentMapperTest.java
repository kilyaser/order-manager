package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import com.profcut.ordermanager.domain.entities.PaymentEntity;
import com.profcut.ordermanager.testData.utils.helper.TestDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UiPaymentMapperTest {

    @Mock
    UiCounterpartyShortMapper uiCounterpartyShortMapper;
    @Mock
    UiOrderShortMapper uiOrderShortMapper;
    @InjectMocks
    UiPaymentMapperImpl mapper;

    @Test
    void shouldMapAllFields() {
        var payment = new PaymentEntity()
                .setPaymentId(UUID.randomUUID())
                .setCounterparty(new CounterpartyEntity()
                        .setId(UUID.randomUUID())
                        .setName("counterpartyName"))
                .setOrder(TestDataHelper.buildDefaultOrder())
                .setPaymentSum(BigDecimal.valueOf(15000000))
                .setPaymentDate(LocalDateTime.now())
                .setModifiedDate(LocalDateTime.now());

        var result = mapper.apply(payment);

        assertNotNull(result);
        assertThat(payment).usingRecursiveComparison()
                .ignoringFields("counterparty", "order")
                .isEqualTo(result);
    }
}
