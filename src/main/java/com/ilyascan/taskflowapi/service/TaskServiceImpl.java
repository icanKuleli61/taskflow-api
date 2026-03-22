package com.ilyascan.taskflowapi.service;

import com.ilyascan.taskflowapi.Security.CustomUserDetails;
import com.ilyascan.taskflowapi.dto.TaskDto;
import com.ilyascan.taskflowapi.entity.Board;
import com.ilyascan.taskflowapi.entity.ListColumn;
import com.ilyascan.taskflowapi.entity.Task;
import com.ilyascan.taskflowapi.entity.User;
import com.ilyascan.taskflowapi.exception.CustomException;
import com.ilyascan.taskflowapi.exception.ExceptionError;
import com.ilyascan.taskflowapi.handler.ApiResponce;
import com.ilyascan.taskflowapi.repository.ListColumnRepository;
import com.ilyascan.taskflowapi.repository.TaskRepository;
import com.ilyascan.taskflowapi.request.TaskRequest;
import com.ilyascan.taskflowapi.request.TaskUpdateRequest;
import com.ilyascan.taskflowapi.responce.TaskResponce;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    private final BoardService boardService;

    private final ListColumnRepository  listColumnRepository;

    public TaskServiceImpl(TaskRepository taskRepository, BoardService boardService, ListColumnRepository listColumnRepository) {
        this.taskRepository = taskRepository;
        this.boardService = boardService;
        this.listColumnRepository = listColumnRepository;
    }


    @Override
    public ResponseEntity<?> createTask(TaskDto taskDto, Authentication authentication) {
        User user = authenGetUser(authentication);
        ListColumn listColumn = listColumnRepository.findWithBoardById(UUID.fromString(taskDto.getListId())).orElseThrow(
                () -> new CustomException(ExceptionError.LIST_IS_NOT_FOUND)
        );

        Board authorizedBoard = boardService.getAuthorizedBoard(authentication, listColumn.getBoard().getBoardId().toString());

        Task taskEntity = toEntity(taskDto,listColumn);
        taskRepository.save(taskEntity);
        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message(taskEntity.getTaskTitle()+ " adında task oluşturuldu. ")
                .timestamp(new Date())
                .build()
        );
    }



    @Override
    public ResponseEntity<?> getTasksByList(TaskRequest taskRequest, Authentication authentication) {

        User user = authenGetUser(authentication);

        ListColumn listColumn = listColumnRepository.findWithBoardById(UUID.fromString(taskRequest.getListId())).orElseThrow(
                () -> new CustomException(ExceptionError.LIST_IS_NOT_FOUND)
        );

        List<Task> tasks = taskRepository.findByListColumn(listColumn);

        List<TaskResponce> response = toTaskResponseList(tasks);

        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message("Tasklar başarıyla getirildi.")
                .data(response)
                .timestamp(new Date())
                .build());
    }



    @Transactional
    @Override
    public ResponseEntity<?> updateTask(TaskUpdateRequest taskUpdateRequest, Authentication authentication) {
        User user = authenGetUser(authentication);

        Task task = taskRepository.findByIdWithDetails(UUID.fromString(taskUpdateRequest.getId())).orElseThrow(
                () -> new CustomException(ExceptionError.UNAUTHORIZED)
        );

        Board authorizedBoard = boardService.getAuthorizedBoard(authentication, task.getListColumn().getBoard().getBoardId().toString());


        boolean flag = false;
        if (updateFieldCheck(task.getTaskTitle(), taskUpdateRequest::getTaskTitle, task::setTaskTitle)) flag = true;
        if (updateFieldCheck(task.getTaskDescription(), taskUpdateRequest::getTaskDescription, task::setTaskDescription))  flag = true;
        if (updateFieldCheck(task.getTaskEndTime(), taskUpdateRequest::getTaskEndTime, task::setTaskEndTime))  flag = true;

        String message = "Güncellenecek bir şey bulunamadı. ";

        if (flag) {
            message = "Alanlar başarıyla güncelllendi";
        }

        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message(message)
                .timestamp(new Date())
                .build());
    }



    @Override
    public ResponseEntity<?> deleteTask(UUID taskId, Authentication authentication) {

        User user = authenGetUser(authentication);

        Task task = taskRepository.findByIdWithDetails(taskId)
                .orElseThrow(() -> new CustomException(ExceptionError.TASK_NOT_FOUND));

        boolean isMember = task.getListColumn().getBoard().getMembers().stream()
                .anyMatch(member -> member.getUserId().equals(user.getUserId()));

        if (!isMember) {
            throw new CustomException(ExceptionError.UNAUTHORIZED);
        }

        taskRepository.delete(task);

        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message("Task başarıyla silindi")
                .timestamp(new Date())
                .build());
    }



    private <T> boolean updateFieldCheck(T field, Supplier<T> supplier, Consumer<T> consumer) {
        T newField = supplier.get();
        boolean flag = false;
        if (Objects.isNull(newField)){
            return flag;
        }
        if (!Objects.equals(field, newField)){
            updateField(newField,consumer);
            flag = true;
        }
        return flag;
    }

    private <T> void updateField(T newField, Consumer<T> consumer){
        consumer.accept(newField);
    }


    private User authenGetUser(Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if (user == null) {
            throw new CustomException(ExceptionError.USER_NOT_FOUND);
        }
        return user;
    }

    private List<TaskResponce> toTaskResponseList(List<Task> taskList) {
        List<TaskResponce> taskResponceList = new ArrayList<>();
        for (Task task : taskList) {
            TaskResponce taskResponce = toTaskResponse(task);
            taskResponceList.add(taskResponce);
        }
        return taskResponceList;


    }

    private TaskResponce toTaskResponse(Task  taskEntity) {
        return TaskResponce.builder()
                .taskTitle(taskEntity.getTaskTitle())
                .taskDescription(taskEntity.getTaskDescription())
                .taskStartTime(taskEntity.getTaskStartTime())
                .taskEndTime(taskEntity.getTaskEndTime())
                .build();
    }

    private Task toEntity(TaskDto taskDto,ListColumn listColumn) {
        return Task.builder()
                .taskTitle(taskDto.getTaskTitle())
                .taskDescription(taskDto.getTaskDescription())
                .listColumn(listColumn)
                .build();
    }
}
