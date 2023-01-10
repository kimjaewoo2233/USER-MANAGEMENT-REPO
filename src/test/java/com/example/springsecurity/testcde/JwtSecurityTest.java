package com.example.springsecurity.testcde;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class JwtSecurityTest {

        //토큰은 Base64로 인코딩 된 값이기에 디코딩으로 열어볼 수 있다.
    private void printToken(String token){
        String[] tokens = token.split("\\.");
        System.out.println("headers: "+ new String(Base64.getDecoder().decode(tokens[0])));
        System.out.println("payLoads: "+ new String(Base64.getDecoder().decode(tokens[1])));

    }

    @DisplayName("1. jjwt를 이용한 토큰 테스트")
    @Test   //okta 꺼
    void test_1(){  //compact() , parser() 를 이용
        String okta_token = Jwts.builder()
                .addClaims(Map.of("name","jaewoo",
                        "price","3000"))    //담을 값
                .signWith(SignatureAlgorithm.HS256,"keytest") //사인 (알고리즘방식, 키값)
                .compact(); //compact는 Base64로 토큰을 인코딩 해준다.
        System.out.println(okta_token);
        //eyJhbGciOiJIUzI1NiJ9.eyJwcmljZSI6IjMwMDAiLCJuYW1lIjoiamFld29vIn0.eaB_yDHQYQ8Xwkkvja_WD1AXf5INbzj59-pur2OvYek
        // . 으로 구분되며 header.payloads.signature이다 Base64로 되어있는 상태임
        printToken(okta_token);


        Jws<Claims> body =
                Jwts.parser().setSigningKey("keytest").parseClaimsJws(okta_token);
        System.out.println("JWS 출력");
        System.out.println(body);
    }

    @DisplayName("2. java-jwt를 이용한 토큰 테스트")
    @Test   //oauth0 꺼
    void test_2(){  //create() 와 require()를 이용한다.


        String oauth0_token = JWT.create()
                .withClaim("name","jaewoo")
                .withClaim("price",3000)
                .sign(Algorithm.HMAC256("keytest"));

        System.out.println(oauth0_token);
        printToken(oauth0_token);

        JWTVerifier jwtVerifier =
                JWT.require(Algorithm.HMAC256("keytest")).build();

        DecodedJWT verified = jwtVerifier.verify(oauth0_token);

        System.out.println(verified.getClaims());
    }

    /**
     * jwt에서 키를 넣는 다는 것은 이 키 값을 해싱하는데  java - jwt는 그 키값을
     * 해싱하지 않고 그냥 키값으로 사용한다 그러힉 떄문에 내부적으로 키값이 다르게 작용한다.
     * 그래서 두개를 같이 쓰는 건 어려움이 있다.
     *  두개를 맞춰주려면 키값을 해싱해줘야한다.
     * */


    @DisplayName("3. 만료시간 테스트")
    @Test
    void test_3() throws InterruptedException {
        //with메소드로 시작하는 것들은 JWT 스팩에서 규정하고 있는 것들이다 다 추가가능하다
       String token =  JWT.create().withSubject("jaewoo")
               .withNotBefore(  //이때부터 사용가능하다
                       new Date(System.currentTimeMillis() + 1000)
               )
               .withExpiresAt(
                new Date(System.currentTimeMillis()+3000)).sign(Algorithm.HMAC256("jaewoo"));

        //현재 시간부터 1초뒤 그리고 현재시간부터 3초뒤까지 사용가능하다 결국 2초가 사용가능한 토큰임
        Thread.sleep(4000); //토큰을 만료시킨다.
       try{
           DecodedJWT decodedJWT = JWT
                   .require(Algorithm.HMAC256("jaewoo")).build().verify(token);

           System.out.println(decodedJWT.getClaims());
       }catch (Exception e){
           //토큰이 만료된 경우 누군지는 다시 열어봐야 하므로 열어본다
           System.out.println("유효하지 않은 토큰입니다");
           DecodedJWT decodedJWT = JWT.decode(token);
           System.out.println(decodedJWT.getClaims());
       }
    }

    @DisplayName("내가 테스트 해본 코드")
    @Test
    void test_4() throws InterruptedException {
            String token = Jwts.builder()
                    .setNotBefore(new Date(System.currentTimeMillis() + 10000))
                    .setClaims(Map.of("id","jw"))
                    .signWith(SignatureAlgorithm.HS256,"key".getBytes())
                    .compact();

            System.out.println(token);
         //   Thread.sleep(4000);
            try{
                System.out.println("인증시간이 지난 후"+token);
            }catch (Exception e){
                System.out.println("토큰이 만료되었습니다");
                System.out.println(e.getMessage());
            }
    }
}
