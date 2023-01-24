package com.example.springsecurity.domain.repository.impl;

import com.example.springsecurity.domain.dto.MemberDTO;
import com.example.springsecurity.domain.entity.Member;
import com.example.springsecurity.domain.entity.QMember;
import com.example.springsecurity.domain.entity.QRole;
import com.example.springsecurity.domain.repository.MemberFeatRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public class MemberFeatRepositoryImpl extends QuerydslRepositorySupport implements MemberFeatRepository {
    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     */
    public MemberFeatRepositoryImpl() {
        super(Member.class);
    }

    public Optional<MemberDTO> memberInfo(String username){
        QMember member = QMember.member;
        QRole role = QRole.role;
        JPQLQuery<Member> query = from(member);

        Member memberResult = query
                .leftJoin(member.memberRole,role)
                .fetchJoin()
                .where(member.username.eq(username))
                .fetch().stream().findFirst().get();


        return Optional.of(MemberDTO.toDTO(memberResult));
    }

    public Optional<Member> socialMemberInfo(String username){
            QMember member = QMember.member;
            QRole role = QRole.role;

            JPQLQuery<Member> query = from(member);



            Member memberResult = query
                    .leftJoin(member.memberRole,role)
                    .fetchJoin()
                    .where(member.username.eq(username).and(member.social.isFalse()))
                    .fetchOne();
            if(memberResult != null){
                return Optional.of(memberResult);
            }else{
                return Optional.empty();
            }
    }


}
