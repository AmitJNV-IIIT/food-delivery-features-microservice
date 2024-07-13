package com.ps.fooddelivery.dto;

import lombok.Data;

@Data//getter,setter
public class AuthResponse {
    private String jwt;
    private String message;
    private boolean isValid;

}
