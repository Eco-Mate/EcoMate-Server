package com.greeny.ecomate.boardSave.service;

import com.greeny.ecomate.board.entity.Board;
import com.greeny.ecomate.board.repository.BoardRepository;
import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.boardSave.dto.CreateBoardSaveRequestDto;
import com.greeny.ecomate.board.entity.BoardSave;
import com.greeny.ecomate.boardSave.repository.BoardSaveRepository;
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
    public void createSaveLog(CreateBoardSaveRequestDto createDto, Long memberId) {
        Board board = boardRepository.findById(createDto.getBoardId())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다."));

        if (boardSaveRepository.existsBoardSaveByBoardAndMemberId(board, memberId)) {
            throw new IllegalStateException("이미 저장한 게시물입니다.");
        }

        BoardSave boardSave = BoardSave.builder()
                .board(board)
                .memberId(memberId)
                .build();
        boardSaveRepository.save(boardSave);
    }

    @Transactional
    public void deleteBoardSave(Long boardId, Long memberId) {
        BoardSave boardSave = boardSaveRepository.findByBoardIdAndMemberId(boardId, memberId)
                .orElseThrow(() -> new NotFoundException("삭제할 게시물 저장 기록이 없습니다."));

        boardSaveRepository.delete(boardSave);
    }

    public Boolean checkBoardSave(Long boardId, Long memberId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시물입니다."));

        return boardSaveRepository.existsBoardSaveByBoardAndMemberId(board, memberId);
    }
}
