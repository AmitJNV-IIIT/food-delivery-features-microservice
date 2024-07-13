package com.ps.fooddelivery.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FoodDTO {
    private String name;
    private String description;
    private double price;
}
