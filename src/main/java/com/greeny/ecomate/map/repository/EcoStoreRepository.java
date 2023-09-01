package com.greeny.ecomate.map.repository;

import com.greeny.ecomate.map.entity.EcoStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EcoStoreRepository extends JpaRepository<EcoStore, Long> {

    @Query("select e from EcoStore e where DEGREES(ACOS(SIN(RADIANS(e.latitude))*SIN(RADIANS(:latitude)) + COS(RADIANS(e.latitude))*COS(RADIANS(:latitude))*COS(RADIANS(e.longitude-:longitude))))* 60*1.1515*1609.344 < 10000")
    List<EcoStore> findEcoStoresByMemberLocation(@Param(value = "latitude") Double latitude,
                                                 @Param(value = "longitude") Double longitude);

}
