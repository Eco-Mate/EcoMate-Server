package com.greeny.ecomate.comment.entity;

import com.greeny.ecomate.base.BaseEntity;
import com.greeny.ecomate.board.entity.Board;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "content", length = 500)
    private String content;

    @Builder
    public Comment(Board board, Long memberId, String content) {
        this.board = board;
        this.memberId = memberId;
        this.content = content;
    }
}
