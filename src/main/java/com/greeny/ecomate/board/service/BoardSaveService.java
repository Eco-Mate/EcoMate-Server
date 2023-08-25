package com.greeny.ecomate.board.service;

import com.greeny.ecomate.board.entity.Board;
import com.greeny.ecomate.board.repository.BoardRepository;
import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.board.dto.CreateSaveLogRequestDto;
import com.greeny.ecomate.board.entity.BoardSave;
import com.greeny.ecomate.board.repository.BoardSaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardSaveService {

    private final BoardSaveRepository boardSaveRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public BoardSave createSaveLog(CreateSaveLogRequestDto createDto, Long memberId) {
        Board board = boardRepository.findById(createDto.getBoardId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다."));

        if (boardSaveRepository.existsBoardSaveByBoardAndMemberId(board, memberId)) {
            throw new IllegalStateException("이미 저장한 게시물입니다.");
        }

        BoardSave boardSave = BoardSave.builder()
                .board(board)
                .memberId(memberId)
                .build();
        return boardSaveRepository.save(boardSave);
    }
}
