package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.controllers.rest.mapper.OrderItemCreateMapper;
import com.profcut.ordermanager.domain.dto.order.OrderItemFieldsPatch;
import com.profcut.ordermanager.domain.dto.order.OrderItemRequest;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import com.profcut.ordermanager.domain.exceptions.OrderItemNotFoundException;
import com.profcut.ordermanager.domain.exceptions.OrderItemVatMissMatchException;
import com.profcut.ordermanager.domain.repository.OrderItemRepository;
import com.profcut.ordermanager.service.MaterialService;
import com.profcut.ordermanager.service.OrderItemService;
import com.profcut.ordermanager.service.ProductService;
import com.profcut.ordermanager.service.TechnologistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final MaterialService materialService;
    private final TechnologistService technologistService;
    private final OrderItemCreateMapper orderItemCreateMapper;

    @Override
    @Transactional(readOnly = true)
    public OrderItemEntity findById(UUID itemId) {
        return orderItemRepository.findById(itemId).orElseThrow(() -> OrderItemNotFoundException.byItemId(itemId));
    }

    @Override
    @Transactional
    public List<OrderItemEntity> createOrderItems(OrderEntity order, List<OrderItemRequest> itemRequests) {
        log.info("invoke 'OrderItemServiceImpl#createOrderItems' with itemRequests: {}", itemRequests);
        var items = itemRequests.stream().map(item -> createOrderItem(order, item)).toList();
        return orderItemRepository.saveAllAndFlush(items);
    }

    @Override
    public OrderItemEntity saveItem(OrderItemEntity item) {
        return orderItemRepository.save(item);
    }

    @Override
    @Transactional
    public void updateOrderItem(OrderItemFieldsPatch patch) {
        log.info("invoke 'OrderItemServiceImpl#updateOrderItem' with patch: {}", patch);
        var item = findById(patch.getItemId());
        updateByPatch(item, patch);
        orderItemRepository.save(item);
    }

    @Override
    @Transactional
    public void deleteOrderItems(Set<UUID> itemIds) {
        log.info("invoke 'OrderItemServiceImpl#deleteOrderItems' with itemIds: {}", itemIds);
        orderItemRepository.deleteOrderItems(itemIds);
    }

    @Override
    @Transactional
    public void deleteById(UUID itemId) {
        log.info("invoke 'OrderItemServiceImpl#deleteById' with id: {}", itemId);
        orderItemRepository.deleteById(itemId);
    }

    private void updateByPatch(OrderItemEntity item, OrderItemFieldsPatch patch) {
        ofNullable(patch.getProductId())
                .ifPresent(productId -> item.setProduct(productService.getProductById(productId)));

        ofNullable(patch.getQuantity())
                .ifPresent(quantity -> item.setQuantity(quantity).calculateTotalPrice());

        ofNullable(patch.getQuantityShipped()).ifPresent(item::setQuantityShipped);

        ofNullable(patch.getPricePerProduct())
                .ifPresent(price -> item.setPricePerProduct(price).calculateTotalPrice());

        ofNullable(patch.getIsVatInclude())
                .ifPresent(vatInclude -> {
                    var orderVatInclude = item.getOrder().isVatInclude();
                    if (vatInclude != orderVatInclude) {
                        throw OrderItemVatMissMatchException.byVatInclude(vatInclude, orderVatInclude, item.getOrder().getOrderId());
                    }
                    item.setVatInclude(vatInclude).calculateVat();
                });

        ofNullable(patch.getProductType()).ifPresent(item::setProductType);
        ofNullable(patch.getMachineType()).ifPresent(item::setMachineType);
        ofNullable(patch.getCompletionDate()).ifPresent(item::setCompletionDate);
        ofNullable(patch.getPreparationState()).ifPresent(item::setPreparationState);

        ofNullable(patch.getMaterialId())
                .ifPresent(materialId -> item.setMaterial(materialService.findById(materialId)));

        ofNullable(patch.getTechnologistId())
                .ifPresent(technologistId -> item.setTechnologist(technologistService.getById(technologistId)));
    }

    private OrderItemEntity createOrderItem(OrderEntity order, OrderItemRequest itemRequest) {
        var itemEntity = orderItemCreateMapper.apply(itemRequest);
        itemEntity.setOrder(order);
        ofNullable(itemRequest.getTechnologistId()).map(technologistService::getById).ifPresent(itemEntity::setTechnologist);
        ofNullable(itemRequest.getMaterialId()).map(materialService::findById).ifPresent(itemEntity::setMaterial);
        ofNullable(itemRequest.getProductId()).map(productService::getProductById).ifPresent(itemEntity::setProduct);
        itemEntity.calculateTotalPrice();
        itemEntity.calculateVat();
        return itemEntity;
    }
}
