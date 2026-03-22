package com.ilyascan.taskflowapi.service;

import com.ilyascan.taskflowapi.dto.ListColumnDto;
import com.ilyascan.taskflowapi.request.ListColumnUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface ListColumnService {
    ResponseEntity<?> listCreate(ListColumnDto listColumnDto, Authentication authentication);

    ResponseEntity<?> boardListGet(UUID boardId, Authentication authentication);

    ResponseEntity<?> listDelete(UUID listId, Authentication authentication);

    ResponseEntity<?> listUpdate(UUID listId, ListColumnUpdate listColumnUpdate, Authentication authentication);
}
