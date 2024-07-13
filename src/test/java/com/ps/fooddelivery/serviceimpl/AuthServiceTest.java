package com.ps.fooddelivery.serviceimpl;

import com.ps.fooddelivery.dto.LoginRequest;
import com.ps.fooddelivery.dto.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AuthService authService;

    private LoginRequest loginRequest;
    private AuthResponse authResponse;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);

        loginRequest = new LoginRequest();
        loginRequest.setEmail("testuser@gmail.com");
        loginRequest.setPassword("password");

        authResponse = new AuthResponse();
        authResponse.setJwt("dummy-token");
    }

    @Test
     void testLogin() {
        String loginUrl = "http://localhost:8081/auth/login";
        ResponseEntity<AuthResponse> responseEntity = new ResponseEntity<>(authResponse, HttpStatus.OK);

        when(restTemplate.postForEntity(any(String.class), any(LoginRequest.class), any(Class.class))).thenReturn(responseEntity);

        AuthResponse result = authService.login(loginRequest);
        assertEquals(authResponse.getJwt(), result.getJwt());
        verify(restTemplate, times(1)).postForEntity(loginUrl, loginRequest, AuthResponse.class);
    }
}