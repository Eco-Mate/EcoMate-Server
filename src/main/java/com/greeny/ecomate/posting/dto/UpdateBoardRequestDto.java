package com.greeny.ecomate.posting.dto;

import lombok.Data;

@Data
public class UpdateBoardRequestDto {
    // image, challenge 는 수정 불가

    private Long boardId;

    private String boardTitle;

    private String boardContent;

}
