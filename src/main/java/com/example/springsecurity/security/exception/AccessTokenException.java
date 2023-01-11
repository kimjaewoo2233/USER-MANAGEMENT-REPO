package com.example.springsecurity.security.exception;

import com.google.gson.Gson;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class AccessTokenException extends RuntimeException{

        TOKEN_ERROR token_error;


        public AccessTokenException(TOKEN_ERROR token_error){
                super(token_error.name());
                this.token_error = token_error;

        }

        public void sendResponseError(HttpServletResponse response){

                response.setStatus(token_error.getStatus());    //해당되는 토큰 에러를 보낸다.,
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                 Gson gson =new Gson();
                String responseStr = gson.toJson(Map.of("msg",token_error.getMsg(),"time",new Date()));

                try{
                    response.getWriter().println(responseStr);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
}
