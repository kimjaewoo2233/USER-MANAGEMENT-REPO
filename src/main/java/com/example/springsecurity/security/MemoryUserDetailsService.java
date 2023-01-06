

package com.example.springsecurity.security;

import com.example.springsecurity.domain.dto.MemberDTO;
import com.example.springsecurity.domain.entity.Member;
import com.example.springsecurity.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemoryUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            log.info("------------------UserDetailsService");

            Member member= memberRepository.findById(username).orElseThrow(() ->
                    new UsernameNotFoundException("User Name Not found"));

            return  new MemberDTO(member.getUsername(),
                    member.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_USER")),
                    member.getNickName(),
                    member.getGender()
                    );
    }
}
