package com.example.springsecurity.domain.dto;

import com.example.springsecurity.domain.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class SecurityUser implements UserDetails {
    /*
    * SecurityUser 클래스는 시스템에서 사용자 세부정보를 스프링 시큐리티가 이해하는 UserDetails 계약에
    * 매핑하는 일만 했다.  SecurityUser에 User엔티티는 반드시 필요하다는 것을 지정하기 위해 final로 선언함
    * 사용할떄 생성자를 통해 지정해야한다.
    * SecurityUser 클래스로 User 엔티티 클래스를 장식하고 스프링 시큐리티 계약에 필요한
    * 코드를 추가해서 JPA 엔티티에 코드를 섰어 결과적으로 여러 다른 작업을 구현하지 않도록 함
    * */
    private final User user;

    private String authority;

    public SecurityUser(User user,String authority) {
        this.user = user;
        this.authority = authority;

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> authority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
