package com.example.spring_code.repository;

import com.example.spring_code.domian.Product;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert(){
        Product product = Product.builder()
                .productName("testProduct")
                .productDescription("ex desc")
                .productPrice(1000)
                .build();

        product.addProductImageByString(UUID.randomUUID() + "IMAGE1.jpg");
        product.addProductImageByString(UUID.randomUUID() + "IMAGE2.jpg");

        productRepository.save(product);
    }

}