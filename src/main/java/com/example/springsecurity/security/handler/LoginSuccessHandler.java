package com.example.springsecurity.security.handler;

import com.example.springsecurity.util.JWTUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            log.info("SUCCESS AUTHENTICATION - PREPARING FOR TOKEN");
            log.info("JWT TOKEN INFORMATION - {}",authentication.getPrincipal());

            Map<String, Object> claim = Map.of("username",authentication.getName());

            String accessToken = jwtUtil.generateToken(claim,1);
            String refreshToken = jwtUtil.generateToken(claim,30);


            Gson gson = new Gson();

            Map<String,String> keyMap = Map.of("accessToken",accessToken,"refreshToken",refreshToken);

            String jsonStr = gson.toJson(keyMap);

            response.getWriter().println(jsonStr);

            //request 객체를 보낸 곳으로 돌려줌
    }
}
