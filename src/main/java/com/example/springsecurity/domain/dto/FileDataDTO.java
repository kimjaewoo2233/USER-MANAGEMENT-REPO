package com.example.springsecurity.domain.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FileDataDTO {

        private String uuid;
        private String fileName;
        private boolean thumbnailCheck;

        public String getLink(){
            if(thumbnailCheck){
                return "s_"+uuid+"_"+fileName;
            }else{
                return uuid+"_"+fileName;
            }
        }
    }
