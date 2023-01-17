package com.example.springsecurity.controller;

import com.example.springsecurity.domain.dto.ProductDTO;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class HomeController {

        @GetMapping("/index")
        public String get(){

            return "index";
        }

        @PostMapping("/save")
        public String post(ProductDTO dto){
            dto.getFileNames().forEach(System.out::println);
            return "redirect:/index";
        }
}
