package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.order.OrderItemRequest;
import com.profcut.ordermanager.domain.enums.MachineType;
import com.profcut.ordermanager.domain.enums.PreparationState;
import com.profcut.ordermanager.domain.enums.ProductType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class OrderItemCreateMapperTest {

    OrderItemCreateMapper mapper = Mappers.getMapper(OrderItemCreateMapper.class);

    @Test
    void shouldMapAllFields() {
        var orderItemReq = OrderItemRequest.builder()
                .productId(UUID.randomUUID())
                .productType(ProductType.NEW)
                .quantity(2)
                .pricePerProduct(BigDecimal.valueOf(1000))
                .totalPrice(BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(1000)))
                .isProgramWritten(true)
                .machineType(MachineType.FIVE_AXIS)
                .preparationState(PreparationState.NOT_STARTED)
                .completionDate(null)
                .materialId(UUID.randomUUID())
                .technologistId(UUID.randomUUID())
                .build();

        var result = mapper.apply(orderItemReq);

        assertNotNull(result);
        assertThat(orderItemReq).usingRecursiveComparison()
                .ignoringFields("technologistId", "productId", "materialId")
                .isEqualTo(result);
    }
}
