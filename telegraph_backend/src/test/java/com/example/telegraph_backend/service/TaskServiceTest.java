package com.example.telegraph_backend.service;

import com.example.telegraph_backend.dto.TaskDto;
import com.example.telegraph_backend.entity.TaskEntity;
import com.example.telegraph_backend.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TaskServiceTest {

    private final String title = "test";
    private final String author = "tester";
    private final String description = "23";

    private Pageable pageable;

    private TaskDto taskDto;

    private TaskEntity taskEntity;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp(){
        initMocks(this);
        taskDto = new TaskDto(title,author,description);
        taskEntity = TaskEntity.builder()
                .title(title)
                .author(author)
                .description(description)
                .build();
    }




    @Test
    void save() {
      when(modelMapper.map(taskDto,TaskEntity.class)).thenReturn(taskEntity);
      when(taskRepository.save(taskEntity)).thenReturn(taskEntity);

      TaskEntity task = taskService.save(taskDto);

      assertEquals(title,task.getTitle());
    }



    @Test
    void update() {
//      when(taskRepository.findById(1L))
//              .thenReturn(Optional.of(taskEntity));
//
//        when(taskRepository.save(taskEntity))
//                .thenReturn(taskEntity);
//        TaskDto dto = taskService.update(1L,taskDto);
//
//        assertEquals(title,taskDto.getTitle());
    }

    @Test
    void deleteById() {
        doNothing().when(taskRepository).deleteById(any(UUID.class));
        taskService.deleteById(UUID.randomUUID());
        verify(taskRepository,times(1));
    }

    @Test
    void getAllByTitleAsc() {

    }

    @Test
    void getById(){
        doNothing().when(taskRepository).getById(any(UUID.class));
        taskService.getById(UUID.randomUUID());
        verify(taskRepository,times(1));
    }


    @Test
    void searchByTitle() {
       List<TaskEntity> taskEntities = Arrays.asList(taskEntity);

       when(taskRepository.findTaskEntitiesByTitleOrderByTitleAsc(title))
               .thenReturn(taskEntities);

       List<TaskEntity> result = taskService.searchByTitle(title);

       assertEquals(taskEntities.size(),result.size());
    }
}