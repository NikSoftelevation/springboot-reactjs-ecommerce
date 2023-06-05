package com.ecommerce_backend.controller;

import com.ecommerce_backend.payload.CartDto;
import com.ecommerce_backend.payload.ItemRequest;
import com.ecommerce_backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartDto> addToCart(@RequestBody ItemRequest itemRequest, Principal principal) {

        CartDto cartDto = cartService.addItem(itemRequest, principal.getName());

        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getAllCart(Principal principal) {
        CartDto getAllCart = cartService.getAll(principal.getName());
        return new ResponseEntity<>(getAllCart, HttpStatus.OK);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCartByCArtId(@PathVariable("cartId") int cartId) {
        return new ResponseEntity<>(cartService.getCartByCartId(cartId), HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<CartDto> deleteCartItemFromCart(@PathVariable("productId") int productId, Principal principal) {
        CartDto remove = cartService.removeCartItemFromCart(principal.getName(), productId);
        return new ResponseEntity<>(remove, HttpStatus.UPGRADE_REQUIRED);
    }
}