package com.ilyascan.taskflowapi.service;


import com.ilyascan.taskflowapi.dto.BoardDto;
import com.ilyascan.taskflowapi.entity.Board;
import com.ilyascan.taskflowapi.request.BoardDeleteRequest;
import com.ilyascan.taskflowapi.request.BoardGetDailtsRequest;
import com.ilyascan.taskflowapi.request.BoardUserTransactions;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface BoardService {

    ResponseEntity<?> createBoard(BoardDto boardDto );

    ResponseEntity<?> getBoards(Authentication authentication);

    ResponseEntity<?> deleteBoard(BoardDeleteRequest boardDeleteRequest, Authentication authentication);

    ResponseEntity<?> boardUserEkle(BoardUserTransactions boardUserTransactions, Authentication authentication);

    ResponseEntity<?> boardMembersInterest(BoardUserTransactions boardUserTransactions, Authentication authentication);

    Board getAuthorizedBoard(Authentication authentication, String boardId);


    ResponseEntity<?> getBoardDetails(BoardGetDailtsRequest details, Authentication authentication);
}
