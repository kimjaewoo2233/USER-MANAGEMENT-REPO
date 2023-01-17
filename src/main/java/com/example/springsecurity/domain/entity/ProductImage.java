package com.example.springsecurity.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product_image")
public class ProductImage implements Comparable<ProductImage> {

    @Id
    private String uuid;


    private String fileName;

    private int ord;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @Override   //OneToMany순서에 맞게 정렬함
    public int compareTo(ProductImage productImage){
            return  this.ord - productImage.ord;
    }
    public void changeProduct(Product product){
        this.product = product;
    }
}
