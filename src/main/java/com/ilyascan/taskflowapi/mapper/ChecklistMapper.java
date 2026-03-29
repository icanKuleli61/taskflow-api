package com.ilyascan.taskflowapi.mapper;


import com.ilyascan.taskflowapi.dto.CheckListItemDto;
import com.ilyascan.taskflowapi.entity.CheckListItem;
import com.ilyascan.taskflowapi.entity.Task;
import com.ilyascan.taskflowapi.responce.CheckListItemResponce;
import org.springframework.stereotype.Component;

@Component
public class ChecklistMapper {


    public CheckListItemResponce map(CheckListItem item){

        return CheckListItemResponce.builder()
                .id(item.getChechListId().toString())
                .text(item.getText())
                .completed(item.isCompleted())
                .position(item.getPosition())
                .build();
    }


    public CheckListItem toEntity(CheckListItemDto checkListItemDto, Task task, Integer maxPos) {
        return CheckListItem.builder()
                .text(checkListItemDto.getText())
                .task(task)
                .position(maxPos)
                .build();
    }


}
