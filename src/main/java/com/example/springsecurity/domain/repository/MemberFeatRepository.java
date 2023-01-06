package com.example.springsecurity.domain.repository;

import com.example.springsecurity.domain.dto.MemberDTO;
import com.example.springsecurity.domain.entity.Member;

public interface MemberFeatRepository {

    public Member memberInfo(String username);
}
