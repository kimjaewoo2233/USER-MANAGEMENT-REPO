package com.example.springsecurity.config;


import com.example.springsecurity.security.MemoryUserDetailsService;
import com.example.springsecurity.security.filter.LoginFilter;
import com.example.springsecurity.security.handler.LoginFailureHandler;
import com.example.springsecurity.security.handler.LoginSuccessHandler;
import com.example.springsecurity.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Slf4j
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class ProjectConfig  {

    private final MemoryUserDetailsService memoryUserDetailsService;

    private final JWTUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);


        authenticationManagerBuilder
                .userDetailsService(memoryUserDetailsService)
                .passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        http.authenticationManager(authenticationManager);
        //filterchain에 authenticationManager 등록시키기

        LoginSuccessHandler successHandler = new LoginSuccessHandler(jwtUtil);
        LoginFailureHandler failureHandler = new LoginFailureHandler();

        LoginFilter loginFilter = new LoginFilter(authenticationManager);
        loginFilter.setAuthenticationSuccessHandler(successHandler);
        loginFilter.setAuthenticationFailureHandler(failureHandler);

        log.info("--------------------security-----------------------");
        http.authorizeHttpRequests(auth -> {
               auth
                       .mvcMatchers(HttpMethod.POST,"/member/register").permitAll()
                       .mvcMatchers(HttpMethod.POST,"/upload").permitAll()
                       .mvcMatchers(HttpMethod.GET,"/view").permitAll()
                       .mvcMatchers(HttpMethod.GET,"/index").permitAll();
        }).formLogin().and()
                .csrf().disable()
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }//http://localhost:8080/login?logout 로그아웃주소 csrf 비활성화로 인해 GET으로도 로그아웃이 가능해짐



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }




    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }



}
