package com.ps.fooddelivery.controller;

import com.ps.fooddelivery.dto.OrderDTO;
import com.ps.fooddelivery.dto.OrderResponseDTO;
import com.ps.fooddelivery.modal.Order;
import com.ps.fooddelivery.modal.OrderStatus;
import com.ps.fooddelivery.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class OrderControllerTest {

   @InjectMocks
   private OrderController orderController;  // Controller class to be tested

   @Mock
   private OrderService orderService;  // Mocked service

   @BeforeEach
    void setUp() {
      MockitoAnnotations.openMocks(this);  // Initialize mocks before each test
   }

   @Test
    void testCreateOrderSuccess() {
      OrderDTO orderDTO = new OrderDTO();
      orderDTO.setUserId("user123");
      orderDTO.setItems(List.of("item1", "item2"));
      orderDTO.setStatus(OrderStatus.PLACED);

      Order order = new Order();
      order.setId("order123");
      order.setUserId(orderDTO.getUserId());
      order.setItems(orderDTO.getItems());
      order.setStatus(orderDTO.getStatus());

      when(orderService.createOrder(any(Order.class))).thenReturn(order);

      ResponseEntity<OrderResponseDTO> response = orderController.createOrder(orderDTO);

      assertEquals(HttpStatus.CREATED, response.getStatusCode());
      assertEquals(order.getId(), response.getBody().getId());
      assertEquals(order.getUserId(), response.getBody().getUserId());
      assertEquals(order.getItems(), response.getBody().getItems());
      assertEquals(order.getStatus(), response.getBody().getStatus());

      verify(orderService, times(1)).createOrder(any(Order.class));
   }

   @Test
   void testCreateOrderFailure() {
      OrderDTO orderDTO = new OrderDTO();
      orderDTO.setUserId("user123");
      orderDTO.setItems(List.of("item1", "item2"));
      orderDTO.setStatus(OrderStatus.PLACED);

      when(orderService.createOrder(any(Order.class))).thenThrow(new RuntimeException("Exception"));

      ResponseEntity<OrderResponseDTO> response = orderController.createOrder(orderDTO);

      assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
      assertEquals(null, response.getBody());
   }

   @Test
    void testGetOrderByIdSuccess() {
      Order order = new Order();
      order.setId("order123");
      order.setUserId("user123");
      order.setItems(List.of("item1", "item2"));
      order.setStatus(OrderStatus.PLACED);

      OrderResponseDTO orderResponseDTO = new OrderResponseDTO(
              order.getId(),
              order.getUserId(),
              order.getItems(),
              order.getStatus()
      );

      when(orderService.trackOrder(anyString())).thenReturn(order);

      ResponseEntity<OrderResponseDTO> response = orderController.getOrderById("order123");

      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertEquals(orderResponseDTO.getId(), response.getBody().getId());
      assertEquals(orderResponseDTO.getUserId(), response.getBody().getUserId());
      assertEquals(orderResponseDTO.getItems(), response.getBody().getItems());
      assertEquals(orderResponseDTO.getStatus(), response.getBody().getStatus());

      verify(orderService, times(1)).trackOrder(anyString());
   }

   @Test
    void testGetOrderByIdFailure() {
      when(orderService.trackOrder(anyString())).thenThrow(new RuntimeException("Exception"));

      ResponseEntity<OrderResponseDTO> response = orderController.getOrderById("order123");

      assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
      assertEquals(null, response.getBody());
   }

   @Test
    void testGetOrderByIdNotFound() {
      when(orderService.trackOrder(anyString())).thenReturn(null);

      ResponseEntity<OrderResponseDTO> response = orderController.getOrderById("order123");

      assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
      assertEquals(null, response.getBody());
   }


}
