package com.example.springsecurity.domain.entity;


import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //이미지 먼저하고 다음에 그거에 따른 내용을 작성할 것임 - 아직 뭐들어갈지 몰라서 그냥 놔둠

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @Builder.Default
    private Set<BoardImage> boardImageSet = new HashSet<>();



    public void addImage(String uuid,String fileName){

            BoardImage boardImage = BoardImage.builder()
                    .uuid(uuid)
                    .fileName(fileName)
                    .product(this)
                    .ord(boardImageSet.size())
                    .build();

            boardImageSet.add(boardImage);
    }

    public void clearImages(){
            boardImageSet.forEach(boardImage -> {
                boardImage.changeProduct(null);
            });
    }
}
