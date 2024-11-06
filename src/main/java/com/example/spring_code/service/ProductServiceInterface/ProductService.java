package com.example.spring_code.service.ProductServiceInterface;

import com.example.spring_code.dto.PageRequestDTO;
import com.example.spring_code.dto.PageResponseDTO;
import com.example.spring_code.dto.ProductDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductService {
    PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO);

    Long register(ProductDTO productDTO);

    ProductDTO get(Long pno);

    void modify(ProductDTO productDTO);

    void remove(Long pno);

}
