package com.ilyascan.taskflowapi.mapper;


import com.ilyascan.taskflowapi.dto.ListColumnDto;
import com.ilyascan.taskflowapi.entity.Board;
import com.ilyascan.taskflowapi.entity.ListColumn;
import com.ilyascan.taskflowapi.responce.ListDtoResponce;
import com.ilyascan.taskflowapi.responce.TaskDtoResponce;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListColunmMapper {


    private final TaskMapper taskMapper;

    public ListColunmMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public ListDtoResponce map(ListColumn list) {

        List<TaskDtoResponce> tasks =
                list.getTasks()
                        .stream()
                        .map(taskMapper::map)
                        .toList();

        return ListDtoResponce.builder()
                .id(list.getId().toString())
                .listName(list.getListName())
                .listDescription(list.getListDescription())
                .taskCount(tasks.size())
                .tasks(tasks)
                .build();
    }


    public ListColumn toEntity(ListColumnDto listColumnDto, Board board) {
        return ListColumn.builder()
                .listName(listColumnDto.getListName())
                .listDescription(listColumnDto.getListDescription())
                .board(board)
                .build();
    }


}
