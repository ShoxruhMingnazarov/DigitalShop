package com.example.telegraph_backend.service;

import com.example.telegraph_backend.dto.TaskDto;
import com.example.telegraph_backend.entity.TaskEntity;
import com.example.telegraph_backend.entity.UserEntity;
import com.example.telegraph_backend.repository.TaskRepository;
import com.example.telegraph_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService implements BaseService<TaskEntity, TaskDto>{

    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    @Override
    public TaskEntity save(TaskDto taskDto) {
        TaskEntity task = modelMapper.map(taskDto, TaskEntity.class);
        task.setLink(String.join("-",task.getTitle(), LocalDateTime.now().toString()));
        return taskRepository.save(task);

    }

    @Override
    public TaskEntity update(TaskDto taskDto,UUID id) {
        TaskEntity taskEntityById = taskRepository.findTaskEntityById(id);
        if(taskDto.getTitle().isEmpty()){
            taskEntityById.setTitle(taskDto.getTitle());
        }
        if(taskDto.getDescription().isEmpty()){
            taskEntityById.setDescription(taskDto.getDescription());
        }
        if (!taskDto.getAuthor().isEmpty()) {
            taskEntityById.setAuthor(taskDto.getAuthor());
        }
        taskEntityById.setUpdateDate(LocalDateTime.now());
        return taskRepository.save(taskEntityById);
    }

    @Override
    public void deleteById(UUID id) {
          taskRepository.deleteById(id);
    }





    @Override
    public TaskEntity getById(UUID id) {
        return taskRepository.findTaskEntityById(id);
    }

    public List<TaskEntity> getAllByTitleAsc(){
        return taskRepository.getAllByData();
    }



    public List<TaskEntity> searchByTitle(String title){
        return taskRepository.findTaskEntitiesByTitleOrderByTitleAsc(title);
    }

    public TaskEntity addTask(TaskDto taskDto,UUID uuid){
        UserEntity userEntity = userRepository.findUserEntitiesById(uuid);
        TaskEntity taskEntity = modelMapper.map(taskDto, TaskEntity.class);
        taskEntity.setLink(String.join("-",taskEntity.getTitle(),LocalDateTime.now().toString()));
        taskEntity.setUser(userEntity);
        return taskRepository.save(taskEntity);
    }
}
