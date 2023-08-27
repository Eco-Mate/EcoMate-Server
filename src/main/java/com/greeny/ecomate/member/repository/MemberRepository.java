package com.greeny.ecomate.member.repository;

import com.greeny.ecomate.member.entity.Member;
import com.greeny.ecomate.member.entity.QMember;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends
        JpaRepository<Member, Long>,
        QuerydslPredicateExecutor<Member>,
        QuerydslBinderCustomizer<QMember> {

    Optional<Member> findByMemberId(Long memberId);

    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByName(String name);

    Optional<Member> findByEmail(String email);

    List<Member> findByNicknameContaining(String nickname);

    Boolean existsMemberByEmail(String email);

    Boolean existsMemberByName(String name);

    Boolean existsMemberByNickname(String nickname);

    @Override
    default void customize(QuerydslBindings bindings, QMember root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.nickname);

        bindings.bind(root.nickname).first(StringExpression::containsIgnoreCase);
    }

}
