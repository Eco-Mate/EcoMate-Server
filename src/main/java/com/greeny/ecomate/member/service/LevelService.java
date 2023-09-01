package com.greeny.ecomate.member.service;

import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.exception.UnauthorizedAccessException;
import com.greeny.ecomate.member.dto.CreateLevelRequestDto;
import com.greeny.ecomate.member.dto.LevelDto;
import com.greeny.ecomate.member.dto.UpdateLevelRequestDto;
import com.greeny.ecomate.member.entity.Level;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.entity.Role;
import com.greeny.ecomate.member.repository.LevelRepository;
import com.greeny.ecomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LevelService {

    private final LevelRepository levelRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createLevel(CreateLevelRequestDto dto, Long memberId) {
        validateAuth(memberId);

        Level level = Level.builder()
                .levelName(dto.getLevelName())
                .goalTreePoint(dto.getGoalTreePoint())
                .build();
        levelRepository.save(level);

        return level.getLevelId();
    }

    public LevelDto getLevel(String levelName) {
        Level level = findLevelByLevelName(levelName);
        return createLevelDto(level);
    }

    @Transactional
    public Long updateLevel(Long levelId, UpdateLevelRequestDto dto, Long memberId) {
        Level level = findLevelByLevelId(levelId);
        validateAuth(memberId);
        level.update(dto.getLevelName(), dto.getGoalTreePoint());
        return level.getLevelId();
    }

    private LevelDto createLevelDto(Level level) {
        Level nextLevel = findLevelByLevelId(level.getLevelId()+1);
        return new LevelDto(level.getLevelName(), level.getGoalTreePoint(), nextLevel.getLevelName());
    }

    private void validateAuth(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));

        if(member.getRole() != Role.ROLE_ADMIN)
            throw new UnauthorizedAccessException("삭제 권한이 없습니다.");
    }

    private Level findLevelByLevelId(Long levelId) {
        return levelRepository.findById(levelId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 레벨입니다."));
    }

    private Level findLevelByLevelName(String levelName) {
        return levelRepository.findLevelByLevelName(levelName)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 레벨입니다."));
    }

}
