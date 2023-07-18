package com.greeny.ecomate.challenge.controller;

import com.greeny.ecomate.challenge.dto.CreateMyChallengeRequestDto;
import com.greeny.ecomate.challenge.dto.MyChallengeDto;
import com.greeny.ecomate.challenge.service.MyChallengeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/myChallenges")
@RequiredArgsConstructor
@Tag(name = "MyChallenge")
public class MyChallengeController {

    private final MyChallengeService myChallengeService;

    @PostMapping("/form")
    public Long createMyChallenge(@RequestBody CreateMyChallengeRequestDto dto) {
        return myChallengeService.createMyChallenge(dto);
    }

    @GetMapping("/{userId}")
    public List<MyChallengeDto> getMyChallengeByUserId(@PathVariable Long userId) {
        return myChallengeService.getMyChallengeByUserId(userId);
    }

}
