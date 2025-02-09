package com.profcut.ordermanager.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tasks")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class TaskEntity {
    /**
     * Идентификатор задачи.
     */
    @Id
    @UuidGenerator
    private UUID id;
    /**
     * Описание задачи.
     */
    private String description;
    /**
     * Признак завершения задачи.
     */
    private boolean isCompleted;
    /**
     * Заказ.
     */
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;
    /**
     * Дата созданя задачи.
     */
    @CreatedDate
    private LocalDateTime createdDate;
    /**
     * Дата завершения задачи
     */
    private LocalDateTime completedDate;
    /**
     * Дата последней модификации.
     */
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
