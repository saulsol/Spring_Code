package com.example.spring_code.repository;

import com.example.spring_code.domian.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
