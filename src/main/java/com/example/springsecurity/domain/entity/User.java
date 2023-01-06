package com.example.springsecurity.domain.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
