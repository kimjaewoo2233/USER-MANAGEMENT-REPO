package com.example.securitytest;

import com.example.springsecurity.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration //이거 연습용이므로 삭제해야함
@RequiredArgsConstructor
public class TestSecurirtyConfig {

    private final MemberService memberService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);


        AuthenticationManager manager = authenticationManagerBuilder.build();

        JWTLoginFilter jwtLoginFilter = new JWTLoginFilter(manager);
        JWTCheckFilter jwtCheckFilter = new JWTCheckFilter(manager,memberService);

        http.csrf().disable().sessionManagement(session ->{
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        })
        .addFilterAt(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class) //LoginFilter를 뒤에 필터 자리에 (저 필터는 원래 로그인처리)
                .addFilterAt(jwtCheckFilter, BasicAuthenticationFilter.class)
        ;
        //*
        //  LoginFilter는 로그인을 처리해 줄 것이고
        //  체크필터는 매번 들고오는 토큰을 검증해 줄 것이다.
        // 특히나 필요한건 아니지만 프로세싱 url바꿔주는 것이 좋다 - LoginFilter에서
        // */

        return http.build();

    }

}
