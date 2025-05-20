package com.profcut.ordermanager.service.report.mapper;

import com.profcut.ordermanager.domain.entities.ContractEntity;
import com.profcut.ordermanager.domain.entities.OrderEntity;
import com.profcut.ordermanager.domain.entities.OrderItemEntity;
import com.profcut.ordermanager.service.report.value.ReportedOrder;
import com.profcut.ordermanager.service.report.value.ReportedOrderItem;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface ReportedOrderMapper extends Function<OrderEntity, ReportedOrder> {

    @Override
    @Mapping(target = "currentDate", expression = "java(LocalDate.now().toString())")
    @Mapping(target = "counterpartyName", source = "counterparty.name")
    @Mapping(target = "contractDate", source = "contract.contractDate")
    @Mapping(target = "contractNumber", source = "order", qualifiedByName = "definedContractNumber")
    @Mapping(target = "orderNumber", source = "order", qualifiedByName = "definedOrderNumber")
    @Mapping(target = "vatSum", expression = "java(formatMoney(order.getVat()))")
    @Mapping(target = "totalPrice", expression = "java(formatMoney(order.getTotalPrice()))")
    @Mapping(target = "currentSum", expression = "java(formatMoney(order.getCurrentSum()))")
    ReportedOrder apply(OrderEntity order);

    @Mapping(target = "productName", source = "product.productName")
    @Mapping(target = "totalPrice", expression = "java(formatMoney(item.getTotalPrice()))")
    @Mapping(target = "pricePerProduct", expression = "java(formatMoney(item.getPricePerProduct()))")
    ReportedOrderItem apply(OrderItemEntity item);

    @Named("definedContractNumber")
    default String setContractNumber(OrderEntity order) {
        return ofNullable(order.getContract()).map(ContractEntity::getContractNumber).orElse("б/н");
    }

    @Named("definedOrderNumber")
    default String setOrderNumber(OrderEntity order) {
        return StringUtils.substringAfter(order.getOrderNumber(), "№");
    }

    default String formatMoney(BigDecimal value) {
        if (value == null) return "0.00";
        NumberFormat format = NumberFormat.getInstance(new Locale("ru", "RU"));
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        return format.format(value);
    }
}
