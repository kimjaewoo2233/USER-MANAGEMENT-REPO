package com.example.springsecurity.domain.entity;


import com.example.springsecurity.domain.Gender;
import com.example.springsecurity.domain.MemberRole;
import lombok.*;

import javax.persistence.*;
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

        private boolean social;

        @Builder.Default
        @OneToMany(fetch = FetchType.LAZY
                ,mappedBy = "member"
                ,cascade = CascadeType.ALL
                ,orphanRemoval = true)
        private Set<Role> memberRole = new HashSet<>();

        //여기서도 save 가능하지만 id값 때문에 일부로 뺌
}
