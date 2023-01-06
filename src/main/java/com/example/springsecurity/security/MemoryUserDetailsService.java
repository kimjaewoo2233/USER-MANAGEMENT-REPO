

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
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemoryUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            log.info("------------------UserDetailsService");

        return MemberDTO.toDTO(memberRepository.findById(username)
                .orElseThrow(() ->
                new UsernameNotFoundException("User Name Not found")));
    }
}
