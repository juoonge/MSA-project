package com.sparta.msa_exam.order.orders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/{orderId}")
    public OrderResponseDto addProduct(@PathVariable Long orderId,
                                       @RequestBody ProductAddDto productId,
                                        @RequestHeader(value = "X-User-Id", required = true) String userId) {
        return orderService.addProduct(orderId,productId,userId);
    }

}