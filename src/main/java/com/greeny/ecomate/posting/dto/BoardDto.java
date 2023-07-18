package com.greeny.ecomate.posting.dto;

import com.greeny.ecomate.posting.entity.Board;
import lombok.Data;

@Data
public class BoardDto {
    private String nickname;
    private String boardTitle;
    private String boardContent;
    private String image;
    private Long likeCnt;

    public BoardDto(Board board) {
        this.nickname = board.getUser().getNickname();
        this.boardTitle = board.getBoardTitle();
        this.boardContent = board.getBoardContent();
        this.image = board.getImage();
        this.likeCnt = board.getLikeCnt();
    }
}