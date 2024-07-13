package com.ps.fooddelivery.service;

import com.ps.fooddelivery.dto.LoginRequest;
import com.ps.fooddelivery.dto.AuthResponse;

public interface AuthService {
    public AuthResponse login(LoginRequest loginRequest);
}
