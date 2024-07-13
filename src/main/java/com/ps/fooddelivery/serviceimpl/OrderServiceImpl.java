package com.ps.fooddelivery.serviceimpl;

import com.ps.fooddelivery.exception.custom.ResourceNotFoundException;
import com.ps.fooddelivery.modal.Order;
import com.ps.fooddelivery.modal.OrderStatus;
import com.ps.fooddelivery.repository.OrderRepository;
import com.ps.fooddelivery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        // Set initial status
        order.setStatus(OrderStatus.PLACED);
        return orderRepository.save(order);
    }

    @Override
    public Order trackOrder(String id) throws ResourceNotFoundException {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
    }

    @Override
    public Order statusOrder(Order updatedOrder) throws ResourceNotFoundException {
        Optional<Order> existingOrderOpt = orderRepository.findById(updatedOrder.getId());
        if (existingOrderOpt.isPresent()) {
            var existingOrder = existingOrderOpt.get();
            existingOrder.setStatus(updatedOrder.getStatus());
            return orderRepository.save(existingOrder);
        } else {
            throw new ResourceNotFoundException("Order not found with id: " + updatedOrder.getId());
        }
    }
}
