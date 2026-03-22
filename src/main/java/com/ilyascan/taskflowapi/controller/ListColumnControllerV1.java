package com.ilyascan.taskflowapi.controller;

import com.ilyascan.taskflowapi.dto.ListColumnDto;
import com.ilyascan.taskflowapi.request.ListColumnUpdate;
import com.ilyascan.taskflowapi.service.ListColumnService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/lists")
public class ListColumnControllerV1 {

    private final ListColumnService listColumnService;


    public ListColumnControllerV1(ListColumnService listColumnService) {
        this.listColumnService = listColumnService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> listCreate(@RequestBody @Valid ListColumnDto listColumnDto
            , Authentication authentication) {
        return listColumnService.listCreate(listColumnDto, authentication);
    }

    @GetMapping
    public ResponseEntity<?> boardListGet(@RequestParam("boardId") UUID boardId
    , Authentication authentication) {
        return listColumnService.boardListGet(boardId,authentication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> listDelete(@PathVariable(name = "id") UUID listId
    , Authentication authentication
    ) {
        return listColumnService.listDelete(listId,authentication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> listUpdate(@PathVariable(name = "id") UUID listId
            , @RequestBody @Valid ListColumnUpdate listColumnUpdate
    , Authentication authentication) {
        return listColumnService.listUpdate(listId,listColumnUpdate,authentication);
    }



}
