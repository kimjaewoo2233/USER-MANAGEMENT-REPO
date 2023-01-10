package com.example.securitytest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springsecurity.domain.entity.Member;

import java.time.Instant;

public class JWTUtil2 {

    private static final Algorithm ALGORITHM = Algorithm.HMAC256("key");
    private static final long AUTH_TIME = 20*60; //20분
    private static final long REGRESH_TIME = 60*60^24*7;


    private static String makeAuthToken(Member member){
        return JWT
                .create()
                .withSubject(member.getUsername())
                .withClaim("exp", Instant.now().getEpochSecond()+AUTH_TIME) //만료시간
                .sign(ALGORITHM);
    }

    private static String makeRefreshToken(Member member){
        return JWT
                .create()
                .withSubject(member.getUsername())
                .withClaim("exp",Instant.now().getEpochSecond()+REGRESH_TIME)
                .sign(ALGORITHM);
    }

    public static VerifyResult verify(String token){
        try{
            DecodedJWT verify = JWT.require(ALGORITHM).build().verify(token);
            //토큰검사
            return VerifyResult.builder().username(verify.getSubject()).build();
        }catch (Exception exception){
            DecodedJWT decodedJWT = JWT.decode(token);
            return VerifyResult.builder().success(false)
                    .username(decodedJWT.getSubject()).build();           }
    }
}
