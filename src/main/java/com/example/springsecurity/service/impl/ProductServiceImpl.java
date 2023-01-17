package com.example.springsecurity.service.impl;

import com.example.springsecurity.domain.dto.ProductDTO;
import com.example.springsecurity.domain.entity.Product;
import com.example.springsecurity.domain.repository.ProductRepository;
import com.example.springsecurity.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

        private final ProductRepository productRepository;

        @Override
        @Transactional
        public boolean saveProduct(ProductDTO dto) {
            Product entity = Product.builder()
                    .productName(dto.getProductName())
                    .build();

            if(!dto.getFileNames().isEmpty()){
                dto.getFileNames().forEach(fileName -> {
                    String[] arr = fileName.split("_");
                    entity.addImage(arr[0],arr[1]);
                });
            }

            productRepository.save(entity);

            return true;
        }

    }

