package com.example.springsecurity.service;


import com.example.springsecurity.domain.MemberRole;
import com.example.springsecurity.domain.data.MemberRegisterData;
import com.example.springsecurity.domain.entity.Member;
import com.example.springsecurity.domain.entity.Role;
import com.example.springsecurity.domain.repository.MemberRepository;
import com.example.springsecurity.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class MemberService {
        private final MemberRepository memberRepository;

        private final PasswordEncoder passwordEncoder;
        private final RoleRepository roleRepository;

        /*
        * 기본 회원가입할때 자동으로 NORMAL 등급으로 지정된다.
        * */
        public boolean register(MemberRegisterData memberRegisterData){
                Member member = Member.builder()
                        .username(memberRegisterData.getUsername())
                        .password(passwordEncoder.encode(memberRegisterData.getPassword()))
                        .nickName(memberRegisterData.getNickName())
                        .gender(memberRegisterData.getGender())
                        .build();

                boolean resultMember = ObjectUtils
                        .isEmpty(memberRepository.save(member));

                boolean resultRole = ObjectUtils
                        .isEmpty(roleRepository
                        .save(Role.builder()
                                .member_username(memberRegisterData.getUsername())
                                .memberRole(MemberRole.ROLE_NORMAL)
                                .build()));

                if (resultMember || resultRole){
                        return false;
                }
                return true;

        }

}
