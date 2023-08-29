package com.greeny.ecomate.member.entity;

import com.greeny.ecomate.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Level extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private Long levelId;

    @Column(name = "level_name")
    private String levelName;

    @Column(name = "goal_tree_point")
    private String goalTreePoint;

}
