package com.ilyascan.taskflowapi.mapper;


import com.ilyascan.taskflowapi.dto.BoardDto;
import com.ilyascan.taskflowapi.entity.Board;
import com.ilyascan.taskflowapi.responce.BoardDtoResponce;
import com.ilyascan.taskflowapi.responce.ListDtoResponce;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BoardMapper {


    private final ListColunmMapper listColumnMapper;

    public BoardMapper(ListColunmMapper listColumnMapper) {
        this.listColumnMapper = listColumnMapper;
    }

    public BoardDtoResponce map(Board board) {

        List<ListDtoResponce> lists =
                board.getListColumns()
                        .stream()
                        .map(listColumnMapper::map)
                        .toList();

        return BoardDtoResponce.builder()
                .boardId(board.getBoardId().toString())
                .boardName(board.getBoardName())
                .boardDescription(board.getBoardDescription())
                .createdAt(board.getCreatedAt())
                .lists(lists)
                .build();
    }



}
