package com.greeny.ecomate.like.repository;

import com.greeny.ecomate.like.entity.Like;
import com.greeny.ecomate.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByBoardAndMemberId(Board board, Long memberId);
}