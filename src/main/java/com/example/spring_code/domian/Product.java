package com.example.spring_code.domian;

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
    public void changeDesc(String productDescription){
        this.productDescription = productDescription;
    }
    public void changeName(String productName){
        this.productName = productName;
    }

    public void addProductImage(ProductImage productImage){
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




}
