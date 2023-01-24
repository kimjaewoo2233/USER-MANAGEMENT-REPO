package com.example.springsecurity.domain.dto;

import com.example.springsecurity.domain.Gender;
import com.example.springsecurity.domain.MemberRole;
import com.example.springsecurity.domain.entity.Member;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class MemberDTO extends User implements OAuth2User {
    private String username;

    private String password;

    private String nickName;

    private Gender gender;

    private boolean social;

    private Map<String, Object> props;  //소셜로그인 정보를 처리하는 멤버변수수




   private Set<MemberRole> memberRoleSet = new HashSet<>();
    public MemberDTO(String username,
                     String password,
                     Collection<? extends GrantedAuthority> authorities,
                     Set<MemberRole> roles,
                     String nickName,
                     Gender gender,boolean social) {
        super(username, password, authorities);
        this.username = username;
        this.password = password;
        this.memberRoleSet.addAll(roles);
        this.nickName = nickName;
        this.gender = gender;
        this.social = false;
    }

    public static Member toEntity(MemberDTO dto){
         //Member와 Role을 분리하였기 때문에 Role은 따로 받야한다.
            return Member.builder()
                    .username(dto.getUsername())
                    .password(dto.getPassword())
                    .nickName(dto.getNickName())
                    .gender(dto.getGender())
                    .social(dto.isSocial())
                    .build();
    }
    public static MemberDTO toDTO(Member entity){
        System.out.println(entity);
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        entity.getMemberRole().forEach(role -> {
            authorities.add(
                    new SimpleGrantedAuthority(
                            role.getMemberRole().getRoleString()));
        });


        return new MemberDTO(entity.getUsername(),
                entity.getPassword(),
                authorities,
                entity.getMemberRole().stream()
                        .map(role -> role.getMemberRole())
                        .collect(Collectors.toUnmodifiableSet()),
                entity.getNickName(),
                entity.getGender(), entity.isSocial());
    }


    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
