package com.greeny.ecomate.member.repository;

import com.greeny.ecomate.member.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Long> {

    Optional<Level> findLevelByLevelName(String levelName);

}
