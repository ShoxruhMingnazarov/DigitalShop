package com.example.telegraph_backend.service;

import com.example.telegraph_backend.dto.TaskDto;
import com.example.telegraph_backend.entity.TaskEntity;

import java.util.List;
import java.util.UUID;

public interface TaskServices extends BaseService<TaskEntity, TaskDto>{
    TaskEntity addTask(TaskDto taskDto, UUID uuid);

    List<TaskEntity> findAllTasks(int page,int size,boolean sortByName);
}
