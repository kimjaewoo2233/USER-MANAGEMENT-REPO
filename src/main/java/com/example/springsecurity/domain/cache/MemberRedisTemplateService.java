package com.example.springsecurity.domain.cache;


import com.example.springsecurity.domain.dto.MemberDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberRedisTemplateService {

    private static final String CACHE_KEY = "MEM";

    private final RedisTemplate<String,Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private HashOperations<String,String,String> hashOperations;


    @PostConstruct
    public void init(){
        this.hashOperations = redisTemplate.opsForHash();
    }

    private String serializeMemberDTO(MemberDTO dto) throws JsonProcessingException {
        //dto를 받아서 json으로 변환한다 objectMapper를 사용해서
        return objectMapper.writeValueAsString(dto);
    }

    private MemberDTO deserializeMemberDTO(String jsonDTO) throws JsonProcessingException {
        //json(String)으로 받아서 dto로 매핑 - deserialize
        return objectMapper.readValue(jsonDTO, MemberDTO.class);
    }
}
