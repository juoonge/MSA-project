package com.sparta.msa_exam.order.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service",url = "localhost:19091/")
public interface ProductClient {
    @GetMapping("products/{id}/")
    ProductResponseDto getProduct(@PathVariable("id") Long id,
                                  @RequestHeader(value="Authorization",required=true) String userId);
}
