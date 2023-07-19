package com.greeny.ecomate.challenge.controller;

import com.greeny.ecomate.challenge.dto.CreateMyChallengeRequestDto;
import com.greeny.ecomate.challenge.dto.MyChallengeDto;
import com.greeny.ecomate.challenge.entity.MyChallenge;
import com.greeny.ecomate.challenge.service.MyChallengeService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @ApiResponse(description = "챌린지 새도전 or 재도전")
    @PostMapping("/form")
    public ApiUtil.ApiSuccessResult<Long> createMyChallenge(@Valid @RequestBody CreateMyChallengeRequestDto dto) {
        return ApiUtil.success("챌린지 도전 시작 성공", myChallengeService.createMyChallenge(dto));
    }

    @ApiResponse(description = "userId 별 도전 챌린지 전체 조회")
    @GetMapping("/user/{userId}")
    public ApiUtil.ApiSuccessResult<List<MyChallengeDto>> getAllMyChallengeByUserId(@PathVariable Long userId) {
        return ApiUtil.success("사용자별 도전 챌린지 조회 성공", myChallengeService.getAllMyChallengeByUserId(userId));
    }

    @ApiResponse(description = "userId 별 완료한 챌린지 전체 조회")
    @GetMapping("/user/{userId}/finish")
    public ApiUtil.ApiSuccessResult<List<MyChallengeDto>> getAllMyChallengeDoneByUserId(@PathVariable Long userId) {
        return ApiUtil.success("사용자별 완료한 챌린지 전체 조회 성공", myChallengeService.getAllMyChallengeDoneByUserId(userId));
    }

    @ApiResponse(description = "userId에 해당하는 사용자가 완료한 챌린지 수 조회")
    @GetMapping("/user/{userId}/finish/cnt")
    public ApiUtil.ApiSuccessResult<Long> getMyChallengeDoneCntByUserId(@PathVariable Long userId) {
        return ApiUtil.success("해당 사용자가 도전 완료한 챌린지 수 조회 성공", myChallengeService.getMyChallengeDoneCntByUserId(userId));
    }

    @ApiResponse(description = "userId에 해당하는 사용자가 진행 중인 챌린지 수 조회")
    @GetMapping("/user/{userId}/proceeding/cnt")
    public ApiUtil.ApiSuccessResult<Long> getMyChallengeProceedingCntByUserId(@PathVariable Long userId) {
        return ApiUtil.success("해당 사용자가 도전 중인 챌린지 수 조회 성공", myChallengeService.getMyChallengeProceedingCntByUserId(userId));
    }

    @ApiResponse(description = "myChallengeId로 도전 챌린지 단일 조회")
    @GetMapping("/{myChallengeId}")
    public ApiUtil.ApiSuccessResult<MyChallengeDto> getMyChallengeById(@PathVariable Long myChallengeId) {
        return ApiUtil.success("도전 챌린지 단일 조회 성공", myChallengeService.getMyChallengeById(myChallengeId));
    }

    @ApiResponse(description = "myChallengeId로 해당 챌린지 도전에 대한 인증 횟수 수정")
    @PutMapping("/{myChallengeId}")
    public ApiUtil.ApiSuccessResult<String> updateMyChallengeDoneCnt(@PathVariable Long myChallengeId) {
        return ApiUtil.success("챌린지 도전에 대한 인증 횟수 수정 성공", myChallengeService.updateMyChallengeDoneCnt(myChallengeId));
    }

}
