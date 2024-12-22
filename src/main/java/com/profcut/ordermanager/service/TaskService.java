package com.profcut.ordermanager.service;

import com.profcut.ordermanager.domain.entities.TaskEntity;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    TaskEntity findById(UUID taskId);

    List<TaskEntity> findAllByOrderId(UUID orderId);

    void deleteTask(UUID taskId);
}
