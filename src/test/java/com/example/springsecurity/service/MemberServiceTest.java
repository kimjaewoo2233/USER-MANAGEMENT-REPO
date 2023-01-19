package com.example.springsecurity.service;

import com.example.springsecurity.domain.Gender;
import com.example.springsecurity.domain.data.MemberRegisterData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    RedisTemplate redisTemplate;


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

    @DisplayName("레디스 스트링 테스트")
    @Test
    void test2(){
            ValueOperations valueOperations = redisTemplate.opsForValue();
            String key = "stringkey";
            String value = "hello";

            valueOperations.set(key,value);

            var result = valueOperations.get(key);
            Assertions.assertEquals(result,value);
    }

    @DisplayName("REDIS에 SET 자료구조 사용하기 - 이 테스트가 개인적으로 많이 사용될 듯함 ")
    @Test
    void redis_set_test(){
            //given
        //중복값을 자동제거하는 자료구조
        var setOperations = redisTemplate.opsForSet();  //SetOperations 타입
        var key = "setKey";

        //redis-cli에서는 smembers key 를 토ㅓㅇ해 값을 확인할 수 있다.
        //when
        setOperations.add(key,"h","e","l","l","o"); //중복체크가 되는지확인한다다


        //thn
        long size = setOperations.size(key);
        Assertions.assertEquals(4L,size);

    }
}