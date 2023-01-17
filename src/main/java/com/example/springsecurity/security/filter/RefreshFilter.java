package com.example.springsecurity.security.filter;

import com.example.springsecurity.security.exception.ErrorCase;
import com.example.springsecurity.security.exception.RefreshTokenException;
import com.example.springsecurity.util.JWTUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.Date;
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
                throw new RefreshTokenException(ErrorCase.NO_ACCESS);
            }

            Map<String,Object> refreshClaims = null;

            //refreshToken을 검증
            try {

                refreshClaims = checkRefreshToken(refreshToken);

            } catch (RefreshTokenException refreshTokenException) {
                refreshTokenException.sendResponseError(response);  //일부러 이 예외를 일으켜야 하기 떄문에 메소드를 사용함
                return;
            }

            //RefreshToken의 유효시간이 얼마남지 않은 경우
            Integer exp = (Integer) refreshClaims.get("exp");   //토큰 만료시간을 뺴온다.

            Date expTime = new Date(Instant.ofEpochMilli(exp).toEpochMilli() * 1000);
            //해당 토큰에 만료시간을 가져온다.
            Date current = new Date(System.currentTimeMillis());
            //현재시간과 비교하기위해 현재시간도 가져온다.

            /*
            * 만료시간과 현재 시간의 간격을 계산한다.
            * 만일 3일 미만인 경우에는 RefreshToken과 AccessToken 모두 재생성
            * */
            long gapTime = (expTime.getTime() - current.getTime());

            String username = (String) refreshClaims.get("username"); //email을 이용해서 토큰을 다시 발급해야하낟.

            String accessTokenValue = jwtUtil.generateToken(Map.of("username",username),1);  //access 1일
            String refreshTokenValue = tokens.get("refreshToken");

                //3일 계싼
            if(gapTime < (1000 * 60 * 60 * 24 * 3)){
                log.info("new RefreshToken required...");
                refreshTokenValue = jwtUtil.generateToken(Map.of("username",username),30);
             }

             sendTokens(accessTokenValue,refreshTokenValue,response);


    }

    private Map<String,String> parserJSON(HttpServletRequest request){
            try(Reader reader = new InputStreamReader(request.getInputStream())){
                Gson gson =new Gson();
                return gson.fromJson(reader,Map.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    private Map<String,Object> checkRefreshToken(String refreshToken) throws RefreshTokenException{
        try{
            Map<String,Object> values = jwtUtil.validation(refreshToken);
            return values;
        }catch (ExpiredJwtException expiredJwtException){
            throw new RefreshTokenException(ErrorCase.OLD_REFRESH);
        }catch(MalformedJwtException malformedJwtException){
            throw new RefreshTokenException(ErrorCase.NO_REFRESH);
        }catch (Exception e){
            new RefreshTokenException(ErrorCase.NO_REFRESH);
        }
        return null;
    }

    private void sendTokens(String accessTokenValue,String refreshTokenValue,HttpServletResponse response){
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            Gson gson = new Gson();

            String jsonStr = gson.toJson(
                    Map.of("accessToken",accessTokenValue,"refreshToken",refreshTokenValue)
            );

            try{
                response.getWriter().println(jsonStr);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
