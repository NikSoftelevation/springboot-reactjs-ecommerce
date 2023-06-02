package com.ecommerce_backend.service.impl;

import com.ecommerce_backend.exception.ResourceNotFoundException;
import com.ecommerce_backend.model.Cart;
import com.ecommerce_backend.model.CartItem;
import com.ecommerce_backend.model.Product;
import com.ecommerce_backend.model.User;
import com.ecommerce_backend.payload.CartDto;
import com.ecommerce_backend.payload.ItemRequest;
import com.ecommerce_backend.repository.CartRepository;
import com.ecommerce_backend.repository.ProductRepository;
import com.ecommerce_backend.repository.UserRepository;
import com.ecommerce_backend.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public CartDto addItem(ItemRequest itemRequest, String username) {

        int productId = itemRequest.getProductId();

        int quantity = itemRequest.getQuantity();


        User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found with email " + username));

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("No Product found with productId " + productId));

        if (!product.isStock()) {

            new ResourceNotFoundException("Product is out of stock");
        }

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        double totalPrice = product.getProduct_price() * quantity;
        cartItem.setTotalPrice(totalPrice);


        /*Getting cart from user*/
        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();

        }
        cartItem.setCart(cart);

        Set<CartItem> items = cart.getItems();

        AtomicReference<Boolean> flag = new AtomicReference<>(false);
        Set<CartItem> newProduct = items.stream().map((i) -> {

            if (i.getProduct().getProduct_id() == product.getProduct_id()) {
                i.setQuantity(quantity);
                i.setTotalPrice(totalPrice);
                flag.set(true);
            }
            return i;
        }).collect(Collectors.toSet());

        if (flag.get()) {

            items.clear();
            items.addAll(newProduct);

        } else {
            cartItem.setCart(cart);
            items.add(cartItem);
        }
        Cart save = cartRepository.save(cart);

        return modelMapper.map(save, CartDto.class);
    }

    @Override
    public CartDto getAll(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("NO user found with email " + email));

        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("There is no cart"));

        return modelMapper.map(cart, CartDto.class);
    }
}