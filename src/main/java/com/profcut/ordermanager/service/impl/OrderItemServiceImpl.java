package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.controllers.rest.mapper.OrderItemCreateMapper;
import com.profcut.ordermanager.controllers.rest.mapper.UpdateItemMapper;
import com.profcut.ordermanager.domain.dto.order.OrderItemFieldsPatch;
import com.profcut.ordermanager.domain.dto.order.OrderItemRequest;
import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import com.profcut.ordermanager.domain.exceptions.OrderItemNotFoundException;
import com.profcut.ordermanager.domain.repository.OrderItemRepository;
import com.profcut.ordermanager.service.MaterialService;
import com.profcut.ordermanager.service.OrderItemService;
import com.profcut.ordermanager.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final MaterialService materialService;
    private final OrderItemCreateMapper orderItemCreateMapper;
    private final UpdateItemMapper updateItemMapper;

    @Override
    @Transactional(readOnly = true)
    public OrderItemEntity findById(UUID itemId) {
        return orderItemRepository.findById(itemId).orElseThrow(() -> OrderItemNotFoundException.byItemId(itemId));
    }

    @Override
    @Transactional
    public List<OrderItemEntity> createOrderItems(List<OrderItemRequest> itemRequests) {
        log.info("invoke 'OrderItemServiceImpl#createOrderItems' with itemRequests: {}", itemRequests);
        return itemRequests.stream().map(this::createOrderItem).toList();
    }

    @Override
    @Transactional
    public OrderItemEntity saveItem(OrderItemEntity item) {
        return orderItemRepository.save(item);
    }

    @Override
    @Transactional
    public List<OrderItemEntity> saveAll(List<OrderItemEntity> items) {
        return orderItemRepository.saveAll(items);
    }

    @Override
    @Transactional
    public void updateOrderItem(OrderItemFieldsPatch patch) {
        log.info("invoke 'OrderItemServiceImpl#updateOrderItem' with patch: {}", patch);
        var item = findById(patch.getItemId());
        updateItemMapper.updateOrderItem(patch, item);
        setPropertyIfPresent(patch.getProductId(), productService::getProductById, item::setProduct);
        setPropertyIfPresent(patch.getMaterialId(), materialService::findById, item::setMaterial);
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

    private OrderItemEntity createOrderItem(OrderItemRequest itemRequest) {
        var itemEntity = orderItemCreateMapper.apply(itemRequest);
        setPropertyIfPresent(itemRequest.getProductId(), productService::getProductById, itemEntity::setProduct);
        setPropertyIfPresent(itemRequest.getMaterialId(), materialService::findById, itemEntity::setMaterial);
        itemEntity.calculateTotalPrice();
        return itemEntity;
    }

    private <T> void setPropertyIfPresent(UUID id,
                                          Function<UUID, T> findFunction,
                                          Consumer<T> setFunction) {
        ofNullable(id)
                .map(findFunction)
                .ifPresent(setFunction);
    }
}
