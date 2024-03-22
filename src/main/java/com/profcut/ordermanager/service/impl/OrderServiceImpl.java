package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.controllers.rest.mapper.OrderItemCreateMapper;
import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.dto.order.CreateOrderRequest;
import com.profcut.ordermanager.domain.dto.order.OrderItemRequest;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import com.profcut.ordermanager.domain.exceptions.OrderNotFoundException;
import com.profcut.ordermanager.domain.repository.OrderRepository;
import com.profcut.ordermanager.security.service.CurrentUserSecurityService;
import com.profcut.ordermanager.service.CounterpartyService;
import com.profcut.ordermanager.service.OrderService;
import com.profcut.ordermanager.service.ProductService;
import com.profcut.ordermanager.service.TechnologistService;
import com.profcut.ordermanager.utils.jpa.specification.PageConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.UUID;

import static com.profcut.ordermanager.domain.enums.MasterStatus.CREATED;
import static com.profcut.ordermanager.domain.enums.OrderState.NEW;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemCreateMapper orderItemCreateMapper;
    private final CurrentUserSecurityService currentUserSecurityService;
    private final TechnologistService technologistService;
    private final ProductService productService;
    private final CounterpartyService counterpartyService;

    @Override
    @Transactional
    public OrderEntity createOrder(CreateOrderRequest request) {
        log.info("invoke OrderServiceImpl#createOrder with request: {}", request);
        var order = new OrderEntity()
                .setOrderNumber(getNextOrderNumber())
                .setOrderName(ofNullable(request.getOrderName()).orElse(""))
                .setWorkFolderLink(request.getWorkFolderLink())
                .setCompletionDate(request.getCompletionDate())
                .setGovernmentOrder(request.isGovernmentOrder())
                .setOrderItems(request.getOrderItemRequest().stream()
                        .map(this::createOrderItem).toList())
                .setOrderState(NEW)
                .setMasterStatus(CREATED)
                .setCompletionDate(request.getCompletionDate())
                .setCounterparty(counterpartyService.findById(request.getCounterpartyId()))
                .setAuthor(currentUserSecurityService.getLogin())
                .recalculateCurrentSum();
        //TODO: Реализовать формироание старторвых задач при сохнарении засаза
        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderEntity> getOrdersPage(PageRequest request) {
        var pageable = PageConverter.covertToPageable(request);
        return orderRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderEntity findOrderById(UUID orderId) {
        return orderRepository.findOrderById(orderId).orElseThrow(() -> OrderNotFoundException.byOrderId(orderId));
    }

    @Override
    @Transactional
    public void deleteOrderById(UUID orderId) {
        log.info("invoke OrderServiceImpl#deleteOrderById with id: {}", orderId);
        var order = findOrderById(orderId);
        order.setDeleted(true);
        orderRepository.save(order);
    }

    private OrderItemEntity createOrderItem(OrderItemRequest itemRequest) {
        var itemEntity = orderItemCreateMapper.apply(itemRequest);
        var technologist = ofNullable(itemRequest.getTechnologistId()).map(technologistService::getById).orElse(null);
        itemEntity.setProduct(productService.getProductById(itemRequest.getProductId()));
        itemEntity.setTechnologist(technologist);
        itemEntity.calculateTotalPrice();
        itemEntity.calculateVat();
        return itemEntity;
    }

    private String getNextOrderNumber() {
        var nexNumber = orderRepository.getOrderNumbers() + 1;
        var year = Year.now().getValue();
        return "Заказ №%d-%d".formatted(year, nexNumber);
    }
}
