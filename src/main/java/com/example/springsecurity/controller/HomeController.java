package com.example.springsecurity.controller;

import com.example.springsecurity.domain.dto.ProductDTO;
import com.example.springsecurity.service.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

        private final ProductService productService;
        @GetMapping("/index")
        public String get(){
            return "index";
        }

        @PostMapping("/save")
        public String post(ProductDTO dto){
            productService.saveProduct(dto);
            return "redirect:/index";
        }
}
