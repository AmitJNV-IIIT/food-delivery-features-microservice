package com.ps.fooddelivery.serviceimpl;

import com.ps.fooddelivery.exception.custom.ResourceNotFoundException;
import com.ps.fooddelivery.modal.Food;
import com.ps.fooddelivery.repository.FoodRepository;
import com.ps.fooddelivery.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    private static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class);

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public List<Food> getAllFoodItems() {
        logger.info("Entering getAllFoodItems");
        List<Food> foodItems = foodRepository.findAll();
        logger.info("All food items retrieved successfully");
        return foodItems;
    }

    @Override
    public List<Food> searchFoodItems(String search) {
        logger.info("Entering searchFoodItems with search: {}", search);
        List<Food> searchResults = foodRepository.findByNameContainingIgnoreCase(search);
        if (searchResults.isEmpty()) {
            logger.error("No food items found for search: {}", search);
            throw new ResourceNotFoundException("No food items found for search: " + search);
        }
        logger.info("Search results retrieved successfully for search: {}", search);
        return searchResults;
    }

    @Override
    public Food createFoodItem(Food foodItem) {
        logger.info("Entering createFoodItem with foodItem: {}", foodItem);
       var createdFoodItem = foodRepository.save(foodItem);
        logger.info("Food item created successfully with id: {}", createdFoodItem.getId());
        return createdFoodItem;
    }
}
