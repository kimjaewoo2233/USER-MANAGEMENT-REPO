package com.example.springsecurity.util;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

@Component
public class JWTUtil {

        private String key = "test";

        public String generateToken(Map<String,Object> valueMap, int day){

            //Header 부분
            Map<String,Object> headers = new HashMap<>();
            headers.put("typ","JWT");    //token 타입은 JWT
            headers.put("alg","HS256"); //해싱 알고리즘 적용

            //PayLoad
            Map<String,Object> payLoads = new HashMap<>();  //payLoad(내용)
            payLoads.putAll(valueMap);

            int time = (10)*day;    //10분

            String jwtStr = Jwts.builder()      //문자열로 암호화 된 거 나감
                    .setHeader(headers)     //header 설정
                    .setClaims(payLoads)    //payload 설정
                    .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                    .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant()))
                    .signWith(SignatureAlgorithm.HS256,key.getBytes())  //키 값과 알고리즘 설정
                    .compact();

            return jwtStr;
        }

        public Map<String,Object> validation(String token) throws Exception{

                Map<String,Object>  claim = null;

                claim = Jwts.parser()
                        .setSigningKey(key.getBytes())  //key로 해체
                        .parseClaimsJws(token)  //토큰을 JWT로 다시 변경함
                        .getBody(); //payLoad 가져옴


                return claim;
        }

}
