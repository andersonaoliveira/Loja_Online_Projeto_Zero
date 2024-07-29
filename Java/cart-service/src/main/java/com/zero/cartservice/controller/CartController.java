package com.zero.cartservice.controller;

import com.zero.cartservice.model.Cart;
import com.zero.cartservice.model.CartItem;
import com.zero.cartservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable String userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<Cart> addItem(@PathVariable String userId, @RequestBody CartItem item) {
        return ResponseEntity.ok(cartService.addItemToCart(userId, item));
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<Cart> updateItem(@PathVariable String userId, @RequestBody CartItem item) {
        return ResponseEntity.ok(cartService.updateCartItem(userId, item));
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<Cart> removeItem(@PathVariable String userId, @PathVariable String productId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(userId, productId));
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
