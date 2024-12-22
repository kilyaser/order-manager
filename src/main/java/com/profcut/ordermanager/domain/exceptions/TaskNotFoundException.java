package com.profcut.ordermanager.domain.exceptions;

import java.util.UUID;

public class TaskNotFoundException extends EntityNotFoundException {

    protected TaskNotFoundException(String message) {
        super(message);
    }

    public static TaskNotFoundException byTaskId(UUID taskId) {
        return new TaskNotFoundException("Task with id %s not found".formatted(taskId));
    }
}
