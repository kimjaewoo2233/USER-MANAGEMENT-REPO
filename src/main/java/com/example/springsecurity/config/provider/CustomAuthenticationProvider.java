package com.example.springsecurity.config.provider;

import com.example.springsecurity.security.MemoryUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider {

    private final MemoryUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(

    );

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        System.out.println(userDetails);
        if(userDetails.getPassword().equals(userDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(username,password, userDetails.getAuthorities());
        }else{
            throw new AuthenticationCredentialsNotFoundException("Error!");
        }
    }

    public boolean supports(Class<?> authentication) {
        return true;
    }
}
