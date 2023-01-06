package com.example.springsecurity.domain.data;


import com.example.springsecurity.domain.Gender;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterData {

    private String username;

    private String password;

    private String nickName;

    private Gender gender;

}
