package com.ecommerce_backend.service;

import com.ecommerce_backend.payload.OrderDto;
import com.ecommerce_backend.payload.OrderRequest;
import com.ecommerce_backend.payload.OrderResponse;

public interface OrderService {
    public OrderDto createOrder(OrderRequest orderRequest, String username);

    public void cancelOrder(int orderId);

    public OrderDto getOrderByOrderId(int orderId);

    public OrderResponse findAllOrders(int pageNumber, int pageSize);
}