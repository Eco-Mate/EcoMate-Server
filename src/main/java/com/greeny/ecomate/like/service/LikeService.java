package com.greeny.ecomate.like.service;

import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.like.dto.CreateLikeDto;
import com.greeny.ecomate.like.entity.Like;
import com.greeny.ecomate.like.repository.LikeRepository;
import com.greeny.ecomate.board.entity.Board;
import com.greeny.ecomate.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;


    public Long like(CreateLikeDto requestDto, Long memberId) {
        Board board = findBoardById(requestDto.getBoardId());
        if (likeRepository.findByBoardAndMemberId(board, memberId).isPresent()) {
            throw new IllegalStateException("이미 좋아요 기록이 존재합니다.");
        }

        board.increaseLike();

        Like like = new Like(memberId, board);
        likeRepository.save(like);

        return board.getLikeCnt();
    }

    public Long unlike(CreateLikeDto requestDto, Long memberId) {
        Board board = findBoardById(requestDto.getBoardId());
        Like like = likeRepository.findByBoardAndMemberId(board, memberId)
                .orElseThrow(() -> new NotFoundException("취소할 좋아요 기록이 없습니다."));

        board.decreaseLike();
        likeRepository.delete(like);

        return board.getLikeCnt();
    }

    private Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("찾을 수 없는 게시물입니다."));
    }

}
