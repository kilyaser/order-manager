package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.domain.entities.TaskEntity;
import com.profcut.ordermanager.domain.exceptions.TaskNotFoundException;
import com.profcut.ordermanager.domain.repository.TaskRepository;
import com.profcut.ordermanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public TaskEntity findById(UUID taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> TaskNotFoundException.byTaskId(taskId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskEntity> findAllByOrderId(UUID orderId) {
        return taskRepository.findAllByOrderId(orderId);
    }

    @Override
    @Transactional
    public void deleteTask(UUID taskId) {
        log.info("Deleting task {}", taskId);
        taskRepository.deleteById(taskId);
    }
}
