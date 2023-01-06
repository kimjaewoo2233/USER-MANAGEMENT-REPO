package com.example.springsecurity.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

        @GetMapping("/hello")
        @PreAuthorize("hasRole('ROLE_NORMAL')")
        public String hello(Authentication authentication){
                System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
                System.out.println(authentication.getName());
                return "\nHello World"+authentication.getName();
        }

        @GetMapping("/hello2")
        @PreAuthorize("hasRole('ROLE_GOLD')")
        public String hello2(){
                return "\nHello World";
        }
}

