package com.profcut.ordermanager.service.handlers;

import com.profcut.ordermanager.controllers.rest.mapper.UiTaskMapper;
import com.profcut.ordermanager.domain.dto.task.UiTask;
import com.profcut.ordermanager.domain.dto.task.UpdateTaskRequest;
import com.profcut.ordermanager.domain.exceptions.TaskNotFoundException;
import com.profcut.ordermanager.domain.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateTaskHandler {

    private final UiTaskMapper taskMapper;
    private final TaskRepository taskRepository;

    @Transactional
    public UiTask handle(UpdateTaskRequest request) {
        return taskRepository.findById(request.getTaskId())
                .map(task -> task.setDescription(request.getDescription()))
                .map(taskRepository::save)
                .map(taskMapper).orElseThrow(() -> TaskNotFoundException.byTaskId(request.getTaskId()));
    }
}
