package com.greeny.ecomate.user.entity;


import com.greeny.ecomate.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;

    @Column(name = "open_yn")
    private Boolean openYn;

    @Column(name = "total_tree_point")
    private Long totalTreePoint;

    @Column(name = "nickname", length = 30, unique = true)
    private String nickname;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email", length = 30)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

}
