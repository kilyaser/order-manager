package com.profcut.ordermanager.massaging.listener;

import com.profcut.ordermanager.domain.entities.OrderEntity;
import com.profcut.ordermanager.domain.entities.PaymentEntity;
import com.profcut.ordermanager.massaging.events.ChangePaymentEvent;
import com.profcut.ordermanager.massaging.listeners.PaymentChangeEventListener;
import com.profcut.ordermanager.service.OrderService;
import com.profcut.ordermanager.testData.utils.helper.TestDataHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentChangeEventListenerTest {

    @Mock
    OrderService orderService;
    @InjectMocks
    PaymentChangeEventListener listener;

    @Test
    @DisplayName("Добавление платежа.")
    void changePaymentProcess_addPayment() {
        var event = ChangePaymentEvent.of(UUID.randomUUID(), new PaymentEntity()
                        .setPaymentId(UUID.randomUUID()).setPaymentSum(BigDecimal.valueOf(5000)),
                false);
        var order = TestDataHelper.buildDefaultOrder();

        when(orderService.findOrderById(any())).thenReturn(order);

        assertThatCode(() -> listener.changePaymentProcess(event)).doesNotThrowAnyException();

        var captor = ArgumentCaptor.forClass(OrderEntity.class);

        verify(orderService).findOrderById(any(UUID.class));
        verify(orderService).saveOrder(captor.capture());

        assertThat(captor.getValue())
                .isInstanceOf(OrderEntity.class)
                .satisfies(o -> {
                    assertThat(o.getDebtSum()).isEqualTo(BigDecimal.ZERO);
                    assertThat(o.getPayments().size()).isEqualTo(2);
                });
    }

    @Test
    @DisplayName("Удаление платежа. Изменение суммы платежа")
    void changePaymentProcess_changeSum() {
        var order = TestDataHelper.buildDefaultOrder();
        var payment = order.getPayments().stream().findFirst().orElse(new PaymentEntity());
        var event = ChangePaymentEvent.of(UUID.randomUUID(), payment.setPaymentSum(BigDecimal.valueOf(5000)),
                false);

        when(orderService.findOrderById(any())).thenReturn(order);

        assertThat(order.getPayments().size()).isEqualTo(1);
        assertThatCode(() -> listener.changePaymentProcess(event)).doesNotThrowAnyException();

        var captor = ArgumentCaptor.forClass(OrderEntity.class);

        verify(orderService).findOrderById(any(UUID.class));
        verify(orderService).saveOrder(captor.capture());

        assertThat(captor.getValue())
                .isInstanceOf(OrderEntity.class)
                .satisfies(o -> {
                    assertThat(o.getDebtSum()).isEqualTo(order.getDebtSum());
                    assertThat(o.getPayments().size()).isEqualTo(1);
                    assertThat(o.getDebtSum()).isEqualTo(BigDecimal.valueOf(1000));
                });
    }

    @Test
    @DisplayName("Удаление платежа.")
    void changePaymentProcess_DeletePayment() {
        var order = TestDataHelper.buildDefaultOrder();
        var payment = order.getPayments().stream().findFirst().orElse(null);
        var event = ChangePaymentEvent.of(UUID.randomUUID(), payment,
                true);

        when(orderService.findOrderById(any())).thenReturn(order);

        assertThat(order.getPayments().size()).isEqualTo(1);
        assertThatCode(() -> listener.changePaymentProcess(event)).doesNotThrowAnyException();

        var captor = ArgumentCaptor.forClass(OrderEntity.class);

        verify(orderService).findOrderById(any(UUID.class));
        verify(orderService).saveOrder(captor.capture());

        assertThat(captor.getValue())
                .isInstanceOf(OrderEntity.class)
                .satisfies(o -> {
                    assertThat(o.getDebtSum()).isEqualTo(order.getDebtSum());
                    assertThat(o.getPayments().size()).isEqualTo(0);
                });
    }
}
