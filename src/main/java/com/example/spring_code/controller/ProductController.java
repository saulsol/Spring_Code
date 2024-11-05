package com.example.spring_code.controller;

import com.example.spring_code.dto.PageRequestDTO;
import com.example.spring_code.dto.PageResponseDTO;
import com.example.spring_code.dto.ProductDTO;
import com.example.spring_code.service.ProductServiceInterface.ProductService;
import com.example.spring_code.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil customFileUtil;
    private final ProductService productService;

//    @PostMapping("/saveFile")
//    public ResponseEntity<?> register(@ModelAttribute ProductDTO productDTO){
//        //MultipartFile 사용으로 인한 FormData로 받아야 하기 때문에 @ModelAttribute 사용
//        log.info("register: " + productDTO);
//
//        List<String> uploadFileNames = customFileUtil.saveFile(productDTO.getUploadFileList());
//        productDTO.setUploadedFileList(uploadFileNames);
//
//        log.info(uploadFileNames);
//        return ResponseEntity.ok().body(Map.of("SUCCESS", "RESULT"));
//    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable("fileName") String fileName) {
        return customFileUtil.getFile(fileName);
    }


    @GetMapping("/list")
    public ResponseEntity<?> list(PageRequestDTO pageRequestDTO){
        return ResponseEntity.ok(productService.getList(pageRequestDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(ProductDTO productDTO){
        List<MultipartFile> uploadFileList = productDTO.getUploadFileList();
        List<String> uploadedFileNames = customFileUtil.saveFile(uploadFileList);
        productDTO.setUploadedFileList(uploadedFileNames);
        return ResponseEntity.ok(productService.register(productDTO));
    }



}
