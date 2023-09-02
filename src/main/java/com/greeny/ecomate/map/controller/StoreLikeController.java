package com.greeny.ecomate.map.controller;

import com.greeny.ecomate.map.dto.CreateStoreLikeDto;
import com.greeny.ecomate.map.dto.StoreLikeDto;
import com.greeny.ecomate.map.service.StoreLikeService;
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

@Tag(name = "StoreLike(에코매장 좋아요)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/ecoStores")
public class StoreLikeController {

    private final StoreLikeService storeLikeService;

    @Operation(summary = "좋아요 등록", description = "좋아요는 한 사용자당 1 번만 등록할 수 있습니다.")
    @PostMapping("/like")
    public ApiUtil.ApiSuccessResult<StoreLikeDto> like(@Valid @RequestBody CreateStoreLikeDto createDto,
                                           HttpServletRequest req) {
        Long memberId = getMemberId(req);
        Long likeCnt = storeLikeService.like(createDto, memberId);
        return ApiUtil.success("좋아요 등록 성공", new StoreLikeDto(likeCnt, true));
    }

    private Long getMemberId(HttpServletRequest req) {
        return (Long) req.getAttribute("memberId");
    }

}
