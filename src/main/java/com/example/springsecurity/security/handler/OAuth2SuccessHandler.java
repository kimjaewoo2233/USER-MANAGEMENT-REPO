package com.example.springsecurity.security.handler;


import com.example.springsecurity.domain.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final PasswordEncoder passwordEncoder;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth Success Handler <=======>");
        log.info("AUTHENTICATION PRINCIPAL ===> {}",authentication.getPrincipal());

        MemberDTO dto = (MemberDTO) authentication.getPrincipal();  //인증객체

        // 소셜로그인을 통해 첫 로그인을 했을 경우

        if(dto.isSocial() && dto.getPassword().equals("1111")
                || passwordEncoder.matches("1111",dto.getPassword())){
            log.info("Sign in successfully with social login");

            response.sendRedirect("/member/modify");
            return;
        }else{
            response.sendRedirect("/"); //비밀번호가 1111이 아니라는 것은 소셜 아이디와 일치하는 회원있다는 것
        }

    }
}
