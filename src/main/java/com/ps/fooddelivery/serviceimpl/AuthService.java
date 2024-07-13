package com.ps.fooddelivery.serviceimpl;

import com.ps.fooddelivery.dto.LoginRequest;
import com.ps.fooddelivery.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String AUTH_SERVICE_URL = "http://localhost:8081/auth";

    public AuthResponse login(LoginRequest loginRequest) {
        String loginUrl = AUTH_SERVICE_URL + "/login";
        ResponseEntity<AuthResponse> responseEntity = restTemplate.postForEntity(loginUrl, loginRequest, AuthResponse.class);
        return responseEntity.getBody();
    }
}
