package com.greeny.ecomate.posting.entity;

import com.greeny.ecomate.base.BaseEntity;
import com.greeny.ecomate.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long boardId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "challenge_id")
    private Long challengeId;

    @Column(name = "board_title", length = 50)
    private String boardTitle;

    @Column(name = "board_content", length = 500)
    private String boardContent;

    @Column(name = "image", length = 100)
    private String image;

    @Column(name = "like_cnt")
    private Long likeCnt;

    @Builder
    public Board(User user, String boardTitle, String boardContent, String image, Long likeCnt, Long challengeId) {
        this.user = user;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.challengeId = challengeId;
        this.image = image;
        this.likeCnt = likeCnt;
    }

    public void update(String boardTitle, String boardContent) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
    }
}
