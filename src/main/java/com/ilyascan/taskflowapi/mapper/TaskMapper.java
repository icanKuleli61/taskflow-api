package com.ilyascan.taskflowapi.mapper;


import com.ilyascan.taskflowapi.dto.TaskDto;
import com.ilyascan.taskflowapi.entity.ListColumn;
import com.ilyascan.taskflowapi.entity.Task;
import com.ilyascan.taskflowapi.responce.CheckListItemResponce;
import com.ilyascan.taskflowapi.responce.TaskDtoResponce;
import com.ilyascan.taskflowapi.responce.TaskResponce;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskMapper {

    private final ChecklistMapper checklistMapper;

    public TaskMapper(ChecklistMapper checklistMapper) {
        this.checklistMapper = checklistMapper;
    }


    public TaskDtoResponce map(Task task) {

        List<CheckListItemResponce> checklist =
                task.getChecklistItem()
                        .stream()
                        .map(checklistMapper::map)
                        .toList();

        int total = checklist.size();

        int completed =
                (int) checklist.stream()
                        .filter(CheckListItemResponce::isCompleted)
                        .count();

        return TaskDtoResponce.builder()
                .taskId(task.getTaskId().toString())
                .taskTitle(task.getTaskTitle())
                .taskDescription(task.getTaskDescription())
                .checklistTotal(total)
                .checklistCompleted(completed)
                .checklist(checklist)
                .build();
    }


    public TaskResponce toTaskResponse(Task  taskEntity) {
        return TaskResponce.builder()
                .taskTitle(taskEntity.getTaskTitle())
                .taskDescription(taskEntity.getTaskDescription())
                .taskStartTime(taskEntity.getTaskStartTime())
                .taskEndTime(taskEntity.getTaskEndTime())
                .build();
    }

    public Task toEntity(TaskDto taskDto, ListColumn listColumn) {
        return Task.builder()
                .taskTitle(taskDto.getTaskTitle())
                .taskDescription(taskDto.getTaskDescription())
                .listColumn(listColumn)
                .build();
    }


}
