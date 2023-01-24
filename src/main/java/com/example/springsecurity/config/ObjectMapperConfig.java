package com.example.springsecurity.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
    //Serialize
    //DeSerialize
    //둘 다 편하게 하려고

}
