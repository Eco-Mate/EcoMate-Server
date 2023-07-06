package com.greeny.ecomate.posting.repository;

import com.greeny.ecomate.posting.entity.Board;
import com.greeny.ecomate.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByUser(User user);
}
