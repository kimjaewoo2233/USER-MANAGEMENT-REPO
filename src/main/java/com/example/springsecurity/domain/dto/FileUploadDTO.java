package com.example.springsecurity.domain.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class FileUploadDTO {

        private List<MultipartFile> files;
}
