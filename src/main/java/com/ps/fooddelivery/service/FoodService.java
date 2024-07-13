package com.ps.fooddelivery.service;

import com.ps.fooddelivery.exception.custom.ResourceNotFoundException;
import com.ps.fooddelivery.modal.Food;
import java.util.List;

public interface FoodService {
    List<Food> getAllFoodItems() throws ResourceNotFoundException;
    Food createFoodItem(Food foodItem) throws ResourceNotFoundException;
    List<Food> searchFoodItems(String search) throws ResourceNotFoundException;
}
