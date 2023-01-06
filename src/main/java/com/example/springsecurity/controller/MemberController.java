package com.example.springsecurity.controller;


import com.example.springsecurity.domain.data.MemberRegisterData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

        @PostMapping("/register")
        public ResponseEntity<Boolean> register(
                @RequestBody MemberRegisterData memberRegisterData){


            return ResponseEntity.ok(false);
        }
}
