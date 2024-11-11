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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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


    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')") // test
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

    @GetMapping("/{pno}")
    public ResponseEntity<?> get(@PathVariable(name = "pno") Long pno){
        return ResponseEntity.ok().body(productService.get(pno));
    }

    @PutMapping("/{pno}")
    public ResponseEntity<?> modify(@PathVariable(name = "pno") Long pno, ProductDTO productDTO){

        productDTO.setPno(pno);

        ProductDTO old = productService.get(pno);
        List<String> oldFileNames = old.getUploadedFileList();

        // 새로 업로드해야 하는 파일
        List<MultipartFile> newFiles = productDTO.getUploadFileList();
        List<String> newFileNames = customFileUtil.saveFile(newFiles);
        List<String> nonDeletedFileNames = productDTO.getUploadedFileList();

        if(newFileNames != null && newFileNames.size() > 0){
            nonDeletedFileNames.addAll(newFileNames);
        }

        // 수정
        productService.modify(productDTO);
        if(oldFileNames != null && oldFileNames.size() > 0){

            //지워야 하는 파일 목록 찾기
            //예전 파일들 중에서 지워져야 하는 파일이름들
            List<String> removeFiles = oldFileNames
                    .stream()
                    .filter(fileName -> nonDeletedFileNames.indexOf(fileName) == -1).collect(Collectors.toList());

            //실제 파일 삭제
            customFileUtil.deleteFiles(removeFiles);
        }
        return ResponseEntity.ok().body(Map.of("RESULT", "SUCCESS"));
    }

    @DeleteMapping("/{pno}")
    public ResponseEntity<?> remove(@PathVariable(name = "pno" )Long pno){
        List<String> uploadedFileList = productService.get(pno).getUploadedFileList();
        productService.remove(pno);
        customFileUtil.deleteFiles(uploadedFileList);
        return ResponseEntity.ok().body(Map.of("RESULT", "SUCCESS"));
    }


}
