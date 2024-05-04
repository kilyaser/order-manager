package com.profcut.ordermanager.domain.entities;

import com.profcut.ordermanager.domain.enums.MachineType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
}