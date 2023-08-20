package com.greeny.ecomate.comment.repository;

import com.greeny.ecomate.comment.entity.Comment;
import com.greeny.ecomate.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBoard_BoardId(Long boardId);

    List<Comment> findAllByBoard(Board board);
}
