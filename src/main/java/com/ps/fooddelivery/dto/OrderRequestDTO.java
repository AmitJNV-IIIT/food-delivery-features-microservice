package com.ps.fooddelivery.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
    private String userId;
    private List<String> items;
}