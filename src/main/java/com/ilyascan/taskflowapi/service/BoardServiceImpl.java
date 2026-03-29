package com.ilyascan.taskflowapi.service;

import com.ilyascan.taskflowapi.Security.CustomUserDetails;
import com.ilyascan.taskflowapi.dto.BoardDto;
import com.ilyascan.taskflowapi.entity.Board;
import com.ilyascan.taskflowapi.entity.User;
import com.ilyascan.taskflowapi.exception.CustomException;
import com.ilyascan.taskflowapi.exception.ExceptionError;
import com.ilyascan.taskflowapi.handler.ApiResponce;
import com.ilyascan.taskflowapi.mapper.BoardMapper;
import com.ilyascan.taskflowapi.repository.BoardRepository;
import com.ilyascan.taskflowapi.repository.UserRepository;
import com.ilyascan.taskflowapi.request.BoardDeleteRequest;
import com.ilyascan.taskflowapi.request.BoardGetDailtsRequest;
import com.ilyascan.taskflowapi.request.BoardUserTransactions;
import com.ilyascan.taskflowapi.responce.BoardDtoResponce;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardRepository boardRepository, UserRepository userRepository, BoardMapper boardMapper) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.boardMapper = boardMapper;
    }


    @Override
    public ResponseEntity<?> createBoard(BoardDto boardDto) {
        Board entity = toEntity(boardDto);
        boardRepository.save(entity);
        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message("Calışma alanı başarıyla ")
        );
    }

    @Override
    public ResponseEntity<?> getBoards(Authentication authentication) {

        User user = authenticationBreakGetUser(authentication);
        List<Board> userBoardList = boardRepository.findByMembersContaining(user);
        if (userBoardList.isEmpty()) {
            throw new CustomException(ExceptionError.BOARD_USER_NOT_FOUND);
        }
        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message("Kullanıcıların calışma alanları getirildi.")
                .data(userBoardList)
                .build()
        );
    }

    @Override
    public ResponseEntity<?> deleteBoard(BoardDeleteRequest boardDeleteRequest, Authentication authentication) {
        User user = authenticationBreakGetUser(authentication);
        Board board = boardRepository.findById(UUID.fromString(boardDeleteRequest.getBoardId())).orElseThrow(
                () -> new CustomException(ExceptionError.BOARD_NOT_FOUND)
        );

        if (!board.getOwner().getUserId().equals(user.getUserId())) {
            throw new CustomException(ExceptionError.USER_DELETE_BOARD_FALSE);
        }
        boardRepository.delete(board);

        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message("Calışma alanı başarı ile silindi")
                .timestamp(new Date())
                .build()
        );
    }

    @Transactional
    @Override
    public ResponseEntity<?> boardUserEkle(BoardUserTransactions boardUserTransactions, Authentication authentication) {
        User user = authenticationBreakGetUser(authentication);
        Board board = boardRepository.findByIdWithMembers(UUID.fromString(boardUserTransactions.getBoardId())).orElseThrow(
                () -> new CustomException(ExceptionError.BOARD_NOT_FOUND)
        );
        if (!board.getOwner().getUserId().equals(user.getUserId())) {
            throw new CustomException(ExceptionError.BOARD_ADD_USER_FOUND);
        }

        User byEmail = userRepository.findByEmail(boardUserTransactions.getEmail());
        if (byEmail == null) {
            throw new CustomException(ExceptionError.USER_NOT_FOUND);
        }

        if (board.getMembers().contains(byEmail)) {
             throw new CustomException(ExceptionError.BOARD_IS_USER);
        }

        board.getMembers().add(byEmail);
        boardRepository.save(board);

        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message(byEmail.getEmail() + " Mail'li kullanıcı Calışma alanına başarıyla eklendi")
                .timestamp(new Date())
                .build()
        );
    }

    @Override
    public ResponseEntity<?> boardMembersInterest(BoardUserTransactions boardUserTransactions, Authentication authentication) {
        User user = authenticationBreakGetUser(authentication);
        Board board = boardRepository.findByIdWithMembers(UUID.fromString(boardUserTransactions.getBoardId())).orElseThrow(
                () -> new CustomException(ExceptionError.BOARD_NOT_FOUND)
        );
        if (!board.getOwner().getUserId().equals(user.getUserId())) {
            throw new CustomException(ExceptionError.BOARD_ADD_USER_FOUND);
        }

        User byEmail = userRepository.findByEmail(boardUserTransactions.getEmail());
        if (byEmail == null) {
            throw new CustomException(ExceptionError.USER_NOT_FOUND);
        }

        if (!board.getMembers().contains(byEmail)) {
            throw new CustomException(ExceptionError.BOARD_IS_NOT_USER);
        }

        board.getMembers().remove(byEmail);
        boardRepository.save(board);

        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message(byEmail.getEmail()+" emaile sahip kullanıcı başarıyla silindi")
                .timestamp(new Date())
                .build()
        );
    }

    public Board getAuthorizedBoard(Authentication authentication, String boardId) {
        User user = authenticationBreakGetUser(authentication);
        Board board = boardRepository.findByIdWithMembers(UUID.fromString(boardId)).orElseThrow(
                () -> new CustomException(ExceptionError.BOARD_NOT_FOUND)
        );
        boolean isMember = board.getMembers().stream().anyMatch(member -> member.getUserId().equals(user.getUserId()));

        if (!isMember) {
            throw new CustomException(ExceptionError.UNAUTHORIZED);
        }
        return board;
    }

    @Override
    public ResponseEntity<?> getBoardDetails(BoardGetDailtsRequest details, Authentication authentication) {
        User user = authenticationBreakGetUser(authentication);
        UUID boardId = UUID.fromString(details.getBoardId());
        Board board = boardRepository.findBoardWithDetails(boardId).orElseThrow(
                () -> new CustomException(ExceptionError.BOARD_NOT_FOUND)
        );

        if (!board.getOwner().getUserId().equals(user.getUserId())) {
            throw new CustomException(ExceptionError.UNAUTHORIZED   );
        }

        BoardDtoResponce map = boardMapper.map(board);

        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message("Başarıyla calışma alanındaki bilgiler getirildi.")
                .data(map)
                .timestamp(new Date())
                .build()
        );
    }


    public Board toEntity(BoardDto boardDto) {
        return Board.builder()
                .boardName(boardDto.getBoardName())
                .owner(getUser(UUID.fromString(boardDto.getUserId())))
                .boardDescription(boardDto.getBoardDescription())
                .build();
    }


    private User authenticationBreakGetUser(Authentication authentication) {
        if (authentication == null) {
            throw new CustomException(ExceptionError.USER_NOT_FOUND_LOGIN);
        }
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        if (principal == null) {
            throw new CustomException(ExceptionError.USER_NOT_FOUND);
        }
        UUID userId = principal.getUser().getUserId();
        return getUser(userId);
    }

    private User getUser(UUID userId) {

        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ExceptionError.USER_NOT_FOUND)
        );
    }


}
