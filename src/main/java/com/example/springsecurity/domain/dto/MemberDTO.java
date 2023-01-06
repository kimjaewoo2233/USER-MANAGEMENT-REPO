package com.example.springsecurity.domain.dto;

import com.example.springsecurity.domain.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class MemberDTO extends User {
    private String username;

    private String password;

    private String nickName;

    private Gender gender;

    public MemberDTO(String username,
                     String password,
                     Collection<? extends GrantedAuthority> authorities,
                     String nickName,
                     Gender gender) {
        super(username, password, authorities);
    }


}
