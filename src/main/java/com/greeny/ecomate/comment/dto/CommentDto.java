package com.greeny.ecomate.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {

    private Long commentId;
    private String nickname;
    private String content;

}
