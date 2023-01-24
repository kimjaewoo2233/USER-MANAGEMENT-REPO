package com.example.springsecurity.domain.repository;

import com.example.springsecurity.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String>,MemberFeatRepository {
}
