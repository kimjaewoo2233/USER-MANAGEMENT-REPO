package com.example.springsecurity.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User {


    private Long id;

    private String username;

    private String password;

    private String authority;

}
