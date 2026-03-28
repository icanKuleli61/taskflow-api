package com.ilyascan.taskflowapi.service;

import com.ilyascan.taskflowapi.dto.CheckListItemDto;
import com.ilyascan.taskflowapi.request.CheckListItemToggleRequest;
import com.ilyascan.taskflowapi.request.CheckListItemUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface CheckListItemService {


    ResponseEntity<?> createChecklistItem(String taskId, CheckListItemDto checkListItemDto, Authentication authentication);

    ResponseEntity<?> getChecklistItem(String taskId, Authentication authentication);

    ResponseEntity<?> updateChecklistItem(String id, CheckListItemUpdate checkListItemUpdate, Authentication authentication);

    ResponseEntity<?> deleteChecklistItem(String id, Authentication authentication);

    ResponseEntity<?> togleChecklistItem(String id, CheckListItemToggleRequest checkListItemToggleRequest, Authentication authentication);
}
