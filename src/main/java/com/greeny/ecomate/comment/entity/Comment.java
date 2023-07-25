package com.greeny.ecomate.comment.entity;

import com.greeny.ecomate.base.BaseEntity;
import com.greeny.ecomate.posting.entity.Board;
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

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "content", length = 500)
    private String content;

    @Builder
    public Comment(Board board, Long userId, String content) {
        this.board = board;
        this.userId = userId;
        this.content = content;
    }
}
