package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.testData.utils.helper.TestDataHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UiOrderShortMapperTest {

    UiOrderShortMapper mapper = Mappers.getMapper(UiOrderShortMapper.class);

    @Test
    void shouldMapAllFields() {
        var order = TestDataHelper.buildDefaultOrder();

        var result = mapper.apply(order);

        assertNotNull(result);
        assertEquals(order.getOrderId(), result.getOrderId());
        assertEquals(order.getOrderNumber(), result.getOrderNumber());
        assertEquals(order.getCurrentSum(), result.getCurrentSum());
        assertEquals(order.getOrderState(), result.getOrderState());
        assertEquals(order.getCompletionDate(), result.getCompletionDate());
        assertEquals(order.isGovernmentOrder(), result.isGovernmentOrder());
    }
}
