package com.greeny.ecomate.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommentListDto {
    List<CommentDto> commentList;
}
