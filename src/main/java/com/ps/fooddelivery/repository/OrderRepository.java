package com.ps.fooddelivery.repository;

import com.ps.fooddelivery.modal.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {
    Optional<Order> findById(String id);
}
