package com.example.springsecurity.domain.repository;

import com.example.springsecurity.domain.Gender;
import com.example.springsecurity.domain.MemberRole;
import com.example.springsecurity.domain.entity.Member;
import com.example.springsecurity.domain.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;


@SpringBootTest
class MemberFeatRepositoryTest {

    @Autowired
    MemberRepository memberRepository;


    @Autowired
    MemberFeatRepository memberFeatRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Test
    void test(){
        Member member = memberRepository.save(Member.builder()
                .username("user3")
                .password(passwordEncoder.encode("1111"))
                .nickName("곰돌쓰")
                .gender(Gender.MAN)
                .build());

        roleRepository.save(Role.builder()
                        .memberRole(MemberRole.ROLE_NORMAL)
                        .member_username("user3")
                .build());

    }

    @Test
    void test2(){
        memberFeatRepository.memberInfo("user1");
    }

    @Test
    void test3(){
        System.out.println(memberFeatRepository.memberInfo("user1"));
    }
}