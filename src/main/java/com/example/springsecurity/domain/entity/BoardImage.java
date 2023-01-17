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
@Table(name = "board_image")
public class BoardImage implements Comparable<BoardImage> {

    @Id
    private String uuid;


    private String fileName;

    private int ord;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    @Override   //OneToMany순서에 맞게 정렬함
    public int compareTo(BoardImage boardImage){
            return  this.ord - boardImage.ord;
    }
    public void changeProduct(Product product){
        this.product = product;
    }
}
