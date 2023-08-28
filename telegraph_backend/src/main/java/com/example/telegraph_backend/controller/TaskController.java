package com.example.telegraph_backend.controller;

import com.example.telegraph_backend.dto.TaskDto;
import com.example.telegraph_backend.entity.TaskEntity;
import com.example.telegraph_backend.exception.ObjectsNotFoundException;
import com.example.telegraph_backend.exception.RequestValidationException;
import com.example.telegraph_backend.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;



@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/task")
public class TaskController {

    private final TaskService taskService;


    @PostMapping("/add")
    @PreAuthorize(value = "hasRole(ADMIN)")
    public TaskEntity addTask(
            @Valid @RequestBody TaskDto taskDto,
            @RequestParam UUID id,
            BindingResult bindingResult
            ){
        if (bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw  new RequestValidationException(allErrors);
        }

       return taskService.addTask(taskDto,id);
    }

    @PostMapping("/update")
    public TaskEntity  updateTask(
            @RequestBody TaskDto taskDto,
            @RequestParam UUID id
    ){
       return taskService.update(taskDto, id);
    }

    @DeleteMapping("/delete")
    public String deleteTask(
            @RequestParam UUID id
            ) {
        if(taskService.getById(id) != null){
            taskService.deleteById(id);
            return "successfully deleted";
        }
        return "task not found";

    }

    @GetMapping("/get-all")
    public List<TaskEntity> getAll(){
         return taskService.getAllByTitleAsc();
    }

    @GetMapping("/search")
    public List<TaskEntity> search(
            @RequestParam String title
    ){
        if (taskService.searchByTitle(title).isEmpty()){
            throw new ObjectsNotFoundException("there is no task with this title");
        };
        return taskService.searchByTitle(title);
    }


}
