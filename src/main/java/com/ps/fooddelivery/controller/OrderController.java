package com.ps.fooddelivery.controller;

import com.ps.fooddelivery.dto.OrderDTO;
import com.ps.fooddelivery.dto.OrderResponseDTO;
import com.ps.fooddelivery.modal.Order;
import com.ps.fooddelivery.modal.OrderStatus;
import com.ps.fooddelivery.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        logger.info("Entering createOrder with orderDTO: {}", orderDTO);

        try {
            var order = new Order();
            order.setUserId(orderDTO.getUserId());
            order.setItems(orderDTO.getItems());
            order.setStatus(OrderStatus.PLACED); // Default status

            var savedOrder = orderService.createOrder(order);

            var orderResponseDTO = new OrderResponseDTO(
                    savedOrder.getId(),
                    savedOrder.getUserId(),
                    savedOrder.getItems(),
                    savedOrder.getStatus()
            );

            logger.info("Order created successfully with id: {}", savedOrder.getId());
            return new ResponseEntity<>(orderResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to create order: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable String id) {
        logger.info("Entering getOrderById with id: {}", id);

        try {
            var order = orderService.trackOrder(id);

            if (order == null) {
                logger.warn("Order not found with id: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            var orderResponseDTO = new OrderResponseDTO(
                    order.getId(),
                    order.getUserId(),
                    order.getItems(),
                    order.getStatus()
            );

            logger.info("Order retrieved successfully with id: {}", id);
            return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to retrieve order with id: {}: {}", id, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
