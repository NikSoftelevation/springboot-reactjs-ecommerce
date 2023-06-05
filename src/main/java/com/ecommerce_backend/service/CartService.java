package com.ecommerce_backend.service;

import com.ecommerce_backend.payload.CartDto;
import com.ecommerce_backend.payload.ItemRequest;

public interface CartService {
    public CartDto addItem(ItemRequest itemRequest, String username);

    public CartDto getAll(String email);

    public CartDto getCartByCartId(int cartId);

    public CartDto removeCartItemFromCart(String username, int productId);
}