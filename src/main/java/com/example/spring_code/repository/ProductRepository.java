package com.example.spring_code.repository;

import com.example.spring_code.domian.Product;
import com.example.spring_code.repository.search.ProductSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {

    @EntityGraph(attributePaths = "imageList")
    @Query("SELECT p FROM Product p WHERE p.pno = :pno")
    Optional<Product> selectOne(@Param("pno") Long pno);

    @Modifying
    @Query("UPDATE Product p SET p.delFlag = :delFlag where p.pno = :pno")
    void updateToDelete(@Param("delFlag") boolean delFlag, @Param("pno") Long pno);

    @Query("SELECT p, pi FROM Product p LEFT JOIN p.imageList pi WHERE pi.ord = 0 AND p.delFlag = false ")
    Page<Object[]> selectList(Pageable pageable);


}
