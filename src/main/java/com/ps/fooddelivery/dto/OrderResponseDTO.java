package com.ps.fooddelivery.dto;

import com.ps.fooddelivery.modal.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private String id;
    private String userId;
    private List<String> items;
    private OrderStatus status;

}
