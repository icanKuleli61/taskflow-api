package com.ilyascan.taskflowapi.service;

import com.ilyascan.taskflowapi.dto.TaskDto;
import com.ilyascan.taskflowapi.request.TaskRequest;
import com.ilyascan.taskflowapi.request.TaskUpdateRequest;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface TaskService {

    ResponseEntity<?> createTask(TaskDto taskDto, Authentication authentication);

    ResponseEntity<?> getTasksByList(TaskRequest taskRequest, Authentication authentication);

    ResponseEntity<?> updateTask(TaskUpdateRequest taskUpdateRequest
            , Authentication authentication);

    ResponseEntity<?> deleteTask(UUID taskId, Authentication authentication);


}
