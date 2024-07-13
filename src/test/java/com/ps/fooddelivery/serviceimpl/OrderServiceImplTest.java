package com.ps.fooddelivery.serviceimpl;

import com.ps.fooddelivery.exception.custom.ResourceNotFoundException;
import com.ps.fooddelivery.modal.Order;
import com.ps.fooddelivery.modal.OrderStatus;
import com.ps.fooddelivery.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testCreateOrder() {
        Order order = new Order();
        order.setStatus(OrderStatus.PLACED);
        when(orderRepository.save(order)).thenReturn(order);

        Order createdOrder = orderService.createOrder(order);
        assertEquals(OrderStatus.PLACED, createdOrder.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
     void testTrackOrderFound() throws ResourceNotFoundException {
        String orderId = "order123";
        Order order = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order foundOrder = orderService.trackOrder(orderId);
        assertEquals(order, foundOrder);
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
     void testTrackOrderNotFound() {
        String orderId = "order123";
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.trackOrder(orderId);
        });
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
     void testStatusOrderUpdated() throws ResourceNotFoundException {
        Order existingOrder = new Order();
        existingOrder.setId("order123");
        existingOrder.setStatus(OrderStatus.PLACED);

        Order updatedOrder = new Order();
        updatedOrder.setId("order123");
        updatedOrder.setStatus(OrderStatus.PACKED);

        when(orderRepository.findById("order123")).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(existingOrder)).thenReturn(updatedOrder);

        Order resultOrder = orderService.statusOrder(updatedOrder);
        assertNotEquals(OrderStatus.PLACED, resultOrder.getStatus());
        verify(orderRepository, times(1)).findById("order123");
        verify(orderRepository, times(1)).save(existingOrder);
    }

    @Test
     void testStatusOrderNotFound() {
        Order updatedOrder = new Order();
        updatedOrder.setId("order123");
        updatedOrder.setStatus(OrderStatus.PLACED);

        when(orderRepository.findById("order123")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.statusOrder(updatedOrder);
        });
        verify(orderRepository, times(1)).findById("order123");
    }
}
