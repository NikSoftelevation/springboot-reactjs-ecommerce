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
public class OrderItemDto {

    private int orderItemId;
    private ProductDto productDto;
    private double totalProductPrice;

    @JsonIgnore
    private OrderDto orderDto;
}