package com.ilyascan.taskflowapi.repository;


import com.ilyascan.taskflowapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findAllByUser_userId(UUID id);

    Optional<Task> findByTaskIdAndUser_UserId(UUID taskId, UUID userId);


}
