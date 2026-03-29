package com.ilyascan.taskflowapi.service;

import com.ilyascan.taskflowapi.dto.ListColumnDto;
import com.ilyascan.taskflowapi.entity.Board;
import com.ilyascan.taskflowapi.entity.ListColumn;
import com.ilyascan.taskflowapi.exception.CustomException;
import com.ilyascan.taskflowapi.exception.ExceptionError;
import com.ilyascan.taskflowapi.handler.ApiResponce;
import com.ilyascan.taskflowapi.mapper.ListColunmMapper;
import com.ilyascan.taskflowapi.repository.BoardRepository;
import com.ilyascan.taskflowapi.repository.ListColumnRepository;
import com.ilyascan.taskflowapi.request.ListColumnUpdate;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class ListColumnImpl implements ListColumnService {

    private final ListColumnRepository listColumnRepository;

    private final BoardRepository boardRepository;

    private final BoardService boardService;

    private final ListColunmMapper listColunmMapper;

    public ListColumnImpl(ListColumnRepository listColumnRepository, BoardRepository boardRepository, BoardService boardService, ListColunmMapper listColunmMapper) {
        this.listColumnRepository = listColumnRepository;
        this.boardRepository = boardRepository;
        this.boardService = boardService;
        this.listColunmMapper = listColunmMapper;
    }


    @Override
    public ResponseEntity<?> listCreate(ListColumnDto listColumnDto
    , Authentication authentication) {

        Board authorizedBoard = boardService.getAuthorizedBoard(authentication, listColumnDto.getBoardId());

        ListColumn entity = listColunmMapper.toEntity(listColumnDto, authorizedBoard);
        listColumnRepository.save(entity);
        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message("Calışma alanına başarılı bir şekilde "+listColumnDto.getListName() + " Adında listte eklendi")
                .build()
        );
    }

    @Override
    public ResponseEntity<?> boardListGet(UUID boardId, Authentication authentication) {
        Board authorizedBoard = boardService.getAuthorizedBoard(authentication,boardId.toString());

        List<ListColumn> byBoardBoardId = listColumnRepository.findByBoard_boardId(boardId);

        if (byBoardBoardId.isEmpty()) {
            throw new CustomException(ExceptionError.BOARD_IS_NOT_LIST);
        }

        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message("Calışma alanlarının listtesi başarıyla getirildi.")
                .data(byBoardBoardId)
                .build()
        );
    }

    @Override
    public ResponseEntity<?> listDelete(UUID listId, Authentication authentication) {

        ListColumn listColumn = listColumnRepository.findWithBoardById(listId).orElseThrow(
                ()-> new CustomException(ExceptionError.LIST_IS_NOT_FOUND)
        );


        Board authorizedBoard = boardService.getAuthorizedBoard(authentication,listColumn.getBoard().getBoardId().toString());

        listColumnRepository.deleteById(listId);
        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message("List'e başarıyla silindi")
                .build()
        );
    }

    @Transactional
    @Override
    public ResponseEntity<?> listUpdate(UUID listId, ListColumnUpdate listColumnUpdate, Authentication authentication) {

        ListColumn listColumn = listColumnRepository.findWithBoardById(listId).orElseThrow(
                ()-> new CustomException(ExceptionError.LIST_IS_NOT_FOUND)
        );


        Board authorizedBoard = boardService.getAuthorizedBoard(authentication,listColumn.getBoard().getBoardId().toString());

        boolean isCheck = false;
        if (fieldIsCheck(listColumn.getListName(),listColumnUpdate::getListName,listColumn::setListName)) isCheck = true;

        if (fieldIsCheck(listColumn.getListDescription(),listColumnUpdate::getListDescription,listColumn::setListDescription)) isCheck = true;

        String message = "Değişecek alan bulunamadı";
        if (isCheck) {
            message = "Alanlar başarıyla değişti";
        }
        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message(message)
                .build()
        );
    }

    private <T> boolean fieldIsCheck(T field, Supplier<T> supplier, Consumer<T> consumer){
        if (!field.equals(supplier.get())) {
            return false;
        }
        fieildUpdate(supplier, consumer);
        return true;
    }

    private  <T> void fieildUpdate (Supplier<T> supplier, Consumer<T> consumer) {
        consumer.accept(supplier.get());
    }



}
