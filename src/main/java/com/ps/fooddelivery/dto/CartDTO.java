package com.ps.fooddelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private String userId;
    private List<CartItemDTO> items;
    private String name;
    private String price;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartItemDTO {
        // Getters and Setters
        private String foodId;
        private int quantity;

    }


}
