package com.sparta.msa_exam.product.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto implements Serializable {
    private Long id;
    private String name;
    private Integer price;
}
