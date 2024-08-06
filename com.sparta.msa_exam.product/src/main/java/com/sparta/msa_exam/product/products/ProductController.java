package com.sparta.msa_exam.product.products;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    // 상품 추가
    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto,
                                            @RequestHeader(value="X-USER-Id",required=true) String userId){
        return productService.createProduct(productRequestDto,userId);
    }

    // 상품 목록 조회
    @GetMapping
    public List<ProductResponseDto> getAllProducts(){
        return productService.getAllProducts();
    }
}
