package com.ilyascan.taskflowapi.controller;

import com.ilyascan.taskflowapi.dto.BoardDto;
import com.ilyascan.taskflowapi.request.BoardDeleteRequest;
import com.ilyascan.taskflowapi.request.BoardGetDailtsRequest;
import com.ilyascan.taskflowapi.request.BoardUserTransactions;
import com.ilyascan.taskflowapi.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/board")
public class BoardControllerV1 {


    private final BoardService boardService;

    public BoardControllerV1(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/me")
    public ResponseEntity<?> createBoard(@RequestBody @Valid BoardDto boardDto ) {
        return boardService.createBoard(boardDto);
    }


    @GetMapping("/boards")
    public ResponseEntity<?> getBoards(Authentication authentication) {
        return boardService.getBoards(authentication);
    }

    @GetMapping("/boards/details")
    public ResponseEntity<?> getBoardDetails(@RequestBody BoardGetDailtsRequest details
    , Authentication authentication) {
        return  boardService.getBoardDetails(details,authentication);
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteBoard(@RequestBody @Valid BoardDeleteRequest boardDeleteRequest
            , Authentication authentication) {
        return boardService.deleteBoard(boardDeleteRequest,authentication);
    }

    @PostMapping("/user/invite")
    public ResponseEntity<?>  boardUserEkle(@RequestBody @Valid BoardUserTransactions boardUserTransactions
            , Authentication authentication){
        return boardService.boardUserEkle(boardUserTransactions,authentication);
    }

    @DeleteMapping("/interest/members")
    public ResponseEntity<?> boardMembersInterest(@RequestBody @Valid BoardUserTransactions boardUserTransactions
            , Authentication authentication){
        return boardService.boardMembersInterest(boardUserTransactions, authentication);

    }





}
