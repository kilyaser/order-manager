package com.profcut.ordermanager.controllers.rest.mapper;

import com.profcut.ordermanager.domain.dto.task.UiTask;
import com.profcut.ordermanager.domain.entities.TaskEntity;
import org.mapstruct.Mapper;

import java.util.function.Function;

@Mapper
public interface UiTaskMapper extends Function<TaskEntity, UiTask> {
}
