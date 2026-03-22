package com.ilyascan.taskflowapi.controller;

import com.ilyascan.taskflowapi.dto.TaskDto;
import com.ilyascan.taskflowapi.request.TaskRequest;
import com.ilyascan.taskflowapi.request.TaskUpdateRequest;
import com.ilyascan.taskflowapi.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/task")
public class TaskControllerV1 {

    private final TaskService taskService;

    public TaskControllerV1(TaskService taskService) {
        this.taskService = taskService;
    }



    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody @Valid TaskDto taskDto
            , Authentication authentication){
        return taskService.createTask(taskDto,authentication);
    }


    @GetMapping("/me")
    public ResponseEntity<?> getTasksByList(@RequestBody @Valid TaskRequest taskRequest
            , Authentication authentication){
        return taskService.getTasksByList(taskRequest,authentication);
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateTask(@RequestBody @Valid TaskUpdateRequest taskUpdateRequest
            , Authentication authentication){
        return taskService.updateTask(taskUpdateRequest,authentication);
    }

    @DeleteMapping("me/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable(name = "id")UUID taskId, Authentication authentication){
        return taskService.deleteTask(taskId,authentication);
    }

}
