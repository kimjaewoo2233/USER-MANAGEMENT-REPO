package com.example.securitytest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    public JWTLoginFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);   //AuthentocationManager가 필요하다
        setFilterProcessesUrl("/login  ");
    }

    //SecurityFilterChain에서 filter를 교환했다 그래서 로그인 요청을 날릴 경우
    //request가 여기로 들어온다
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
     // UserLoginForm userLoginForm =
        // objectMapper.readValue(request.getInputStream(),UserLogin.class);
        /*
        * UsernamePasswordAuthenticationToken token =
        *           new UsernamePasswordAutehtnicationToken(
        *                   userLoginForm.getUasernam, passowrd, null);
        *
        *
        *
        * */
     //   return getAuthenticationManager().authenticate(token);
        return null;
        //AuthenticationManager에게 토큰을 검증해달라고 보내면 AuthenticationManager가
        //DaoAuthenticationProvider를 가지고 있는 AuthenticationProvider를 통해서
        //UserDetailsService를 타고 유저를 가져와서 패스워드 검증한 다음에
        // 컨테스트에서 유저를 넣어준다
        // 만약 성공했다면 성공했다는 메시지를 콜할 것이다 그게 successAuthentication 메소드( 컨텍스트에 정보를 넣음
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
             // User user = (User) authResult.getPrincipal();    여기에 인증된 User정보가 들어가있음

        //  response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer "+ JWTUtil.makeAuthToken(user));
        //  response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE); //json으로 보낸다.
        //  response.getOutputStream().write(objectMapper.writeValueAsBytes(user));
        //응답으로 이걸 보내줌
            super.successfulAuthentication(request,response,chain,authResult);
    }
}
