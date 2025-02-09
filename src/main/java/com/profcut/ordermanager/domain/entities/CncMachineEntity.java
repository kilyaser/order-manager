package com.profcut.ordermanager.domain.entities;

import com.profcut.ordermanager.domain.enums.MachineType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "cnc_machines")
public class CncMachineEntity {
    /**
     * Идентификатор станка с ЧПУ.
     */
    @Id
    @UuidGenerator
    private UUID id;
    /**
     * Тип станка с ЧПУ.
     */
    @Enumerated(EnumType.STRING)
    private MachineType machineType;
    /**
     * Наименование станка с ЧПУ.
     */
    private String name;
    /**
     * Признак занятости станка.
     */
    private boolean isOccupied;
    /**
     * Заказ.
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    /**
     * Идентификатор позиции заказа.
     */
    @ManyToOne
    @JoinColumn(name = "order_item_id")
    private OrderItemEntity orderItem;
}
