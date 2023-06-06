package com.ecommerce_backend.service;

import com.ecommerce_backend.payload.OrderDto;
import com.ecommerce_backend.payload.OrderRequest;

public interface OrderService {
    public OrderDto createOrder(OrderRequest orderRequest, String username);
}
