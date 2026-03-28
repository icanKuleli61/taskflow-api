package com.ilyascan.taskflowapi.repository;

import com.ilyascan.taskflowapi.entity.CheckListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CheckListItemRepository  extends JpaRepository<CheckListItem, UUID> {

    @Query("SELECT COALESCE(MAX(c.position), -1) FROM CheckListItem c WHERE c.task.taskId = :taskId")
    Integer findMaxPositionByTaskId(@Param("taskId") UUID taskId);

    List<CheckListItem> findAllByTaskTaskIdAndPositionGreaterThanOrderByPosition(UUID taskId, Integer position);

    List<CheckListItem> findByTask_taskId(UUID taskId);


    @Query("""
    SELECT c FROM CheckListItem c
    JOIN FETCH c.task t
    JOIN FETCH t.listColumn l
    JOIN FETCH l.board b
    WHERE c.chechListId = :id
    """)
    Optional<CheckListItem> findByIdWithDetails(@Param("id") UUID id);

}
