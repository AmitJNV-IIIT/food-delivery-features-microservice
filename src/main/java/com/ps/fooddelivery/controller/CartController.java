package com.ps.fooddelivery.controller;
import com.ps.fooddelivery.dto.CartDTO;
import com.ps.fooddelivery.exception.custom.ResourceNotFoundException;
import com.ps.fooddelivery.modal.Cart;
import com.ps.fooddelivery.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartDTO cartDTO) {
        logger.info("Entering addToCart with cartDTO: {}", cartDTO);

        // Convert CartDTO to Cart entity
        var cart = new Cart();
        cart.setUserId(cartDTO.getUserId());
        cart.setName(cartDTO.getName());
        cart.setPrice(cartDTO.getPrice());
        cart.setItems(cartDTO.getItems().stream()
                .map(dto -> new Cart.CartItem(dto.getFoodId(), dto.getQuantity()))
                .collect(Collectors.toList()));

        try {
            cartService.addToCart(cart);
            logger.info("Cart item added successfully: {}", cart);
            return ResponseEntity.ok("Cart item added successfully");
        } catch (Exception e) {
            logger.error("Failed to add cart item: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add cart item");
        }
    }

    @GetMapping
    public ResponseEntity<Cart> getCartItems(@RequestParam String userId) {
        logger.info("Entering getCartItems with userId: {}", userId);
        List<Cart> cartList = cartService.getCart(userId);
        if (cartList.isEmpty()) {
            throw new ResourceNotFoundException("No cart items found for userId: " + userId);
        }
        var cart = cartList.get(0);
        logger.info("Cart items retrieved successfully for userId: {}", userId);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/cartQuantity")
    public ResponseEntity<Integer> getCartNumber() {
        List<Cart> cartAll = cartService.getCartAll();
        int totalQuantity = cartAll.size();
        return ResponseEntity.ok(totalQuantity);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cart>> getCartItemsAll() {
        logger.info("Entering getCartItemsAll");
        List<Cart> cartAll = cartService.getCartAll();
        logger.info("All cart items retrieved successfully");
        return ResponseEntity.ok(cartAll);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCartItem(@PathVariable String id) {
        logger.info("Entering removeCartItem with id: {}", id);
        cartService.deleteItemFromCart(id);
        logger.info("Item removed from cart successfully with id: {}", id);
        return ResponseEntity.ok("Removed from cart");
    }

    @DeleteMapping
    public ResponseEntity<String> clearCart() {
        logger.info("Entering clearCart");
        cartService.remove();
        logger.info("All items removed from cart successfully");
        return ResponseEntity.ok("Removed all");
    }
}
