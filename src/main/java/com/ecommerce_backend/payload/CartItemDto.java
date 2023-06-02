package com.ecommerce_backend.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

    private int cartItemId;
    private int quantity;
    private double totalPrice;
    @JsonIgnore
    private CartDto cartDto;
    private ProductDto productDto;
}