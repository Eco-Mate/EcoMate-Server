package com.greeny.ecomate.map.repository;

import com.greeny.ecomate.map.entity.EcoStore;
import com.greeny.ecomate.map.entity.StoreLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreLikeRepository extends JpaRepository<StoreLike, Long> {

    Optional<StoreLike> findByEcoStoreAndMemberId(EcoStore ecoStore, Long memberId);

}
