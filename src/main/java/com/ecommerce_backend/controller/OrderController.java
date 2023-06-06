package com.ecommerce_backend.controller;

import com.ecommerce_backend.payload.ApiResponse;
import com.ecommerce_backend.payload.OrderDto;
import com.ecommerce_backend.payload.OrderRequest;
import com.ecommerce_backend.payload.OrderResponse;
import com.ecommerce_backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest orderRequest, Principal principal) {
        return new ResponseEntity<>(orderService.createOrder(orderRequest, principal.getName()), HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{orderId}")
    public ResponseEntity<?> cancelOrderByOrderId(@PathVariable("orderId") int orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(new ApiResponse("Order Deleted Successfully", true), HttpStatus.GONE);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderByOrderId(@PathVariable("orderId") int orderId) {
        return new ResponseEntity<>(orderService.getOrderByOrderId(orderId), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public OrderResponse findAllOrders(
            @RequestParam(defaultValue = "2", value = "pageSize") int pageSize
            , @RequestParam(defaultValue = "0", value = "pageNumber") int pageNumber) {

        OrderResponse findAllOrders = orderService.findAllOrders(pageNumber, pageSize);
        return findAllOrders;
    }
}