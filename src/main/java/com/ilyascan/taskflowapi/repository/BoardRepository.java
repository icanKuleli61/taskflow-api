package com.ilyascan.taskflowapi.repository;

import com.ilyascan.taskflowapi.entity.Board;
import com.ilyascan.taskflowapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID> {

    List<Board> findByMembersContaining(User user);

    @Query("SELECT b FROM Board b LEFT JOIN FETCH b.members WHERE b.boardId = :id")
    Optional<Board> findByIdWithMembers(@Param("id") UUID id);

}
