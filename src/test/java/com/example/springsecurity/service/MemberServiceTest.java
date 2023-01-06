package com.example.springsecurity.service;

import com.example.springsecurity.domain.Gender;
import com.example.springsecurity.domain.data.MemberRegisterData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void test(){
        memberService.register(MemberRegisterData
                .builder()
                        .username("userTester")
                        .password("1111")
                        .nickName("터키아이스크림")
                        .gender(Gender.MAN)
                .build());
    }
}