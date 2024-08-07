package com.sparta.msa_exam.order.orders;

import com.sparta.msa_exam.order.core.Order;
import com.sparta.msa_exam.order.core.client.ProductClient;
import com.sparta.msa_exam.order.core.client.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto, String userId) {

        Order order = Order.createOrder(requestDto.getOrderItemIds(), userId);
        Order savedOrder = orderRepository.save(order);
        return toResponseDto(savedOrder);
    }


    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been deleted"));
        return toResponseDto(order);
    }

    /**
     * `FeignClient`를 이용해서 주문 서비스에 상품 서비스 클라이언트 연결
     *  상품 목록 조회 API를 호출해서 파라미터로 받은 `product_id` 가 상품 목록에 존재하는지 검증
     *  존재할경우 주문에 상품을 추가하고, 존재하지 않는다면 주문에 저장하지 않음.
     */
    @Transactional
    public OrderResponseDto addProduct(Long orderId, OrderRequestDto requestDto,String UserId) {


        // 1. 주문 존재 여부 확인
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        // 2. 상품 존재 여부 확인
        for(Long productId:requestDto.getOrderItemIds()){
            ProductResponseDto product=productClient.getProduct(productId);
            if(product==null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with ID " + productId + " does not exist or is out of stock.");
            }
        }

        // 3. 주문에 상품 추가
        order.addProduct(requestDto.getOrderItemIds());
        Order addOrder=orderRepository.save(order);

        return toResponseDto(addOrder);
    }


    private OrderResponseDto toResponseDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getCreatedBy(),
                order.getOrderItemIds()
        );
    }
}