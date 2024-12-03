package com.lion.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long oid;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // mappedBy 는 폴인키를 가르킴
    private List<OrderItem> orderItems = new ArrayList<>(); // 빈 리스트로 초기화
    // 데이터 가져올떄만 쓰이는건가

    private LocalDateTime orderDateTime;
    private int totalAmount;

    // 연관관계 메소드 추가
    public void addOrderItem(OrderItem orderItem) {
        if (this.orderItems == null) {
            this.orderItems = new ArrayList<>();
        }
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
