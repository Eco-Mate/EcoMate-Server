package com.greeny.ecomate.posting.repository;

import com.greeny.ecomate.posting.entity.Board;
import com.greeny.ecomate.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b join fetch b.member")
    List<Board> findAll(Pageable pageable);

    @Query("SELECT b FROM Board b join fetch b.member")
    List<Board> findAll();

    List<Board> findAllByMember(Member member);

    @Query("SELECT b FROM Board b join fetch b.member order by b.likeCnt desc")
    List<Board> findAllSortedByLikeCnt();

}
