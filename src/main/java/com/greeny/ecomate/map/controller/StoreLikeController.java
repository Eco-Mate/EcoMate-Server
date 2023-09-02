package com.greeny.ecomate.map.controller;

import com.greeny.ecomate.map.dto.CreateStoreLikeDto;
import com.greeny.ecomate.map.dto.StoreLikeDto;
import com.greeny.ecomate.map.service.StoreLikeService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "StoreLike(에코매장 좋아요)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/ecoStores")
public class StoreLikeController {

    private final StoreLikeService storeLikeService;

    @Operation(summary = "좋아요 등록", description = "좋아요는 한 사용자당 1 번만 등록할 수 있습니다.")
    @ApiResponse(description = "에코 매장 좋아요 등록")
    @PostMapping("/like")
    public ApiUtil.ApiSuccessResult<StoreLikeDto> like(@Valid @RequestBody CreateStoreLikeDto createDto,
                                           HttpServletRequest req) {
        Long memberId = getMemberId(req);
        Long likeCnt = storeLikeService.like(createDto, memberId);
        return ApiUtil.success("좋아요 등록 성공", new StoreLikeDto(likeCnt, true));
    }

    @Operation(summary = "좋아요 취소", description = "사용자가 좋아요를 등록한 적이 있을 경우에만 좋아요를 취소할 수 있습니다.")
    @ApiResponse(description = "에코 매장 좋아요 취소")
    @PostMapping("/unlike")
    public ApiUtil.ApiSuccessResult<StoreLikeDto> unlike(@Valid @RequestBody CreateStoreLikeDto createDto,
                                                         HttpServletRequest req) {
        Long memberId = getMemberId(req);
        Long likeCnt = storeLikeService.unlike(createDto, memberId);
        return ApiUtil.success("좋아요 취소 성공", new StoreLikeDto(likeCnt, false));
    }

    private Long getMemberId(HttpServletRequest req) {
        return (Long) req.getAttribute("memberId");
    }

}
