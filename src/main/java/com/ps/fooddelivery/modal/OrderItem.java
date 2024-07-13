package com.ps.fooddelivery.modal;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "order_items")
public class OrderItem {
    @Id
    private String foodId;
    private int quantity;

}
