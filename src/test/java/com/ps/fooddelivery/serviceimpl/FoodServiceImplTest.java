package com.ps.fooddelivery.serviceimpl;

import com.ps.fooddelivery.exception.custom.ResourceNotFoundException;
import com.ps.fooddelivery.modal.Food;
import com.ps.fooddelivery.repository.FoodRepository;
import com.ps.fooddelivery.service.FoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FoodServiceImplTest {

   @Mock
   private FoodRepository foodRepository;

   @InjectMocks
   private FoodServiceImpl foodService;

   @BeforeEach
   public void setUp() {
      MockitoAnnotations.openMocks(this);
   }

   @Test
   public void testGetAllFoodItems() {
      // Arrange
      Food food1 = new Food("1", "Pizza", "Cheese Pizza", 9.99);
      Food food2 = new Food("2", "Burger", "Beef Burger", 5.99);
      List<Food> foodList = Arrays.asList(food1, food2);
      when(foodRepository.findAll()).thenReturn(foodList);

      // Act
      List<Food> result = foodService.getAllFoodItems();

      // Assert
      assertEquals(2, result.size());
      assertTrue(result.contains(food1));
      assertTrue(result.contains(food2));
      verify(foodRepository, times(1)).findAll();
   }

   @Test
   public void testSearchFoodItemsSuccess() {
      // Arrange
      Food food1 = new Food("1", "Pizza", "Cheese Pizza", 9.99);
      Food food2 = new Food("2", "Pizza", "Pepperoni Pizza", 10.99);
      List<Food> foodList = Arrays.asList(food1, food2);
      when(foodRepository.findByNameContainingIgnoreCase("Pizza")).thenReturn(foodList);

      // Act
      List<Food> result = foodService.searchFoodItems("Pizza");

      // Assert
      assertEquals(2, result.size());
      assertTrue(result.contains(food1));
      assertTrue(result.contains(food2));
      verify(foodRepository, times(1)).findByNameContainingIgnoreCase("Pizza");
   }

   @Test
   public void testSearchFoodItemsNotFound() {
      // Arrange
      when(foodRepository.findByNameContainingIgnoreCase("NonExistingFood")).thenReturn(Arrays.asList());

      // Act & Assert
      ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
         foodService.searchFoodItems("NonExistingFood");
      });
      assertEquals("No food items found for search: NonExistingFood", thrown.getMessage());
      verify(foodRepository, times(1)).findByNameContainingIgnoreCase("NonExistingFood");
   }

   @Test
   public void testCreateFoodItem() {
      // Arrange
      Food food = new Food("1", "Pizza", "Cheese Pizza", 9.99);
      when(foodRepository.save(food)).thenReturn(food);

      // Act
      Food result = foodService.createFoodItem(food);

      // Assert
      assertNotNull(result);
      assertEquals("1", result.getId());
      assertEquals("Pizza", result.getName());
      verify(foodRepository, times(1)).save(food);
   }
}
