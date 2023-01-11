package com.example.springsecurity.security.filter;

import com.example.springsecurity.util.JWTUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
public class RefreshFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

            if(!"refreshToken".equals(request.getRequestURI())){
                    log.info("PASS THE REFRESHTOKEN");
                    filterChain.doFilter(request,response);
                    return;
            }


            //Refresh 토큰작동
            log.info("OUTPUTTING REFRESHTOKEN >>");
            //request에서 토큰 두개 추출
            Map<String,String> tokens = parserJSON(request);

            String accessToken = tokens.get("accessToken");
            String refreshToken = tokens.get("refreshToken");

            log.info("ACCESSTOKEN >>> {}",accessToken);
            log.info("REFRESHTOKEN >>> {}",refreshToken);


            try{
                jwtUtil.validation(accessToken);
            }catch (ExpiredJwtException e) {
                log.info("ACCESS TOKEN HAS EXPIRED");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


    }

    private Map<String,String> parserJSON(HttpServletRequest request){
            try(Reader reader = new InputStreamReader(request.getInputStream())){
                Gson gson =new Gson();
                return gson.fromJson(reader,Map.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
