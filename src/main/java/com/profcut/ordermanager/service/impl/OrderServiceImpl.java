package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.controllers.rest.mapper.UpdateOrderByPatchMapper;
import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.dto.order.CreateOrderRequest;
import com.profcut.ordermanager.domain.dto.order.UpdateOrderRequest;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import com.profcut.ordermanager.domain.enums.OrderState;
import com.profcut.ordermanager.domain.exceptions.OrderNotFoundException;
import com.profcut.ordermanager.domain.repository.OrderRepository;
import com.profcut.ordermanager.security.service.CurrentUserSecurityService;
import com.profcut.ordermanager.service.CounterpartyService;
import com.profcut.ordermanager.service.OrderItemService;
import com.profcut.ordermanager.service.OrderService;
import com.profcut.ordermanager.utils.jpa.specification.OrderSpecification;
import com.profcut.ordermanager.utils.jpa.specification.PageConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;
import java.util.UUID;

import static com.profcut.ordermanager.domain.enums.MasterStatus.CREATED;
import static com.profcut.ordermanager.domain.enums.OrderState.NEW;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CurrentUserSecurityService currentUserSecurityService;
    private final CounterpartyService counterpartyService;
    private final OrderItemService orderItemService;
    private final UpdateOrderByPatchMapper updateOrderByPatchMapper;

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
                .setOrderState(NEW)
                .setMasterStatus(CREATED)
                .setCompletionDate(request.getCompletionDate())
                .setCounterparty(counterpartyService.findById(request.getCounterpartyId()))
                .setAuthor(currentUserSecurityService.getLogin());
        var savedOrder = orderRepository.save(order);
        var items = orderItemService.createOrderItems(request.getItemRequests());
        savedOrder.addItems(items);
        checkAndChangeItemVat(order.isVatInclude(), order.getOrderItems());
        orderItemService.saveAll(items);
        return orderRepository.saveAndFlush(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderEntity> getOrdersPage(PageRequest request) {
        var pageable = PageConverter.covertToPageable(request);
        var spec = OrderSpecification.byIsDeletedFalse();
        return orderRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public OrderEntity updateOrder(UpdateOrderRequest request) {
        log.info("invoke OrderServiceImpl#updateOrder by request: {}", request);
        var order = findOrderById(request.getId());
        updateOrderByPatchMapper.updateOrder(request.getPatch(), order);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public OrderEntity saveOrder(OrderEntity order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderEntity findOrderById(UUID orderId) {
        return orderRepository.findOrderById(orderId).orElseThrow(() -> OrderNotFoundException.byOrderId(orderId));
    }

    @Override
    public Page<OrderEntity> findAllOrdersByCounterpartyId(UUID counterpartyId, PageRequest pageRequest) {
        var pageable = PageConverter.covertToPageable(pageRequest);
        var spec = OrderSpecification.byCounterpartyId(counterpartyId);
        return orderRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public OrderEntity changeState(UUID orderId, OrderState state) {
        log.info("invoke OrderServiceImpl#changeState orderId: {}, newState: {}", orderId, state);
        var order = findOrderById(orderId);
        order.setOrderState(state);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrderById(UUID orderId) {
        log.info("invoke OrderServiceImpl#deleteOrderById with id: {}", orderId);
        var order = findOrderById(orderId);
        order.setDeleted(true);
        orderRepository.save(order);
    }

    private void checkAndChangeItemVat(boolean orderVat, List<OrderItemEntity> items) {
        items.stream().filter(item -> item.isVatInclude() != orderVat)
                .peek(item -> {
                    item.setVatInclude(orderVat);
                    item.calculateVat();
                }).forEach(orderItemService::saveItem);
    }

    private String getNextOrderNumber() {
        var nexNumber = orderRepository.getOrderNumbers() + 1;
        var year = Year.now().getValue();
        return "Заказ №%d-%d".formatted(year, nexNumber);
    }
}
