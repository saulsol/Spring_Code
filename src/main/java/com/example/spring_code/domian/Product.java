package com.example.spring_code.domian;

import com.example.spring_code.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_product")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageList")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String productName;

    private int productPrice;

    private String productDescription;

    private boolean delFlag;

    @ElementCollection
    @Builder.Default
    private List<ProductImage> imageList = new ArrayList<>();


    public void changeProductPrice(int productPrice){
        this.productPrice = productPrice;
    }
    public void changeProductDesc(String productDescription){
        this.productDescription = productDescription;
    }
    public void changeProductName(String productName){
        this.productName = productName;
    }
    public void changeDelFlag(boolean delFlag){
        this.delFlag = delFlag;
    }

    private void addProductImage(ProductImage productImage){
        productImage.setOrd(imageList.size());
        imageList.add(productImage);
    }

    public void addProductImageByString(String fileName){
        ProductImage productImage = ProductImage.builder()
                .fileName(fileName)
                .build();
        addProductImage(productImage);
    }

    public void clearProductImage(){
        this.imageList.clear();
    }

    public static Product dtoToEntity(ProductDTO productDTO){

        Product newProduct = Product.builder()
                .pno(productDTO.getPno())
                .productName(productDTO.getProductName())
                .productDescription(productDTO.getProductDescription())
                .productPrice(productDTO.getProductPrice())
                .build();

        List<String> uploadFileNames = productDTO.getUploadedFileList(); // 이미 업로드가 끝난 파일 리스트

        if(uploadFileNames == null || uploadFileNames.size() == 0) {
            return newProduct;
        }

        uploadFileNames.forEach(
                fileName -> {
                    newProduct.addProductImageByString(fileName);
                }
        );


        return newProduct;
    }

    public static ProductDTO entityToDto(Product product){
        ProductDTO responseDto = ProductDTO.builder()
                .pno(product.getPno())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .productPrice(product.getProductPrice())
                .build();

        List<ProductImage> productImageList = product.getImageList();

        if(productImageList == null || productImageList.size() == 0){
            return responseDto;
        }

        List<String> productFileNameList = productImageList.stream().map(
                productImage -> productImage.getFileName()
        ).toList();

        responseDto.setUploadedFileList(productFileNameList);
        return responseDto;
    }





}
