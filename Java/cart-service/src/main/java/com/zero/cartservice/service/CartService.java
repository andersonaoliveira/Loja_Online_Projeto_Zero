package com.zero.cartservice.service;

import com.zero.cartservice.model.Cart;
import com.zero.cartservice.model.CartItem;
import com.zero.cartservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Cart getCartByUserId(String userId) {
        return cartRepository.findByUserId(userId).orElse(new Cart());
    }

    public Cart addItemToCart(String userId, CartItem item) {
        Cart cart = getCartByUserId(userId);
        if (cart.getUserId() == null) {
            cart.setUserId(userId);
        }
        cart.addItem(item);
        return cartRepository.save(cart);
    }
    
    public Cart updateCartItem(String userId, CartItem item) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().stream()
                .filter(i -> i.getProductId().equals(item.getProductId()))
                .findFirst()
                .ifPresent(i -> {
                    i.setQuantity(item.getQuantity());
                    i.setPrice(item.getPrice());
                });
        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(String userId, String productId) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        return cartRepository.save(cart);
    }

    public void clearCart(String userId) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
