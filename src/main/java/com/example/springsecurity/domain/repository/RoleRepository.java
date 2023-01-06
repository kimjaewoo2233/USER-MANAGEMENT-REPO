package com.example.springsecurity.domain.repository;

import com.example.springsecurity.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
