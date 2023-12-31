package com.greeny.ecomate.challenge.controller;

import com.greeny.ecomate.challenge.dto.ChallengeDto;
import com.greeny.ecomate.challenge.dto.CreateChallengeRequestDto;
import com.greeny.ecomate.challenge.dto.UpdateChallengeRequestDto;
import com.greeny.ecomate.challenge.service.ChallengeService;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.service.MemberService;
import com.greeny.ecomate.utils.api.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/challenges")
@RequiredArgsConstructor
@Tag(name = "Challenge")
public class ChallengeController {

    private final ChallengeService challengeService;
    private final MemberService memberService;

    @Operation(summary = "관리자 - 챌린지 생성", description = "account token이 필요합니다.")
    @ApiResponse(description = "(관리자) 챌린지 생성")
    @PostMapping(value = "/form", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ApiUtil.ApiSuccessResult<Long> createNewChallenge(@Valid @RequestPart CreateChallengeRequestDto dto,
                                                             @RequestPart MultipartFile file,
                                                             HttpServletRequest req) {
        Long memberId = getMemberId(req);
        Long challengeId = challengeService.createChallenge(dto, memberId, file);
        return ApiUtil.success("챌린지 생성 성공", challengeId);
    }

    @Operation(summary = "challengeId로 챌린지 단일 조회")
    @ApiResponse(description = "challengeId로 챌린지 단일 조회")
    @GetMapping("/{challengeId}")
    public ApiUtil.ApiSuccessResult<ChallengeDto> getChallengeById(@PathVariable Long challengeId, HttpServletRequest req) {
        return ApiUtil.success("챌린지 조회 성공", challengeService.getChallengeById(challengeId));
    }

    @Operation(summary = "챌린지 전체 조회", description = "account token이 필요합니다. 권한에 따라 활성화 여부에 의해 필터링된 챌린지 리스트를 조회할 수 있습니다.")
    @ApiResponse(description = "챌린지 전체 조회")
    @GetMapping
    public ApiUtil.ApiSuccessResult<List<ChallengeDto>> getAllChallenge(HttpServletRequest req) {
        Long memberId = getMemberId(req);
        Member member = memberService.getMemberById(memberId);
        return ApiUtil.success("챌린지 전체 조회 성공", challengeService.findAllChallenge(member));
    }

    @Operation(summary = "로그인된 사용자가 도전하지 않은 챌린지 전체 조회", description = "account token이 필요합니다.")
    @ApiResponse(description = "로그인된 사용자가 도전하지 않은 챌린지 전체 조회")
    @GetMapping("/unchallenged")
    public ApiUtil.ApiSuccessResult<List<ChallengeDto>> getBeforeStartChallenges(HttpServletRequest req) {
        Long memberId = getMemberId(req);
        Member member = memberService.getMemberById(memberId);
        return ApiUtil.success("도전하지 않은 챌린지 전체 조회 성공", challengeService.findBeforeStartChallenge(member));
    }

    @Operation(summary = "challengeId로 해당 챌린지를 도전하고 있는 회원 수 조회")
    @ApiResponse(description = "challengeId로 해당 챌린지를 도전하고 있는 회원 수 조회")
    @GetMapping("/cnt/{challengeId}")
    public ApiUtil.ApiSuccessResult<Long> getChallengeInCnt(@PathVariable Long challengeId) {
        return ApiUtil.success("챌린지 도전 회원 수 조회 성공", challengeService.getProceedingChallengeCnt(challengeId));
    }

    @Operation(summary = "관리자 - 챌린지 활성화 여부 수정", description = "account token이 필요합니다.")
    @ApiResponse(description = "(관리자) challengeId에 해당 하는 챌린지의 활성화 여부 수정")
    @PutMapping("/activeYn/{challengeId}")
    public ApiUtil.ApiSuccessResult<Long> updateChallengeActiveYn(@PathVariable Long challengeId,
                                        @RequestBody boolean activeYn,
                                        HttpServletRequest req) {
        Long memberId = getMemberId(req);
        challengeService.updateChallengeActiveYn(challengeId, activeYn, memberId);
        return ApiUtil.success("챌린지 활성화 여부 수정 성공", challengeId);
    }

    @Operation(summary = "관리자 - 챌린지 수정", description = "account token이 필요합니다. image는 수정 불가능합니다.")
    @ApiResponse(description = "(관리자) challengeId에 해당하는 챌린지 수정")
    @PutMapping("/{challengeId}")
    public ApiUtil.ApiSuccessResult<Long> updateChallenge(@PathVariable Long challengeId,
                                                          @Valid @RequestBody UpdateChallengeRequestDto dto,
                                                          HttpServletRequest req) {
        Long memberId = getMemberId(req);
        return ApiUtil.success("챌린지 수정 성공", challengeService.updateChallenge(challengeId, dto, memberId));
    }

    @Operation(summary = "관리자 - 챌린지 삭제", description = "account token이 필요합니다.")
    @ApiResponse(description = "(관리자) challengeId에 해당하는 챌린지 삭제")
    @DeleteMapping("/{challengeId}")
    public ApiUtil.ApiSuccessResult<String> deleteChallenge(@PathVariable Long challengeId, HttpServletRequest req) {
        Long memberId = getMemberId(req);
        challengeService.deleteChallenge(challengeId, memberId);
        return ApiUtil.success("챌린지 삭제 성공", "해당 챌린지가 삭제되었습니다.");
    }

    private Long getMemberId(HttpServletRequest req) {
        return (Long) req.getAttribute("memberId");
    }

}
