package com.ps.fooddelivery.repository;

import com.ps.fooddelivery.modal.Food;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends MongoRepository<Food, String> {
    List<Food> findByNameContainingIgnoreCase(String name);
}