package com.ps.fooddelivery.dto;

import com.ps.fooddelivery.modal.OrderStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO {
    private String userId;
    private List<String> items;
    private OrderStatus status;
}

