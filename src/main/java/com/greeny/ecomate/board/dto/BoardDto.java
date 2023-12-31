package com.greeny.ecomate.board.dto;

import com.greeny.ecomate.board.entity.Board;
import com.greeny.ecomate.utils.imageUtil.ImageUtil;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {

    private Long boardId;
    private Long memberId;
    private String nickname;
    private String profileImage;
    private String challengeTitle;
    private String boardTitle;
    private String boardContent;
    private String image;
    private Long likeCnt;
    private Boolean liked;
    private LocalDateTime createdDate;

    public BoardDto(Board board, String challengeTitle, Boolean liked) {
        this.boardId = board.getBoardId();
        this.memberId = board.getMember().getMemberId();
        this.nickname = board.getMember().getNickname();
        this.profileImage = ImageUtil.getProfileImage(board.getMember().getProfileImage());
        this.challengeTitle = challengeTitle;
        this.boardTitle = board.getBoardTitle();
        this.boardContent = board.getBoardContent();
        this.image = ImageUtil.getBoardImage(board.getImage());
        this.likeCnt = board.getLikeCnt();
        this.liked = liked;
        this.createdDate = board.getCreatedDate();
    }
}
