package com.greeny.ecomate.boardSave.repository;

import com.greeny.ecomate.board.entity.Board;
import com.greeny.ecomate.board.entity.BoardSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardSaveRepository extends JpaRepository<BoardSave, Long> {

    @Query("SELECT s FROM BoardSave s join fetch s.board where s.memberId = :memberId")
    List<BoardSave> findByMemberId(Long memberId);

    Boolean existsBoardSaveByBoardAndMemberId(Board board, Long memberId);

    @Query("SELECT s FROM BoardSave s where s.memberId = :memberId and s.board.boardId = :boardId")
    Optional<BoardSave> findByBoardIdAndMemberId(Long boardId, Long memberId);
}
