package com.example.springsecurity.domain.repository;

import com.example.springsecurity.domain.dto.MemberDTO;
import com.example.springsecurity.domain.entity.Member;

import java.util.Optional;

public interface MemberFeatRepository {

    public Optional<MemberDTO> memberInfo(String username);
}
