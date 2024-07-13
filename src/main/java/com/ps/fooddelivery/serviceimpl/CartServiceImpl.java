package com.ps.fooddelivery.serviceimpl;

import com.ps.fooddelivery.exception.custom.ResourceNotFoundException;
import com.ps.fooddelivery.modal.Cart;
import com.ps.fooddelivery.repository.CartRepository;
import com.ps.fooddelivery.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CartRepository cartRepository;

    @Override
    public void addToCart(Cart cart) {
        logger.info("Entering addToCart with cart: {}", cart);
        cartRepository.save(cart);
        logger.info("Cart item added successfully: {}", cart);
    }

    @Override
    public List<Cart> getCart(String userId) {
        logger.info("Entering getCart with userId: {}", userId);
        List<Cart> cartList = cartRepository.findByUserId(userId);
        if (cartList.isEmpty()) {
            logger.error("No cart items found for userId: {}", userId);
            throw new ResourceNotFoundException("Cart items not found for userId: " + userId);
        }
        logger.info("Cart items retrieved successfully for userId: {}", userId);
        return cartList;
    }

    @Override
    public List<Cart> getCartAll() {
        logger.info("Entering getCartAll");
        List<Cart> cartAll = cartRepository.findAll();
        logger.info("All cart items retrieved successfully");
        return cartAll;
    }

    @Override
    public void deleteItemFromCart(String id) {
        logger.info("Entering deleteItemFromCart with id: {}", id);
        if (!cartRepository.existsById(id)) {
            logger.error("Cart item with id: {} not found", id);
            throw new ResourceNotFoundException("Cart item not found with id: " + id);
        }
        cartRepository.deleteById(id);
        logger.info("Cart item deleted successfully with id: {}", id);
    }

    @Override
    public void remove() {
        logger.info("Entering remove");
        cartRepository.deleteAll();
        logger.info("All cart items removed successfully");
    }
}
