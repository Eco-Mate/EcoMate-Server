package com.greeny.ecomate.posting.dto;

import com.greeny.ecomate.posting.entity.Board;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Data
public class BoardDto {

    private Long boardId;
    private Long userId;
    private String nickname;
    private String profileImage;
    private String challengeTitle;
    private String boardTitle;
    private String boardContent;
    private String image;
    private Long likeCnt;
    private LocalDateTime createdDate;

    public BoardDto(Board board, String challengeTitle, String s3Url, String boardDirectory) {
        this.boardId = board.getBoardId();
        this.userId = board.getUser().getUserId();
        this.nickname = board.getUser().getNickname();
        this.profileImage = board.getUser().getProfleImage();
        this.challengeTitle = challengeTitle;
        this.boardTitle = board.getBoardTitle();
        this.boardContent = board.getBoardContent();
        this.image = s3Url + "/" + boardDirectory + "/" + board.getImage();
        this.likeCnt = board.getLikeCnt();
        this.createdDate = board.getCreatedDate();
    }
}
