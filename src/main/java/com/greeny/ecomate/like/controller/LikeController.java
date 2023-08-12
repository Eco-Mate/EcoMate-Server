package com.greeny.ecomate.like.controller;

import com.greeny.ecomate.like.dto.CreateLikeDto;
import com.greeny.ecomate.like.dto.LikeDto;
import com.greeny.ecomate.like.service.LikeService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "Like(게시물 좋아요)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/boards")
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "좋아요 등록", description = "좋아요는 한 사용자당 1 번만 등록할 수 있습니다.")
    @PostMapping("/like")
    public ApiUtil.ApiSuccessResult<LikeDto> like(@RequestBody @Valid CreateLikeDto requestDto,
                                                    HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        Long likeCnt = likeService.like(requestDto, memberId);
        return ApiUtil.success("좋아요 등록 성공", new LikeDto(likeCnt, true));
    }

    @Operation(summary = "좋아요 취소", description = "사용자가 좋아요를 등록한 적이 있을 경우에만 좋아요를 취소할 수 있습니다.")
    @PostMapping("/unlike")
    public ApiUtil.ApiSuccessResult<LikeDto> unlike(@RequestBody @Valid CreateLikeDto requestDto,
                                                    HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        Long likeCnt = likeService.unlike(requestDto, memberId);
        return ApiUtil.success("좋아요 취소 성공", new LikeDto(likeCnt, false));
    }

}
