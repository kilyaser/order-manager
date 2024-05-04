package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.testData.utils.helper.TestDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UiOrderMapperTest {

    @Mock
    UiCounterpartyShortMapper uiCounterpartyShortMapper;
    @Mock
    UiPaymentShortMapper uiPaymentShortMapper;
    @Mock
    UiOrderItemMapper uiOrderItemMapper;
    @InjectMocks
    UiOrderMapperImpl mapper;


    @Test
    void shouldMapAllFields() {
        var order = TestDataHelper.buildDefaultOrder();

        var result = mapper.apply(order);

        assertNotNull(result);
        assertThat(order).usingRecursiveComparison()
                .ignoringFields("orderItems", "payments", "tasks", "isDeleted", "masterStatus")
                .isEqualTo(result);
    }
}