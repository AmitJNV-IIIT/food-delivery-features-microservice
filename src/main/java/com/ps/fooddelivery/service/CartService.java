package com.ps.fooddelivery.service;

import com.ps.fooddelivery.modal.Cart;

import java.util.List;

public interface CartService {
    void addToCart(Cart cart);
    List<Cart> getCart(String userId);
    List<Cart> getCartAll();
    void deleteItemFromCart(String id);
    void remove();
}
