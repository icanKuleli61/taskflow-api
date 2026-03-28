package com.ilyascan.taskflowapi.service;

import com.ilyascan.taskflowapi.Security.CustomUserDetails;
import com.ilyascan.taskflowapi.dto.CheckListItemDto;
import com.ilyascan.taskflowapi.entity.CheckListItem;
import com.ilyascan.taskflowapi.entity.Task;
import com.ilyascan.taskflowapi.entity.User;
import com.ilyascan.taskflowapi.exception.CustomException;
import com.ilyascan.taskflowapi.exception.ExceptionError;
import com.ilyascan.taskflowapi.handler.ApiResponce;
import com.ilyascan.taskflowapi.repository.CheckListItemRepository;
import com.ilyascan.taskflowapi.repository.TaskRepository;
import com.ilyascan.taskflowapi.repository.UserRepository;
import com.ilyascan.taskflowapi.request.CheckListItemToggleRequest;
import com.ilyascan.taskflowapi.request.CheckListItemUpdate;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class CheckListItemServiceImpl implements CheckListItemService {

    private final CheckListItemRepository checkListItemRepository;

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    public CheckListItemServiceImpl(CheckListItemRepository checkListItemRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.checkListItemRepository = checkListItemRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    @Override
    public ResponseEntity<?> createChecklistItem(String taskId, CheckListItemDto checkListItemDto, Authentication authentication) {
        UUID taskUUID = UUID.fromString(taskId);
        Task task = isAuthorized(taskUUID, authentication);


        Integer maxPos = checkListItemRepository.findMaxPositionByTaskId(taskUUID);
        Integer nextPos = maxPos + 1;

        CheckListItem entity = toEntity(checkListItemDto, task, nextPos);
        checkListItemRepository.save(entity);
        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message("Başarılı bir şekilde check text'ler oluşturuldu")
                .timestamp(new Date())
                .build()
        );
    }

    @Override
    public ResponseEntity<?> getChecklistItem(String taskId, Authentication authentication) {
        UUID taskUUID = UUID.fromString(taskId);
        Task authorized = isAuthorized(taskUUID, authentication);

        List<CheckListItem> byTaskTaskId = checkListItemRepository.findByTask_taskId(taskUUID);


        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message(authorized.getTaskTitle() +" ait checklistler getirildi.")
                .data(byTaskTaskId)
                .timestamp(new Date())
                .build()
        );
    }

    @Transactional
    @Override
    public ResponseEntity<?> updateChecklistItem(String checkId, CheckListItemUpdate checkListItemUpdate, Authentication authentication) {
        UUID checkUUID = UUID.fromString(checkId);
        CheckListItem checkListItem = checkListItemRepository.findByIdWithDetails(checkUUID).orElseThrow(
                () -> new CustomException(ExceptionError.UNAUTHORIZED)
        );
        Task authorized = isAuthorized(checkListItem.getTask().getTaskId(), authentication);
        boolean flag = false;
        if (changeTrueField(checkListItem.getText(),checkListItemUpdate::getText,checkListItemUpdate::setText)) flag = true;
        String message = "Değiştirilecek bir şey bulunamadı.";
        if (flag){
            message = "Alanlar Başarıyla değişti";
        }
        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message(message)
                .data(checkListItem)
                .timestamp(new Date())
                .build()
        );
    }

    @Transactional
    @Override
    public ResponseEntity<?> togleChecklistItem(String id, CheckListItemToggleRequest checkListItemToggleRequest, Authentication authentication) {
        return null;
    }

    @Transactional
    @Override
    public ResponseEntity<?> deleteChecklistItem(String id, Authentication authentication) {
        UUID checkUUID = UUID.fromString(id);
        CheckListItem checkListItem = checkListItemRepository.findByIdWithDetails(checkUUID).orElseThrow(
                () -> new CustomException(ExceptionError.UNAUTHORIZED)
        );
        Task authorized = isAuthorized(checkListItem.getTask().getTaskId(), authentication);

        Integer deletedPosition = checkListItem.getPosition();
        UUID taskId = checkListItem.getTask().getTaskId();

        checkListItemRepository.delete(checkListItem);
        List<CheckListItem> items =
                checkListItemRepository
                        .findAllByTaskTaskIdAndPositionGreaterThanOrderByPosition(
                                taskId,
                                deletedPosition
                        );
        for (CheckListItem item : items) {
            item.setPosition(item.getPosition() - 1);
        }
        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message(checkListItem.getText() + " Başarıyla silindi.")
                .timestamp(new Date())
                .build());
    }




    private <T> boolean changeTrueField(T field, Supplier<T> supplier, Consumer<T> consumer) {
        if (!isFiealdCheck(field, supplier)) {
            consumer.accept(supplier.get());
            return true;
        }
        return false;
    }

    private <T> boolean isFiealdCheck(T  field, Supplier<T> supplier) {
        if (supplier.get() == null) {
            return true;
        }
        if (supplier.get() instanceof Boolean && supplier.get().equals(Boolean.TRUE)) {
            return true;
        }
        return field.equals(supplier.get());
    }




    private Task isAuthorized(UUID taskId, Authentication authentication) {

        Task task = getTask(taskId);
        User user = getUser(authentication);
        boolean isMember = task.getListColumn().getBoard().getMembers().stream()
                .anyMatch(member -> member.getUserId().equals(user.getUserId()));

        if (!isMember) {
            throw new CustomException(ExceptionError.UNAUTHORIZED);
        }
        return task;
    }

    private Task getTask(UUID taskId){
        return taskRepository.findByIdWithDetails(taskId).orElseThrow(
                () -> new CustomException(ExceptionError.TASK_NOT_FOUND_USER)
        );
    }

    private User getUser(Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        assert principal != null;
        return userRepository.findById(principal.getUser().getUserId()).orElseThrow(
                () -> new CustomException(ExceptionError.USER_NOT_FOUND)
        );
    }

    private CheckListItem toEntity(CheckListItemDto checkListItemDto,Task task,Integer maxPos) {
        return CheckListItem.builder()
                .text(checkListItemDto.getText())
                .task(task)
                .position(maxPos)
                .build();
    }


}
