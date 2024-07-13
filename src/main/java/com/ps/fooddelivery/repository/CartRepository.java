package com.ps.fooddelivery.repository;

import com.ps.fooddelivery.modal.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
//    @Query("Select * from carts c where c.userId == :userId")
    List<Cart> findByUserId(String userId);
}