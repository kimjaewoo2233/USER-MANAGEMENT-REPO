package com.example.springsecurity.controller;

import com.example.springsecurity.config.ProjectConfig;
import com.example.springsecurity.domain.Gender;
import com.example.springsecurity.domain.data.MemberRegisterData;
import com.example.springsecurity.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/*
*  - JPA 기능은 동작하지 않는다.
*  - 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에만 집중할 수 있는 어노테이션
*  - @Controller, @ControllerAdvice 사용가능
*  - 단, @Service, @Repository등은 사용할 수 없다.
* */
// - Security 제거하기 -- 실팸함
@WebMvcTest(value = MemberController.class,
                excludeFilters = {
                        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                                                classes = ProjectConfig.class)
                })
class MemberControllerTest {


        @Autowired
        private MockMvc mvc;

        @MockBean
        private MemberService memberService;
        @MockBean
        ProjectConfig projectConfig;

        @Test
        @WithMockUser
        void test() throws Exception {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonData = objectMapper.writeValueAsString(MemberRegisterData
                        .builder()
                        .username("userTester22")
                        .password("1122")
                        .nickName(" 남양아이스크림")
                        .gender(Gender.MAN)
                        .build());

                RequestBuilder requestBuilder = MockMvcRequestBuilders
                        .post("/member/register")
                        .content(jsonData)
                        .contentType(MediaType.APPLICATION_JSON).with(csrf());  //csrf 토큰이 없으면 403


            mvc.perform(requestBuilder)
                    .andExpect(status().isOk())
                    .andExpect(content().string("true"));
        }
}