package com.greeny.ecomate.challenge.controller;

import com.greeny.ecomate.challenge.dto.ChallengeDto;
import com.greeny.ecomate.challenge.dto.CreateChallengeRequestDto;
import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.challenge.service.ChallengeService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/challenges")
@RequiredArgsConstructor
@Tag(name = "Challenge")
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping("/form")
    public ApiUtil.ApiSuccessResult<Long> createNewChallenge(@Valid @RequestBody CreateChallengeRequestDto dto) {
        return ApiUtil.success("챌린지 생성 성공", challengeService.createChallenge(dto));
    }

    @GetMapping("/{challengeId}")
    public ApiUtil.ApiSuccessResult<Challenge> getChallengeById(@PathVariable Long challengeId,
                                      HttpServletRequest req) {
        return ApiUtil.success("챌린지 조회 성공", challengeService.getChallengeById(challengeId));
    }

    @GetMapping
    public ApiUtil.ApiSuccessResult<List<ChallengeDto>> getAllChallenge() {
        return ApiUtil.success("챌린지 전체 조회 성공", challengeService.findAllChallenge());
    }

    @PutMapping("/activeYn/{challengeId}")
    public ApiUtil.ApiSuccessResult<Long> updateChallengeActiveYn(@PathVariable Long challengeId,
                                        @RequestBody boolean activeYn,
                                        HttpServletRequest req) {
        challengeService.updateChallengeActiveYn(challengeId, activeYn);
        return ApiUtil.success("챌린지 활성화 여부 수정 성공", challengeId);
    }

    @PutMapping("/{challengeId}")
    public ApiUtil.ApiSuccessResult<Long> updateChallenge(@PathVariable Long challengeId,
                                @Valid @RequestBody ChallengeDto challengeDto,
                                HttpServletRequest req) {

        challengeService.updateChallenge(challengeId, challengeDto);
        return ApiUtil.success("챌린지 수정 성공", challengeId);
    }

    @DeleteMapping("/{challengeId}")
    public ApiUtil.ApiSuccessResult<String> deleteChallenge(@PathVariable Long challengeId,
                                  HttpServletRequest req) {
        challengeService.deleteChallenge(challengeId);
        return ApiUtil.success("챌린지 삭제 성공", "삭제되었습니다.");
    }

}
