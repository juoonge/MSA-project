package com.sparta.msa_exam.product.products;

import com.sparta.msa_exam.product.core.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto, String userId) {
        Product product = Product.createProduct(requestDto, userId);
        Product savedProduct = productRepository.save(product);
        return toResponseDto(savedProduct);
    }

    public List<ProductResponseDto> getAllProducts(){
        return productRepository.findAll().stream()
                .map(Product::toResponseDto)
                .collect(Collectors.toList());
    }

    private ProductResponseDto toResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }
}
