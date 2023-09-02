package com.greeny.ecomate.map.service;

import com.greeny.ecomate.exception.NotFoundException;
import com.greeny.ecomate.map.dto.CreateStoreLikeDto;
import com.greeny.ecomate.map.entity.EcoStore;
import com.greeny.ecomate.map.entity.StoreLike;
import com.greeny.ecomate.map.repository.EcoStoreRepository;
import com.greeny.ecomate.map.repository.StoreLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreLikeService {

    private final StoreLikeRepository storeLikeRepository;
    private final EcoStoreRepository ecoStoreRepository;

    public Long like(CreateStoreLikeDto dto, Long memberId) {
        EcoStore ecoStore = findEcoStoreById(dto.getStoreId());
        if(storeLikeRepository.findByEcoStoreAndMemberId(ecoStore, memberId).isPresent())
            throw new IllegalStateException("이미 좋아요 기록이 존재합니다.");

        ecoStore.increaseLike();

        StoreLike storeLike = new StoreLike(memberId, ecoStore);
        storeLikeRepository.save(storeLike);

        return ecoStore.getLikeCnt();
    }

    public Long unlike(CreateStoreLikeDto dto, Long memberId) {
        EcoStore ecoStore = findEcoStoreById(dto.getStoreId());
        StoreLike storeLike = storeLikeRepository.findByEcoStoreAndMemberId(ecoStore, memberId)
                .orElseThrow(() -> new NotFoundException("취소할 좋아요 기록이 없습니다."));

        ecoStore.decreaseLike();
        storeLikeRepository.delete(storeLike);

        return ecoStore.getLikeCnt();
    }

    private EcoStore findEcoStoreById(Long storeId) {
        return ecoStoreRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException("찾을 수 없는 에코 매장입니다."));
    }

}
