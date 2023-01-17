package com.example.springsecurity.controller;


import com.example.springsecurity.domain.dto.FileDataDTO;
import com.example.springsecurity.domain.dto.FileUploadDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
public class FileController {

        @Value("${org.upload.path}")
        private String path;

        /**
         * @param dto 필드 fileList name을 맞춰서 이미지를 보내주면 받을 수 있음
         * @apiNote uuid_파일명 으로 받고 이미지만 받을 수 있도록 만듬
         * */
        @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<List<FileDataDTO>> upload(FileUploadDTO dto){
            //log.info("{}",dto.getFiles().get(0).getOriginalFilename());
            String rtr;
            if(dto.getFiles() != null){

                final List<FileDataDTO> list = new ArrayList<>();

                dto.getFiles().forEach(multipartFile -> {
                    log.info("{}",multipartFile.getOriginalFilename());
                    String uuid = UUID.randomUUID().toString();

                    Path savePath = Paths.get(path,uuid+"_"+multipartFile.getOriginalFilename());
                   // File file = new File("C:\\Hallym\\img1.png"); file객체로도 가능함

                    boolean thumb = false;


                    try{
                        multipartFile.transferTo(savePath);
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    list.add(FileDataDTO.builder()
                                    .uuid(uuid)
                                    .fileName(multipartFile.getOriginalFilename())
                                    .thumbnailCheck(false)
                            .build());
                });
                return ResponseEntity.ok(list);
            }
            return null;
        }
        /**
         * @param fileName 을 받아서 해당 파일을 찾는다. 파일이 없으면 FileNotFoundException이 일어남
         * @apiNote 해당 확장자는 데이터를 뿌릴때 어짜피 같이 뿌릴 거기에 따로 예외처리는 하지 않음
         *  TODO: .jpg 뺴야함
         * */
        @GetMapping("/view")
        public ResponseEntity<Resource> viewFile(@RequestParam String fileName){
            Resource resource =
                    new FileSystemResource(path+File.separator+fileName);

            String resourceName = resource.getFilename();
            HttpHeaders headers = new HttpHeaders();
            try{
                headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
            } catch (IOException e) {
                return ResponseEntity.internalServerError().build();
            }
            return ResponseEntity.ok().headers(headers).body(resource);
        }


}
