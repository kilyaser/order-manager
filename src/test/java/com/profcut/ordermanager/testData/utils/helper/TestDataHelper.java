package com.profcut.ordermanager.testData.utils.helper;

import com.profcut.ordermanager.domain.dto.auth.AuthRequest;
import com.profcut.ordermanager.domain.dto.auth.AuthResponse;
import com.profcut.ordermanager.domain.dto.auth.OmUser;
import com.profcut.ordermanager.domain.dto.auth.RegisterRequest;
import com.profcut.ordermanager.domain.dto.machine.CreateMachineRequest;
import com.profcut.ordermanager.domain.dto.machine.UiMachine;
import com.profcut.ordermanager.domain.dto.order.CreateOrderRequest;
import com.profcut.ordermanager.domain.dto.order.OrderItemRequest;
import com.profcut.ordermanager.domain.dto.technologist.CreateTechnologistRequest;
import com.profcut.ordermanager.domain.entities.CncMachineEntity;
import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import com.profcut.ordermanager.domain.entities.MaterialEntity;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import com.profcut.ordermanager.domain.entities.PaymentEntity;
import com.profcut.ordermanager.domain.entities.ProductEntity;
import com.profcut.ordermanager.domain.enums.MachineType;
import com.profcut.ordermanager.domain.enums.OrderState;
import com.profcut.ordermanager.domain.enums.PreparationState;
import com.profcut.ordermanager.security.domain.model.entity.OmRoleEntity;
import com.profcut.ordermanager.security.domain.model.entity.OmUserEntity;
import com.profcut.ordermanager.security.domain.model.enums.OmRole;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import static com.profcut.ordermanager.domain.enums.ProductType.NEW;
import static com.profcut.ordermanager.security.domain.model.enums.OmRole.ADMIN;
import static com.profcut.ordermanager.security.domain.model.enums.OmRole.CEO;
import static com.profcut.ordermanager.security.domain.model.enums.OmRole.MANAGER;
import static com.profcut.ordermanager.security.domain.model.enums.OmRole.TECHNOLOGIST;

public class TestDataHelper {

    private static final Map<OmRole, OmRoleEntity> rolesMap = Map.of(
            ADMIN, new OmRoleEntity(UUID.randomUUID(), ADMIN, "admin"),
            MANAGER, new OmRoleEntity(UUID.randomUUID(), MANAGER, "manager"),
            CEO, new OmRoleEntity(UUID.randomUUID(), CEO, "ceo"),
            TECHNOLOGIST, new OmRoleEntity(UUID.randomUUID(), TECHNOLOGIST, "technologist")
    );

    public static RegisterRequest getDefaultRegisterRequest() {
        return RegisterRequest.builder()
                .firstName("firstName")
                .lastName("lastName")
                .email("test@mail.ru")
                .password("Password1")
                .build();
    }

    public static OmUser getDefaultOmUser(RegisterRequest request, UUID id) {
        return OmUser.builder()
                .id(id)
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .email(request.getEmail())
                .build();
    }

    public static OmUserEntity getDefaultOmUserEntity() {
        return new OmUserEntity()
                .setFirstName("firstName")
                .setLastName("lastName")
                .setPatronymic("patronymic")
                .setEmail("test@mail.ru")
                .setPhone("+11111111111");
    }

    public static Set<OmRoleEntity> getSelectedRole(OmRole role) {
        return Set.of(rolesMap.get(role));
    }

    public static AuthRequest getDefaultAuthRequest() {
        return AuthRequest.builder()
                .email("default@mail.ru")
                .password("Password1")
                .build();
    }

    public static AuthResponse getDefaultAuthResponse() {
        return AuthResponse.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
    }

    public static CncMachineEntity getDefaultMachine() {
        return new CncMachineEntity()
                .setId(UUID.randomUUID())
                .setMachineType(MachineType.THREE_AXIS)
                .setName("machineName");
    }

    public static CreateMachineRequest getDefaultCreateMachineRequest() {
        return new CreateMachineRequest()
                .setMachineType(MachineType.THREE_AXIS)
                .setName("machineName");
    }

    public static UiMachine getDefaultUiMachine() {
        return new UiMachine(
                UUID.randomUUID(),
                MachineType.THREE_AXIS,
                "machineName");
    }

    public static CounterpartyEntity buildDefaultCounterparty(UUID id) {
        return new CounterpartyEntity()
                .setId(id)
                .setFullName("fullName")
                .setName("name")
                .setInn("1234567897")
                .setEmail("email");
    }

    public static CreateTechnologistRequest getDefaultCreateTechnologistRequest() {
        return CreateTechnologistRequest.builder()
                .firstName("Name")
                .lastName("lastName")
                .patronymic("Patronymic")
                .email("test@email.ru")
                .phone("+711111")
                .build();
    }

    public static CreateOrderRequest getDefaultCreateOrderRequest() {
        return CreateOrderRequest.builder()
                .orderName("orderName")
                .isGovernmentOrder(false)
                .counterpartyId(UUID.randomUUID())
                .itemRequests(List.of(getDefaultOrderItemRequest()))
                .build();
    }

    public static OrderItemRequest getDefaultOrderItemRequest() {
        return OrderItemRequest.builder()
                .productId(UUID.randomUUID())
                .productType(NEW)
                .quantity(2)
                .pricePerProduct(BigDecimal.valueOf(3000))
                .totalPrice(BigDecimal.valueOf(6000))
                .isVatInclude(false)
                .isProgramWritten(false)
                .materialId(UUID.randomUUID())
                .preparationState(PreparationState.NOT_STARTED)
                .materialId(UUID.randomUUID())
                .technologistId(UUID.randomUUID())
                .build();
    }

    public static OrderItemEntity getDefaultOrderItem() {
        var item = new OrderItemEntity()
                .setId(UUID.randomUUID())
                .setProduct(new ProductEntity()
                        .setProductId(UUID.randomUUID())
                        .setProductName("ProductName"))
                .setQuantity(3)
                .setProductType(NEW)
                .setVatInclude(true)
                .setMachineId(UUID.randomUUID())
                .setPreparationState(PreparationState.NOT_STARTED)
                .setMaterial(new MaterialEntity()
                        .setId(UUID.randomUUID())
                        .setMaterialType("metal"))
                .setPricePerProduct(BigDecimal.valueOf(2000));
        item.calculateTotalPrice();
        item.calculateVat();
        return item;
    }

    public static OrderEntity buildDefaultOrder() {
        var items = new ArrayList<OrderItemEntity>();
        items.add(getDefaultOrderItem());
        var order = new OrderEntity()
                .setOrderId(UUID.randomUUID())
                .setOrderNumber("%s-%s".formatted(Year.now(), new Random().nextInt(100)))
                .setOrderName("test order")
                .setBillNumber("Счет №%s".formatted(new Random().nextInt(100)))
                .setOrderState(OrderState.NEW)
                .setOrderItems(items)
                .setVatInclude(true)
                .setCreatedDate(LocalDateTime.now())
                .recalculateCurrentSum();
        order.getPayments().add(new PaymentEntity()
                .setPaymentId(UUID.randomUUID())
                .setModifiedDate(LocalDateTime.now())
                .setPaymentSum(BigDecimal.valueOf(1000)));
        return order;
    }
}
