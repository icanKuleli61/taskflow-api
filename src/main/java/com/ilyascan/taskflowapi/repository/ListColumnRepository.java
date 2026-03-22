package com.ilyascan.taskflowapi.repository;

import com.ilyascan.taskflowapi.entity.ListColumn;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ListColumnRepository extends JpaRepository<ListColumn, UUID> {

    List<ListColumn> findByBoard_boardId(UUID boardId);

    @EntityGraph(attributePaths = {"board"})
    Optional<ListColumn> findWithBoardById(UUID id);

    @Query("""
    SELECT l FROM ListColumn l
    JOIN FETCH l.board b
    JOIN FETCH b.members
    WHERE l.id = :id
    """)
    Optional<ListColumn> findByIdWithBoardMembers(@Param("id") UUID id);
}

