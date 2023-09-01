package com.greeny.ecomate.member.entity;


import com.greeny.ecomate.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "level")
    private String level;

//    @Column(name = "open_yn")
//    private Boolean openYn;

    @Column(name = "total_tree_point")
    private Long totalTreePoint;

    @Column(name = "nickname", length = 30, unique = true)
    private String nickname;

    @Column(name = "profile_image", length = 200)
    private String profileImage;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "email", length = 30)
    private String email;

    @Column(name = "status_message", length = 255)
    private String statusMessage;

    @Column(name = "follower_cnt")
    private Long followerCnt;

    @Column(name = "following_cnt")
    private Long followingCnt;


    @Builder
    public Member(Role role, String level, Long totalTreePoint,
                  String nickname, String name, String password, String email, String statusMessage) {
        this.role = role;
        this.level = level;
        this.totalTreePoint = totalTreePoint;
        this.nickname = nickname;
        this.name = name;
        this.password = password;
        this.email = email;
        this.statusMessage = statusMessage;
        this.followerCnt = 0L;
        this.followingCnt = 0L;
    }

    public void updateTotalTreePoint(Long treePoint) {
        this.totalTreePoint = treePoint;
    }
    public void updateLevel(String levelName) { this.level = levelName; }

    public void updateFollowerCnt(Long cnt) { this.followerCnt = cnt; }
    public void updateFollowingCnt(Long cnt) { this.followingCnt = cnt; }

    public void update(String nickname, String email, String statusMessage) {
        this.nickname = nickname;
        this.email = email;
        this.statusMessage = statusMessage;
    }

    public void updateProfileImage(String fileName) {
        this.profileImage = fileName;
    }

    public void deleteProfileImage() {
        this.profileImage = null;
    }
}
