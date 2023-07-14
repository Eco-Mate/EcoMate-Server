package com.greeny.ecomate.challenge.controller;

import com.greeny.ecomate.challenge.dto.ChallengeDto;
import com.greeny.ecomate.challenge.dto.CreateChallengeRequestDto;
import com.greeny.ecomate.challenge.entity.Challenge;
import com.greeny.ecomate.challenge.service.ChallengeService;
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
    public Long createNewChallenge(@RequestBody CreateChallengeRequestDto dto) {
        return challengeService.createChallenge(dto);
    }

    @GetMapping("/{challengeId}")
    public Challenge getChallengeById(@PathVariable Long challengeId,
                                      HttpServletRequest req) {
        return challengeService.getChallengeById(challengeId);
    }

    @GetMapping
    public List<ChallengeDto> getAllChallenge() {
        return challengeService.findAllChallenge();
    }

    @PutMapping("/activeYn/{challengeId}")
    public Long updateChallengeActiveYn(@PathVariable Long challengeId,
                                        @RequestBody boolean activeYn,
                                        HttpServletRequest req) {
        challengeService.updateChallengeActiveYn(challengeId, activeYn);
        return challengeId;
    }

    @PutMapping("/{challengeId}")
    public Long updateChallenge(@PathVariable Long challengeId,
                                @Valid @RequestBody ChallengeDto challengeDto,
                                HttpServletRequest req) {

        challengeService.updateChallenge(challengeId, challengeDto);
        return challengeId;
    }

}
