package com.example.springsecurity.domain.repository;

import com.example.springsecurity.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
