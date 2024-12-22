package com.profcut.ordermanager.controllers.rest.ui;

import com.profcut.ordermanager.controllers.rest.mapper.UiTaskMapper;
import com.profcut.ordermanager.domain.dto.task.CreateTaskRequest;
import com.profcut.ordermanager.domain.dto.task.TaskCompletedRequest;
import com.profcut.ordermanager.domain.dto.task.UiTask;
import com.profcut.ordermanager.domain.dto.task.UpdateTaskRequest;
import com.profcut.ordermanager.service.TaskService;
import com.profcut.ordermanager.service.handlers.AddTaskHandler;
import com.profcut.ordermanager.service.handlers.TaskComplectedHandler;
import com.profcut.ordermanager.service.handlers.UpdateTaskHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ui/tasks")
@Tag(name = "task-ui-api", description = "Контроллер для уравления задачами")
public class TaskApi {

    private final TaskService taskService;
    private final UiTaskMapper taskMapper;
    private final AddTaskHandler addHandler;
    private final TaskComplectedHandler complectedHandler;
    private final UpdateTaskHandler updateHandler;

    @GetMapping("/{taskId}")
    @Operation(description = "Получить задачу по ID")
    public UiTask getTask(@PathVariable("taskId") UUID taskId) {
        return taskMapper.apply(taskService.findById(taskId));
    }

    @GetMapping("/order/{orderId}")
    @Operation(description = "Получить задачи по заказу")
    public List<UiTask> getTasksByOrderId(@PathVariable("orderId") UUID orderId) {
        return taskService.findAllByOrderId(orderId).stream()
                .map(taskMapper)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Добавить задачу к заказу")
    public UiTask addTask(@Valid @RequestBody CreateTaskRequest request) {
        return addHandler.handle(request);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Изменить признак завершения задачи")
    public UiTask updateTask(@Valid @RequestBody TaskCompletedRequest request) {
        return complectedHandler.handler(request);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Изменить опесание заказа")
    public UiTask updateTask(@Valid @RequestBody UpdateTaskRequest request) {
        return updateHandler.handle(request);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить задачу по ID")
    void deleteTask(@PathVariable("taskId") UUID taskId) {
        taskService.deleteTask(taskId);
    }
}
