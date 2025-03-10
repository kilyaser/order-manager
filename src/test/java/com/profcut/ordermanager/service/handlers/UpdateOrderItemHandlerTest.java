package com.profcut.ordermanager.service.handlers;

import com.profcut.ordermanager.controllers.rest.mapper.UiOrderItemMapper;
import com.profcut.ordermanager.domain.dto.order.OrderItemFieldsPatch;
import com.profcut.ordermanager.domain.dto.order.UpdateOrderItemRequest;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import com.profcut.ordermanager.service.OrderItemService;
import com.profcut.ordermanager.service.OrderService;
import com.profcut.ordermanager.service.validator.OrderItemFieldsPatchValidator;
import com.profcut.ordermanager.testData.utils.helper.TestDataHelper;
import jakarta.persistence.EntityManager;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.instancio.Select.field;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateOrderItemHandlerTest {

    @Mock
    OrderItemService orderItemService;
    @Mock
    OrderService orderService;
    @Mock
    EntityManager entityManager;
    @Mock
    UiOrderItemMapper mapper;
    @Mock
    OrderItemFieldsPatchValidator patchValidator;
    @InjectMocks
    UpdateOrderItemHandler updateOrderItemHandler;

    @Test
    @DisplayName("Обновление позиции заказа")
    void updateOrderItem() {
        var item = Instancio.of(OrderItemFieldsPatch.class)
                .set(field(OrderItemFieldsPatch::getQuantity), 5)
                .create();
        var request = new UpdateOrderItemRequest()
                .setOrderId(UUID.randomUUID())
                .setPatch(List.of(item));
        var order = TestDataHelper.buildDefaultOrder();

        when(orderService.findOrderById(request.getOrderId())).thenReturn(order);
        when(patchValidator.validate(any(OrderItemFieldsPatch.class))).thenReturn(item);

        assertThatCode(() -> updateOrderItemHandler.handle(request)).doesNotThrowAnyException();

        verify(orderService).findOrderById(any(UUID.class));
        verify(orderItemService).updateOrderItem(any(OrderItemFieldsPatch.class));
        verify(entityManager).refresh(any(OrderEntity.class));
        verify(patchValidator).validate(any());
        verify(mapper).apply(any());
    }
}
