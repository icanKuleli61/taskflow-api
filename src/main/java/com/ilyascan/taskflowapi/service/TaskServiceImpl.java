package com.ilyascan.taskflowapi.service;

import com.ilyascan.taskflowapi.Security.CustomUserDetails;
import com.ilyascan.taskflowapi.dto.TaskDto;
import com.ilyascan.taskflowapi.entity.Task;
import com.ilyascan.taskflowapi.entity.User;
import com.ilyascan.taskflowapi.repository.TaskRepository;
import com.ilyascan.taskflowapi.request.TaskUpdateRequest;
import com.ilyascan.taskflowapi.responce.TaskResponce;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    @Override
    public ResponseEntity<?> createTask(TaskDto taskDto, Authentication authentication) {

        Task taskEntity = toEntity(taskDto,authenGetUser(authentication));
        taskRepository.save(taskEntity);
        return ResponseEntity.ok(taskEntity.getTaskTitle()+ " adında task oluşturuldu. ");
    }

    @Override
    public ResponseEntity<?> getTaskUser(Authentication authentication) {
        User user = authenGetUser(authentication);
        List<Task> allByUserId = taskRepository.findAllByUser_userId(user.getUserId());
        List<TaskResponce> taskResponceList = toTaskResponceList(allByUserId);
        return  ResponseEntity.ok(taskResponceList);
    }

    @Transactional
    @Override
    public ResponseEntity<?> updateTask(TaskUpdateRequest taskUpdateRequest, Authentication authentication) {
        User user = authenGetUser(authentication);
        Task task = taskRepository.findByTaskIdAndUser_UserId(UUID.fromString(taskUpdateRequest.getId()),
                user.getUserId()).orElseThrow(
                () -> new RuntimeException("Böyle bir task bulunamadı.")
        );
        StringBuilder builder = new StringBuilder();
        String baslik = updateFieldCheck("Başlık ", task.getTaskTitle(), taskUpdateRequest::getTaskTitle, task::setTaskTitle);
        String aciklama = updateFieldCheck("Acıklama ", task.getTaskDescription(), taskUpdateRequest::getTaskDescription, task::setTaskDescription);
        String bitisSuresi = updateFieldCheck("Bitiş Süresi ", task.getTaskEndTime(), taskUpdateRequest::getTaskEndTime, task::setTaskEndTime);
        String boolenCheck = updateFieldCheck("Proje bitti mi ", task.isCompleted(), taskUpdateRequest::getCompleted, task::setCompleted);

        builder.append(baslik).append(aciklama).append(bitisSuresi).append(boolenCheck).append(" Alanları Başarıyla Güncellendi");
        if (builder.isEmpty()){
            builder.delete(0, builder.length());
            builder.append("Güncellenecek bir şey bulunamadı.");
        }
        return ResponseEntity.ok(builder.toString());
    }

    @Override
    public ResponseEntity<?> deleteTask(UUID taskId, Authentication authentication) {
        User user = authenGetUser(authentication);
        Task task = taskRepository.findByTaskIdAndUser_UserId(taskId, user.getUserId()).orElseThrow(
                () -> new RuntimeException("Bu kullanacıya ait bir task bulunamadı. ")
        );
        taskRepository.delete(task);
        return ResponseEntity.ok("Task başarıyla silindi");
    }

    private <T> String updateFieldCheck(String message,T field, Supplier<T> supplier, Consumer<T> consumer) {
        T newField = supplier.get();
        if (Objects.isNull(newField)){
            return "";
        }
        String checkMesasage = "";
        if (!Objects.equals(field, newField)){
            updateField(newField,consumer);
            checkMesasage = message;
        }
        return checkMesasage;
    }

    private <T> void updateField(T newField, Consumer<T> consumer){
        consumer.accept(newField);
    }



    private List<TaskResponce> toTaskResponceList(List<Task> taskList){
        List<TaskResponce> taskResponceList = new ArrayList<>();
        for (Task task : taskList) {
            TaskResponce taskResponce = toTaskResponce(task);
            taskResponceList.add(taskResponce);
        }
        return taskResponceList;
    }

    private TaskResponce toTaskResponce(Task  taskEntity) {
        return TaskResponce.builder()
                .taskId(taskEntity.getTaskId().toString())
                .taskTitle(taskEntity.getTaskTitle())
                .taskDescription(taskEntity.getTaskDescription())
                .taskStartTime(taskEntity.getTaskStartTime())
                .taskEndTime(taskEntity.getTaskEndTime())
                .build();
    }

    private User authenGetUser(Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if (user == null) {
            throw new RuntimeException("User found exception");
        }
        return user;
    }

    private Task toEntity(TaskDto taskDto, User user){
        return Task.builder()
                .taskTitle(taskDto.getTaskTitle())
                .taskDescription(taskDto.getTaskDescription())
                .user(user)
                .build();
    }
}
