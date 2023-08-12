package com.greeny.ecomate.like.controller;

import com.greeny.ecomate.like.dto.CreateLikeDto;
import com.greeny.ecomate.like.dto.LikeDto;
import com.greeny.ecomate.like.service.LikeService;
import com.greeny.ecomate.utils.api.ApiUtil;
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

    @PostMapping("/like")
    public ApiUtil.ApiSuccessResult<LikeDto> like(@RequestBody @Valid CreateLikeDto requestDto,
                                                    HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        Long likeCnt = likeService.like(requestDto, memberId);
        return ApiUtil.success("좋아요 생성 성공", new LikeDto(likeCnt, true));
    }

    @PostMapping("/unlike")
    public ApiUtil.ApiSuccessResult<LikeDto> unlike(@RequestBody @Valid CreateLikeDto requestDto,
                                                    HttpServletRequest req) {
        Long memberId = (Long) req.getAttribute("memberId");
        Long likeCnt = likeService.unlike(requestDto, memberId);
        return ApiUtil.success("좋아요 취소 성공", new LikeDto(likeCnt, false));
    }

}
