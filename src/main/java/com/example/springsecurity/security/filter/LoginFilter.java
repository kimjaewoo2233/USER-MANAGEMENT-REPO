package com.example.springsecurity.security.filter;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.http.HttpRequest;
import java.util.Map;


@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    public LoginFilter(AuthenticationManager manager){
        super(manager);

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
    throws AuthenticationException {
        if(request.getMethod().equalsIgnoreCase("GET")){
            log.info("THE GET METHOD IS NOT ALLOWED");

            return null;
        }
        Map<String,String> valueMap = jsonToMap(request);

        log.info("INPUT LOGIN USERNAME {}",valueMap.get("username"));

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        valueMap.get("username")
                        ,valueMap.get("password"));

        return getAuthenticationManager().authenticate(token);
    }



    public Map<String,String> jsonToMap(HttpServletRequest request){

        try(Reader reader = new InputStreamReader(request.getInputStream())){
            Gson gson = new Gson();

            return gson.fromJson(reader,Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
