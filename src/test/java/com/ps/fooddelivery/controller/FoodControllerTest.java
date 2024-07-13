package com.ps.fooddelivery.controller;

import com.ps.fooddelivery.dto.AuthResponse;
import com.ps.fooddelivery.dto.FoodDTO;
import com.ps.fooddelivery.dto.LoginRequest;
import com.ps.fooddelivery.modal.Food;
import com.ps.fooddelivery.service.FoodService;
import com.ps.fooddelivery.serviceimpl.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@CrossOrigin(origins = "http://localhost:3000")
 class FoodControllerTest {

    @InjectMocks
    private FoodController foodController;

    @Mock
    private FoodService foodService;

    @Mock
    private AuthService authService;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        LoginRequest loginRequest = new LoginRequest();
        AuthResponse authResponse = new AuthResponse();
        when(authService.login(loginRequest)).thenReturn(authResponse);

        ResponseEntity<AuthResponse> response = foodController.login(loginRequest);
        assertEquals(authResponse, response.getBody());
        verify(authService, times(1)).login(loginRequest);
    }

    @Test
     void testGetAllFoodItems() {
        List<Food> foodItems = Arrays.asList(new Food(), new Food());
        when(foodService.getAllFoodItems()).thenReturn(foodItems);

        ResponseEntity<List<Food>> response = foodController.getAllFoodItems();
        assertEquals(foodItems, response.getBody());
        verify(foodService, times(1)).getAllFoodItems();
    }

    @Test
     void testCreateFoodItemSuccess() {
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setName("Pizza");
        foodDTO.setDescription("Cheese Pizza");
        foodDTO.setPrice(10.99);

        Food food = new Food();
        food.setId("food123");
        food.setName(foodDTO.getName());
        food.setDescription(foodDTO.getDescription());
        food.setPrice(foodDTO.getPrice());

        when(foodService.createFoodItem(any(Food.class))).thenReturn(food);

        ResponseEntity<Food> response = foodController.createFoodItem(foodDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(food, response.getBody());

        verify(foodService, times(1)).createFoodItem(any(Food.class));
    }

    @Test
     void testSearchFoodItems() {
        String search = "pizza";
        List<Food> searchResults = Arrays.asList(new Food(), new Food());
        when(foodService.searchFoodItems(search)).thenReturn(searchResults);

        ResponseEntity<List<Food>> response = foodController.searchFoodItems(search);
        assertEquals(searchResults, response.getBody());
        verify(foodService, times(1)).searchFoodItems(search);
    }
}
