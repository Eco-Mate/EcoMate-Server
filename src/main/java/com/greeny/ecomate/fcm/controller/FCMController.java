package com.greeny.ecomate.fcm.controller;

import com.greeny.ecomate.fcm.dto.CreateFCMTokenDto;
import com.greeny.ecomate.fcm.service.FCMService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Tag(name = "FCM 알림")
@RequestMapping("/v1/fcm")
@RequiredArgsConstructor
public class FCMController {

    private final FCMService fcmService;

    @PutMapping
    public ApiUtil.ApiSuccessResult<Long> registerFCMToken(@Valid @RequestBody CreateFCMTokenDto createDto,
                                                             HttpServletRequest req) {
        Long memberId = getMemberId(req);
        return ApiUtil.success("FCM 토큰 등록 성공", fcmService.registerFCMToken(createDto, memberId).getMemberId());
    }

    Long getMemberId(HttpServletRequest req) {
        return (Long) req.getAttribute("memberId");
    }

}
