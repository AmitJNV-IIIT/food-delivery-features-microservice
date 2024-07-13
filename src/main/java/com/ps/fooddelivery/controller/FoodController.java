package com.ps.fooddelivery.controller;

import com.ps.fooddelivery.dto.AuthResponse;
import com.ps.fooddelivery.dto.FoodDTO;
import com.ps.fooddelivery.modal.Food;
import com.ps.fooddelivery.service.FoodService;
import com.ps.fooddelivery.dto.LoginRequest;
import com.ps.fooddelivery.serviceimpl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@CrossOrigin(origins = "http://localhost:3000")
public class FoodController {

    private static final Logger logger = LoggerFactory.getLogger(FoodController.class);

    @Autowired
    private FoodService foodService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Entering login with loginRequest: {}", loginRequest);
        var authResponse = authService.login(loginRequest);
        logger.info("User logged in successfully");
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/catalog")
    public ResponseEntity<List<Food>> getAllFoodItems() {
        logger.info("Entering getAllFoodItems");
        List<Food> foodItems = foodService.getAllFoodItems();
        logger.info("All food items retrieved successfully");
        return ResponseEntity.ok(foodItems);
    }

    @PostMapping
    public ResponseEntity<Food> createFoodItem(@RequestBody FoodDTO foodDTO) {
        logger.info("Entering createFoodItem with foodDTO: {}",foodDTO);
        var foodItem = new Food();
        foodItem.setName(foodDTO.getName());
        foodItem.setDescription(foodDTO.getDescription());
        foodItem.setPrice(foodDTO.getPrice());

        var createdFoodItem = foodService.createFoodItem(foodItem);
        logger.info("Food item created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFoodItem);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFoodItems(@RequestParam("search") String search) {
        logger.info("Entering searchFoodItems with search: {}", search);
        List<Food> searchResults = foodService.searchFoodItems(search);
        logger.info("Search results retrieved successfully for search: {}", search);
        return ResponseEntity.ok(searchResults);
    }
}
