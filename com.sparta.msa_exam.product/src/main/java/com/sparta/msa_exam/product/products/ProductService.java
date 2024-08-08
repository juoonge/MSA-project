package com.sparta.msa_exam.product.products;

import com.sparta.msa_exam.product.core.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto,String userId) {
        Product product = Product.createProduct(requestDto);
        Product savedProduct = productRepository.save(product);
        return toResponseDto(savedProduct);
    }

    // 상품 목록 cache
    // 이 메서드의 결과는 캐싱이 가능하다
    // cacheNames: 이 메서드로 인해 만들어진 캐시를 지칭하는 이름
    @Cacheable(cacheNames="productAllCache", key="methodName")
    public List<ProductResponseDto> getAllProducts(String userId){
        return productRepository.findAll().stream()
                .map(Product::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found or has been deleted"));
        return toResponseDto(product);
    }

    private ProductResponseDto toResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }
}
