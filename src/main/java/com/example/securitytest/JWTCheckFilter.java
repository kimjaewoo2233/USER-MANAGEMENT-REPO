package com.example.securitytest;

import com.example.springsecurity.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTCheckFilter extends BasicAuthenticationFilter {

    private MemberService memberService;


    public JWTCheckFilter(AuthenticationManager authenticationManager,MemberService service) {
        super(authenticationManager);
        this.memberService = service;
    }
    //request가 올때마다  토큰을 검사를 해서 시큐리티컨텍스트 홀더에 유저정보를 채워줘야 한다,.
    //BasicAuthenticationFilter가 비슷한 역할을 한다.

    //doFIlterInternal을 통해서 토큰에 대한 검사를 진행하면 될 듯하다.
   @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
                String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);

                if(bearer == null || !bearer.startsWith("Bearer ") ){
                    filterChain.doFilter(request,response); //헤더에 값이 없거나 이상한 값이면 흘려보내준다.
                    //흘려보내서 예외나면 거기서 로그인 페이지로 가라고 말한다
                    return;
                }

                String token = bearer.substring("Bearer ".length());
                VerifyResult result = JWTUtil2.verify(token);

                if(result.isSuccess()){ //인증된 사용자라면
                        //User user = loadUserByUsername(username); 을 통해 user 정보를 받는다
//                    UsernamePasswordAuthenticationToken token1 = new UsernamePasswordAuthenticationToken(
//                        user.getUsername,
//                        null, 비밀번호는 필요없음
//                            user.getAuthority 권한은필요
//                    ); 토큰을 만들어서 컨텍스트홀더에 넣어주면 된다.
                    //SecurityContextHolder.getContext().setAuthentication(token1);
                    filterChain.doFilter(request,response);//저장하고 다시 필터타고 감감
                }else{
                    //토큰 올바르지 않을 경우
                    throw new AuthenticationException("Token is not valid");
                }
       filterChain.doFilter(request,response);
   }
}
