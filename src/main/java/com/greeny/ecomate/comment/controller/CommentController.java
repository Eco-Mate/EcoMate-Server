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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiUtil.ApiSuccessResult<Long> createComment(@Valid @RequestBody CreateCommentRequestDto createRequest,
                                                        HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        return ApiUtil.success("댓글 생성 성공", commentService.createComment(createRequest, memberId));
    }

    @GetMapping("/boards/{boardId}")
    public ApiUtil.ApiSuccessResult<CommentListDto> getCommentByBoard(@PathVariable Long boardId) {
        return ApiUtil.success("게시물의 댓글 조회 성공", commentService.getCommentByBoard(boardId));
    }

    @DeleteMapping("/{commentId}")
    public ApiUtil.ApiSuccessResult<String> deleteCommentById(@PathVariable Long commentId,
                                                              HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        commentService.deleteCommentById(commentId, memberId);
        return ApiUtil.success("댓글 삭제 성공", commentId + "이(가) 삭제되었습니다.");
    }

}
