package com.greeny.ecomate.challenge.controller;

import com.greeny.ecomate.challenge.dto.CreateMyChallengeRequestDto;
import com.greeny.ecomate.challenge.dto.MyChallengeDto;
import com.greeny.ecomate.challenge.service.MyChallengeService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/myChallenges")
@RequiredArgsConstructor
@Tag(name = "MyChallenge")
public class MyChallengeController {

    private final MyChallengeService myChallengeService;

    @PostMapping("/form")
    public ApiUtil.ApiSuccessResult<Long> createMyChallenge(@Valid @RequestBody CreateMyChallengeRequestDto dto) {
        return ApiUtil.success("챌린지 도전 시작 성공", myChallengeService.createMyChallenge(dto));
    }

    @GetMapping("/{userId}")
    public ApiUtil.ApiSuccessResult<List<MyChallengeDto>> getMyChallengeByUserId(@PathVariable Long userId) {
        return ApiUtil.success("사용자별 도전 챌린지 조회 성공", myChallengeService.getMyChallengeByUserId(userId));
    }

    @PutMapping("/{myChallengeId}")
    public ApiUtil.ApiSuccessResult<String> updateMyChallengeDoneCnt(@PathVariable Long myChallengeId) {
        return ApiUtil.success("챌린지 도전 횟수 수정 성공", myChallengeService.updateMyChallengeDoneCnt(myChallengeId));
    }

}
