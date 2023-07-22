package com.greeny.ecomate.posting.repository;

import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.posting.entity.Board;
import com.greeny.ecomate.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b join fetch b.user")
    List<Board> findAll(Pageable pageable);

    List<Board> findAllByUser(User user);
}
