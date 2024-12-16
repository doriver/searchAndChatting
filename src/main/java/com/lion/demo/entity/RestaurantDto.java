package com.lion.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RestaurantDto {
    private Restaurant restaurant;
    private int infoCount;
    private int reviewCount;
    private double reviewScore;
    private float matchScore;

}
