package com.ilyascan.taskflowapi.repository;


import com.ilyascan.taskflowapi.entity.ListColumn;
import com.ilyascan.taskflowapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {


    List<Task> findByListColumn(ListColumn list);

    @Query("""
    SELECT t FROM Task t
    JOIN FETCH t.listColumn l
    JOIN FETCH l.board b
    LEFT JOIN FETCH t.checklistItem
    WHERE t.taskId = :id
""")
    Optional<Task> findByIdWithDetails(UUID id);

}
