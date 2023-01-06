package com.example.springsecurity.domain.entity;


import com.example.springsecurity.domain.MemberRole;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "member_member_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_username",insertable = false,updatable = false)
    private Member member;


    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Column(name = "member_username")
    private String member_username;
}
