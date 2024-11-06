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


import java.util.List;
import java.util.Optional;
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

    @Override
    public ProductDTO get(Long pno) {
        Optional<Product> result = productRepository.findById(pno);
        Product product = result.orElseThrow();
        return Product.entityToDto(product);
    }

    @Override
    public void modify(ProductDTO productDTO) {
        // 조회
        Optional<Product> result = productRepository.findById(productDTO.getPno());
        // 변경 내용 반영
        Product product = result.orElseThrow();
        product.changeProductPrice(productDTO.getProductPrice());
        product.changeProductName(productDTO.getProductName());
        product.changeProductDesc(productDTO.getProductDescription());
        product.changeDelFlag(productDTO.isDelFlag());

        // 이미지 처리
        List<String> uploadeFileList = productDTO.getUploadedFileList();
        product.clearProductImage(); // 전부 삭제

        if(uploadeFileList != null && !uploadeFileList.isEmpty()){
            uploadeFileList.forEach(
                    uploadName -> product.addProductImageByString(uploadName)
            );
        }

        // 저장
        productRepository.save(product);
    }
}
