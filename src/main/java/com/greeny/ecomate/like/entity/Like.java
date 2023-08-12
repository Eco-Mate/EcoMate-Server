package com.greeny.ecomate.like.entity;

import com.greeny.ecomate.base.BaseEntity;
import com.greeny.ecomate.posting.entity.Board;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "like_id")
    private Long likeId;

    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public Like(Long memberId, Board board) {
        this.memberId = memberId;
        this.board = board;
    }
}
