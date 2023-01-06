package com.example.springsecurity.domain.entity;


import com.example.springsecurity.domain.Gender;
import com.example.springsecurity.domain.MemberRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {

        @Id
        private String username;

        private String password;

        private String nickName;

        @Enumerated(EnumType.STRING)
        private Gender gender;

        @Builder.Default
        @ElementCollection
        private Set<MemberRole> memberRole = new HashSet<>();
}
