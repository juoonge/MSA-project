package com.sparta.msa_exam.order.core;

import com.sparta.msa_exam.order.orders.OrderResponseDto;
import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "order_item_id")
    private List<Long> orderItemIds;


    public static Order createOrder(List<Long> orderItemIds, String createdBy) {
        return Order.builder()
                .orderItemIds(orderItemIds)
                .build();
    }

    public void addProduct(Long productId) {
        this.orderItemIds.add(productId);
    }

    // DTO로 변환하는 메서드
    public OrderResponseDto toResponseDto() {
        return new OrderResponseDto(
                this.id,
                this.name,
                this.orderItemIds
        );
    }
}