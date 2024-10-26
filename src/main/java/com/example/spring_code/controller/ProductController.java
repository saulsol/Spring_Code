package com.example.spring_code.controller;

import com.example.spring_code.dto.ProductDTO;
import com.example.spring_code.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil customFileUtil;

    @PostMapping("/saveFile")
    public ResponseEntity<?> register(@ModelAttribute ProductDTO productDTO){
        //MultipartFile 사용으로 인한 FormData로 받아야 하기 때문에 @ModelAttribute 사용
        log.info("register: " + productDTO);

        List<String> uploadFileNames = customFileUtil.saveFile(productDTO.getUploadFileList());
        productDTO.setUploadedFileList(uploadFileNames);

        log.info(uploadFileNames);
        return ResponseEntity.ok().body(Map.of("SUCCESS", "RESULT"));
    }


}
