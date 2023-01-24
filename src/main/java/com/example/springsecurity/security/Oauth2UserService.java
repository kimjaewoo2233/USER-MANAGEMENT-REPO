package com.example.springsecurity.security;


import com.example.springsecurity.domain.Gender;
import com.example.springsecurity.domain.MemberRole;
import com.example.springsecurity.domain.dto.MemberDTO;
import com.example.springsecurity.domain.entity.Member;
import com.example.springsecurity.domain.entity.Role;
import com.example.springsecurity.domain.repository.MemberRepository;
import com.example.springsecurity.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class Oauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
            log.info("OAuth INFO ==== > {}",userRequest);

            ClientRegistration clientRegistration = userRequest.getClientRegistration();
            String clientName = clientRegistration.getClientName();

            log.info("OAuth NAME ==== > {}",clientName);    // 카카오로 들어왔으니 kakao가 출력된다.

            OAuth2User oAuth2User = super.loadUser(userRequest);
            Map<String, Object> paramMap = oAuth2User.getAttributes();  //선택항목들

            paramMap.forEach((k, v) -> {
                log.info("{} : {}",k,v);
            });

            String email = null;

            switch (clientName){
                case "kakao":
                    email = getKakaoEmail(paramMap);
                    break;
            }

            log.info("EMAIL INFO === > {}",email);
            return generateDTO(email,paramMap);
    }


    /**
     * @apiNote 만약 email을 통해 조회해서 없는 경우 만들고 있는 경우 있는 거 리턴해준다.
     *
     * */
    private MemberDTO generateDTO(String email, Map<String, Object> paramMap) {

        Optional<Member> result = memberRepository.socialMemberInfo(email); //이메일이 유저네임임

        //DB에 해당 이메일을 가진 사용자가 없는 경우에

        if(result.isEmpty()){
                //username으로 회원을 추가하고 비밀번호는 테스트용으로 1111을 지원하도록 한다.
                Member member = Member.builder()
                        .username(email)
                        .password(passwordEncoder.encode("1111"))
                        .social(true)   //소셜로 가입된 아이디이기 때문에 이 값을 true로 넣어줘야 한다.
                        .build();
               Member returnMember =  memberRepository.save(member);
               Role role = Role.builder().memberRole(MemberRole.ROLE_NORMAL).member(member).build();
                roleRepository.save(role);

                //새로 회원가입하는 회원이기 떄문에 굳이 join 쿼리로 사용자를 불러올 필요가 없다.
                MemberDTO dto = new MemberDTO(email,
                        "1111",
                        Arrays.asList(new SimpleGrantedAuthority("ROLE_NORMAL")),
                        Set.of(MemberRole.ROLE_NORMAL),
                        email,
                        Gender.DEFAULT,
                        true
                        );
                //소셜로그인으로 가입한 사람이기에 gender와 닉네임은 따로 지정할 수 있도록 해야한다.
                return dto;
        }else{
            Member member = result.get();
            MemberDTO dto = MemberDTO.toDTO(member);
            return dto;
        }
    }

    private String getKakaoEmail(Map<String, Object> paramMap) {
            log.info("KAKAO 서버로 접근");

            Object value = paramMap.get("kakao_account");

            LinkedHashMap linkedHashMap = (LinkedHashMap) value;
            String email = (String) linkedHashMap.get("email");

            log.info("Email 추출 : {}",email);
            return email;
    }
}
