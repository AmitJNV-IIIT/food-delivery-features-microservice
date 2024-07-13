package com.ps.fooddelivery.service;

import com.ps.fooddelivery.exception.custom.ResourceNotFoundException;
import com.ps.fooddelivery.modal.Order;

public interface OrderService {
    Order createOrder(Order order);
    Order trackOrder(String id) throws ResourceNotFoundException;
    Order statusOrder(Order order) throws ResourceNotFoundException;
}
