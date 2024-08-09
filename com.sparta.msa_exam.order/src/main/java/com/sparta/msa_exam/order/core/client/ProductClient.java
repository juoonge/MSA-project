package com.sparta.msa_exam.order.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service",url = "${product-service.url}")
public interface ProductClient {
    @PostMapping("products/{id}/")
    ProductResponseDto getProduct(@PathVariable("id") Long id,
                                  @RequestHeader(value="X-USER-Id",required=true) String userId);
}
