package com.example.spring_code.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long pno;

    private String productName;

    private int productPrice;

    private String productDescription;

    // 소프트 딜리트 방식 채용
    private boolean delFlag;

    // new ArrayList로 생성자를 밀리 만들어 놓아서 사전에 NullPointerException 방지
    // 업로드하는 파일 리스트
    @Builder.Default
    private List<MultipartFile> uploadFileList = new ArrayList<>();

    // 이미 업로드 된 파일들의 String List
    @Builder.Default
    private List<String> uploadedFileList = new ArrayList<>();



}
