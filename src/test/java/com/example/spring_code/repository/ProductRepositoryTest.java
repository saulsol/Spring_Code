package com.example.spring_code.repository;

import com.example.spring_code.domian.Product;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
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

    @Test
    public void testRead(){
        Long pno = 1L;
        Optional<Product> result = productRepository.findById(pno);

        Product product = result.orElseThrow();
        log.info(product);
        log.info(product.getImageList());
        // 쿼리문이 2번 발생 => @Transactional 없으면 커넥션 객체를 2번 생성해야 하기 때문에 에러
    }

    @Test
    public void testRead2(){
        Long pno = 1L;
        Optional<Product> result = productRepository.selectOne(pno);

        Product product = result.orElseThrow();
        log.info(product);
        log.info(product.getImageList());
    }

    @Test
    @Commit
    @Transactional
    public void testDelete(){
        Long pno = 1L;
        productRepository.updateToDelete(false, pno);
    }
    @Test
    public void testUpdate(){
        Product product = productRepository.selectOne(1L).get();

        product.changeProductPrice(5000);

        product.clearProductImage();

        product.addProductImageByString(UUID.randomUUID() + "IMAGE1.jpg");
        product.addProductImageByString(UUID.randomUUID() + "IMAGE2.jpg");

        productRepository.save(product);

    }

}