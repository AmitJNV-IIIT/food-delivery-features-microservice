package com.ps.fooddelivery.controller;

import com.ps.fooddelivery.dto.CartDTO;
import com.ps.fooddelivery.exception.custom.ResourceNotFoundException;
import com.ps.fooddelivery.modal.Cart;
import com.ps.fooddelivery.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@CrossOrigin(origins = "http://localhost:3000")
 class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
     void testAddToCartSuccess() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setUserId("user123");
        cartDTO.setItems(List.of(new CartDTO.CartItemDTO("foodId1", 2)));
        cartDTO.setName("Cart Name");
        cartDTO.setPrice("10.00");

        Cart cart = new Cart();
        cart.setUserId(cartDTO.getUserId());
        cart.setItems(cartDTO.getItems().stream()
                .map(dto -> new Cart.CartItem(dto.getFoodId(), dto.getQuantity()))
                .collect(Collectors.toList()));
        cart.setName(cartDTO.getName());
        cart.setPrice(cartDTO.getPrice());

        doNothing().when(cartService).addToCart(any(Cart.class));

        ResponseEntity<String> response = cartController.addToCart(cartDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cart item added successfully", response.getBody());

        verify(cartService, times(1)).addToCart(any(Cart.class));
    }

    @Test
    void testAddToCartFailure() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setUserId("user123");
        cartDTO.setItems(List.of(new CartDTO.CartItemDTO("foodId1", 2)));
        cartDTO.setName("Cart Name");
        cartDTO.setPrice("10.00");

        Cart cart = new Cart();
        cart.setUserId(cartDTO.getUserId());
        cart.setItems(cartDTO.getItems().stream()
                .map(dto -> new Cart.CartItem(dto.getFoodId(), dto.getQuantity()))
                .collect(Collectors.toList()));
        cart.setName(cartDTO.getName());
        cart.setPrice(cartDTO.getPrice());

        doThrow(new RuntimeException("Exception")).when(cartService).addToCart(any(Cart.class));

        ResponseEntity<String> response = cartController.addToCart(cartDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to add cart item", response.getBody());
    }

    @Test
     void testGetCartItemsFound() {
        String userId = "user123";
        Cart cart = new Cart();
        List<Cart> cartList = Arrays.asList(cart);
        when(cartService.getCart(userId)).thenReturn(cartList);

        ResponseEntity<Cart> response = cartController.getCartItems(userId);
        assertEquals(cart, response.getBody());
        verify(cartService, times(1)).getCart(userId);
    }

    @Test
     void testGetCartItemsNotFound() {
        String userId = "user123";
        when(cartService.getCart(userId)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            cartController.getCartItems(userId);
        });
        verify(cartService, times(1)).getCart(userId);
    }

    @Test
     void testGetCartNumber() {
        List<Cart> cartAll = Arrays.asList(new Cart(), new Cart());
        when(cartService.getCartAll()).thenReturn(cartAll);

        ResponseEntity<Integer> response = cartController.getCartNumber();
        assertEquals(2, response.getBody());
        verify(cartService, times(1)).getCartAll();
    }

    @Test
    void testGetCartItemsAll() {
        List<Cart> cartAll = Arrays.asList(new Cart(), new Cart());
        when(cartService.getCartAll()).thenReturn(cartAll);

        ResponseEntity<List<Cart>> response = cartController.getCartItemsAll();
        assertEquals(cartAll, response.getBody());
        verify(cartService, times(1)).getCartAll();
    }

    @Test
     void testRemoveCartItem() {
        String id = "item123";
        doNothing().when(cartService).deleteItemFromCart(id);

        ResponseEntity<String> response = cartController.removeCartItem(id);
        assertEquals("Removed from cart", response.getBody());
        verify(cartService, times(1)).deleteItemFromCart(id);
    }

    @Test
    void testClearCart() {
        doNothing().when(cartService).remove();

        ResponseEntity<String> response = cartController.clearCart();
        assertEquals("Removed all", response.getBody());
        verify(cartService, times(1)).remove();
    }
}
