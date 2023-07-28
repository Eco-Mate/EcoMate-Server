package com.greeny.ecomate.security.detail;

import com.greeny.ecomate.member.entity.Member;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class MemberDetails extends User {

    public MemberDetails(Member member) {
        super(member.getEmail(), member.getPassword(),
                AuthorityUtils.createAuthorityList(member.getRole().toString()));
    }

    public String getEmail() {
        return this.getUsername();
    }

}
