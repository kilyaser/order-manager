package com.profcut.ordermanager.controllers.rest.handlers;

import com.profcut.ordermanager.controllers.rest.mapper.UiTaskMapper;
import com.profcut.ordermanager.domain.dto.task.CreateTaskRequest;
import com.profcut.ordermanager.domain.dto.task.UiTask;
import com.profcut.ordermanager.domain.entities.TaskEntity;
import com.profcut.ordermanager.domain.repository.TaskRepository;
import com.profcut.ordermanager.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AddTaskHandler {

    private final OrderService orderService;
    private final TaskRepository taskRepository;
    private final UiTaskMapper taskMapper;

    @Transactional
    public UiTask handle(CreateTaskRequest request) {
        log.info("invoke AddTaskHandler#handle with request: {}", request);
        var order = orderService.findOrderById(request.getOrderId());
        var task = new TaskEntity().setDescription(request.getDescription());
        order.addTask(List.of(task));
        return taskMapper.apply(taskRepository.save(task));
    }
}
