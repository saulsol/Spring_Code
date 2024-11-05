package com.example.spring_code.service.productServiceImpl;


import com.example.spring_code.domian.Product;
import com.example.spring_code.domian.ProductImage;
import com.example.spring_code.dto.PageRequestDTO;
import com.example.spring_code.dto.PageResponseDTO;
import com.example.spring_code.dto.ProductDTO;
import com.example.spring_code.repository.ProductRepository;
import com.example.spring_code.service.ProductServiceInterface.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                    pageRequestDTO.getPage() -1,
                    pageRequestDTO.getSize(),
                    Sort.by("pno").descending()
                );

        Page<Object[]> result = productRepository.selectList(pageable);

        List<ProductDTO> dtoList = result.get().map(
                arr -> {
                    Product product = (Product) arr[0];
                    ProductImage productImage = (ProductImage) arr[1];

                    ProductDTO productDTO = ProductDTO.builder()
                            .pno(product.getPno())
                            .productName(product.getProductName())
                            .productDescription(product.getProductDescription())
                            .productPrice(product.getProductPrice())
                            .build();

                    String imageStr = productImage.getFileName();
                    productDTO.setUploadedFileList(List.of(imageStr));
                    return productDTO;
                }
        ).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResponseDTO.<ProductDTO>withAll()
                .dtoList(dtoList)
                .total(totalCount)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    @Override
    public Long register(ProductDTO productDTO) {
        Product product = Product.dtoToEntity(productDTO);
        return productRepository.save(product).getPno();
    }
}
