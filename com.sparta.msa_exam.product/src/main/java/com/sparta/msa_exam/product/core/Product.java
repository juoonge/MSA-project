package com.sparta.msa_exam.product.core;

import com.sparta.msa_exam.product.products.ProductRequestDto;
import com.sparta.msa_exam.product.products.ProductResponseDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access= AccessLevel.PROTECTED)
@Builder(access=AccessLevel.PRIVATE)
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer price;
    private String createdBy;

    public static Product createProduct(ProductRequestDto requestDto,String userId){
        return Product.builder()
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .createdBy(userId)
                .build();
    }

    public ProductResponseDto toResponseDto(){
        return new ProductResponseDto(
                this.id,
                this.name,
                this.price
        );
    }
}
