package com.sparta.msa_exam.order.orders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto orderRequestDto,
                                        @RequestHeader(value = "X-User-Id", required = true) String userId) {

        return orderService.createOrder(orderRequestDto, userId);
    }


    @GetMapping("/{orderId}")
    public OrderResponseDto getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }


    @PutMapping("/{orderId}")
    public OrderResponseDto addProduct(@PathVariable Long orderId,
                                        @RequestBody OrderAddDto orderAddDto,
                                        @RequestHeader(value = "X-User-Id", required = true) String userId) {
        return orderService.addProduct(orderId,orderAddDto,userId);
    }

}