package com.greeny.ecomate.board.entity;

import com.greeny.ecomate.base.BaseEntity;
import com.greeny.ecomate.member.entity.Member;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "challenge_id")
    private Long challengeId;

    @Column(name = "board_title", length = 50)
    private String boardTitle;

    @Column(name = "board_content", length = 500)
    private String boardContent;

    @Column(name = "image", length = 200)
    private String image;

    @Column(name = "like_cnt")
    private Long likeCnt;

    @Builder
    public Board(Member member, String boardTitle, String boardContent, String image, Long likeCnt, Long challengeId) {
        this.member = member;
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

    public void increaseLike() {
        this.likeCnt++;
    }

    public void decreaseLike() {
        this.likeCnt--;
    }
}
