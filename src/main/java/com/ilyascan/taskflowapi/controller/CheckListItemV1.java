package com.ilyascan.taskflowapi.controller;


import com.ilyascan.taskflowapi.dto.CheckListItemDto;
import com.ilyascan.taskflowapi.request.CheckListItemToggleRequest;
import com.ilyascan.taskflowapi.request.CheckListItemUpdate;
import com.ilyascan.taskflowapi.service.CheckListItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/chechListItem")
public class CheckListItemV1 {

    private final CheckListItemService checkListItemService;

    public CheckListItemV1(CheckListItemService checkListItemService) {
        this.checkListItemService = checkListItemService;
    }

    @PostMapping("/tasks/{taskId}")
    public ResponseEntity<?>  createChecklistItem(
            @PathVariable String taskId,
            @RequestBody @Valid CheckListItemDto checkListItemDto,
            Authentication authentication
            ){
        return checkListItemService.createChecklistItem(taskId,checkListItemDto,authentication);
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<?> getChecklistItem(
            @PathVariable String taskId
            , Authentication authentication
    ){
        return checkListItemService.getChecklistItem(taskId,authentication);
    }

    @PutMapping("checklist/{id}")
    public ResponseEntity<?> updateChecklistItem(@PathVariable String id
        , @RequestBody CheckListItemUpdate checkListItemUpdate
        , Authentication authentication){

        return checkListItemService.updateChecklistItem(id,checkListItemUpdate, authentication );
    }

    @PutMapping("toggle/{id}")
    public ResponseEntity<?> togleChecklistItem(@PathVariable String id
            , @RequestBody CheckListItemToggleRequest checkListItemToggleRequest
            , Authentication authentication){
        return checkListItemService.togleChecklistItem(id,checkListItemToggleRequest,authentication);
    }


    @DeleteMapping("chechlist/{id}")
    public ResponseEntity<?> deleteChecklistItem(@PathVariable String id
        , Authentication authentication){
        return checkListItemService.deleteChecklistItem(id,authentication);

    }







}
