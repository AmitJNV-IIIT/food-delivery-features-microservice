package com.ps.fooddelivery.serviceimpl;

import com.ps.fooddelivery.exception.custom.ResourceNotFoundException;
import com.ps.fooddelivery.modal.Cart;
import com.ps.fooddelivery.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

 class CartServiceImplTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private CartRepository cartRepository;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testAddToCart() {
        Cart cart = new Cart();
        cart.setId("cart123");

        cartService.addToCart(cart);  // Call the method under test
        verify(cartRepository, times(1)).save(cart);  // Verify that the save method was called once
    }

    @Test
     void testGetCartFound() {
        String userId = "user123";
        List<Cart> cartList = new ArrayList<>();
        Cart cart = new Cart();
        cart.setUserId(userId);
        cartList.add(cart);

        when(cartRepository.findByUserId(userId)).thenReturn(cartList);  // Mock repository behavior

        List<Cart> result = cartService.getCart(userId);  // Call the method under test
        assertEquals(cartList, result);  // Verify the result
        verify(cartRepository, times(1)).findByUserId(userId);  // Verify that the findByUserId method was called once
    }

    @Test
     void testGetCartNotFound() {
        String userId = "user123";

        when(cartRepository.findByUserId(userId)).thenReturn(new ArrayList<>());  // Mock repository behavior

        assertThrows(ResourceNotFoundException.class, () -> {
            cartService.getCart(userId);  // Call the method under test
        });
        verify(cartRepository, times(1)).findByUserId(userId);  // Verify that the findByUserId method was called once
    }

    @Test
     void testGetCartAll() {
        List<Cart> cartAll = new ArrayList<>();
        Cart cart = new Cart();
        cart.setId("cart123");
        cartAll.add(cart);

        when(cartRepository.findAll()).thenReturn(cartAll);  // Mock repository behavior

        List<Cart> result = cartService.getCartAll();  // Call the method under test
        assertEquals(cartAll, result);  // Verify the result
        verify(cartRepository, times(1)).findAll();  // Verify that the findAll method was called once
    }

    @Test
     void testDeleteItemFromCart() {
        String cartId = "cart123";

        when(cartRepository.existsById(cartId)).thenReturn(true);  // Mock repository behavior

        cartService.deleteItemFromCart(cartId);  // Call the method under test
        verify(cartRepository, times(1)).deleteById(cartId);  // Verify that the deleteById method was called once
    }

    @Test
     void testDeleteItemFromCartNotFound() {
        String cartId = "cart123";

        when(cartRepository.existsById(cartId)).thenReturn(false);  // Mock repository behavior

        assertThrows(ResourceNotFoundException.class, () -> {
            cartService.deleteItemFromCart(cartId);  // Call the method under test
        });
        verify(cartRepository, times(1)).existsById(cartId);  // Verify that the existsById method was called once
    }

    @Test
     void testRemove() {
        cartService.remove();  // Call the method under test
        verify(cartRepository, times(1)).deleteAll();  // Verify that the deleteAll method was called once
    }
}
