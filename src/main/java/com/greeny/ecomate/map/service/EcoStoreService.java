package com.greeny.ecomate.map.service;

import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.exception.UnauthorizedAccessException;
import com.greeny.ecomate.map.dto.CreateEcoStoreRequestDto;
import com.greeny.ecomate.map.entity.EcoStore;
import com.greeny.ecomate.map.repository.EcoStoreRepository;
import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.entity.Role;
import com.greeny.ecomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EcoStoreService {

    private final EcoStoreRepository ecoStoreRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createEcoStore(CreateEcoStoreRequestDto createDto, Long memberId) {
        validateAuth(memberId);

        EcoStore ecoStore = EcoStore.builder()
                .memberId(memberId)
                .storeName(createDto.getStoreName())
                .latitude(createDto.getLatitude())
                .longitude(createDto.getLongitude())
                .address(createDto.getAddress())
                .likeCnt(0L)
                .build();

        ecoStoreRepository.save(ecoStore);
        return ecoStore.getStoreId();

    }

    private void validateAuth(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 사용자입니다."));

        if(member.getRole() != Role.ROLE_ADMIN)
            throw new UnauthorizedAccessException("권한이 없습니다.");
    }

}
