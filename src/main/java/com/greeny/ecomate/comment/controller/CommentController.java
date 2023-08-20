package com.greeny.ecomate.comment.controller;

import com.greeny.ecomate.comment.dto.CommentListDto;
import com.greeny.ecomate.comment.dto.CreateCommentRequestDto;
import com.greeny.ecomate.comment.service.CommentService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @Operation(summary = "댓글 생성")
    public ApiUtil.ApiSuccessResult<Long> createComment(@Valid @RequestBody CreateCommentRequestDto createRequest,
                                                        HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("댓글 생성 성공", commentService.createComment(createRequest, memberId));
    }

    @GetMapping("/boards/{boardId}")
    @Operation(summary = "특정 게시물의 댓글 조회")
    public ApiUtil.ApiSuccessResult<CommentListDto> getCommentByBoard(@PathVariable Long boardId) {
        return ApiUtil.success("게시물의 댓글 조회 성공", commentService.getCommentByBoard(boardId));
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ApiUtil.ApiSuccessResult<String> deleteCommentById(@PathVariable Long commentId,
                                                              HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        commentService.deleteCommentById(commentId, memberId);
        return ApiUtil.success("댓글 삭제 성공", commentId + "이(가) 삭제되었습니다.");
    }

}
