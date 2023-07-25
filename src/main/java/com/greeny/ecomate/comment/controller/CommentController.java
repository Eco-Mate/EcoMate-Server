package com.greeny.ecomate.comment.controller;

import com.greeny.ecomate.comment.dto.CommentDto;
import com.greeny.ecomate.comment.dto.CommentListDto;
import com.greeny.ecomate.comment.dto.CreateCommentRequestDto;
import com.greeny.ecomate.comment.service.CommentService;
import com.greeny.ecomate.posting.dto.BoardDto;
import com.greeny.ecomate.posting.entity.Board;
import com.greeny.ecomate.utils.api.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiUtil.ApiSuccessResult<Long> createComment(@Valid  @RequestBody CreateCommentRequestDto createRequest) {
        return ApiUtil.success("댓글 생성 성공", commentService.createComment(createRequest));
    }

    @GetMapping("/board/{boardId}")
    public ApiUtil.ApiSuccessResult<CommentListDto> getCommentByBoard(@PathVariable Long boardId) {
        return ApiUtil.success("게시물의 댓글 조회 성공", commentService.getCommentByBoard(boardId));
    }

}
