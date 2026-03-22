package com.ilyascan.taskflowapi.repository;

import com.ilyascan.taskflowapi.entity.CheckListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CheckListItemRepository  extends JpaRepository<CheckListItem, UUID> {
}
