package com.example.spring_code.repository.search;

import com.example.spring_code.dto.PageRequestDTO;
import com.example.spring_code.dto.PageResponseDTO;
import com.example.spring_code.dto.ProductDTO;

public interface ProductSearch {
    PageResponseDTO<ProductDTO> searchList(PageRequestDTO pageRequestDTO);
}
