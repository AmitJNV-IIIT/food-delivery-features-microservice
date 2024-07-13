package com.ps.fooddelivery.modal;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "carts")
public class Cart {

    @Id
    private String id;
    private String userId;
    private List<CartItem> items;
    private String name;
    private String price;
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Document(collection = "cartItems")
    public static class CartItem {
        @Id
        private String foodId;
        private int quantity;

    }

}
