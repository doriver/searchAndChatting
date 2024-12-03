package com.lion.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cid;
    
    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
    // 여기에따만 하는듯? User, Book 은 안건드림
    @ManyToOne
    @JoinColumn(name = "bid")
    private Book book;
    
    private int quantity;
    
}
