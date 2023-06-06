package com.ecommerce_backend.service.impl;

import com.ecommerce_backend.exception.ResourceNotFoundException;
import com.ecommerce_backend.model.*;
import com.ecommerce_backend.payload.OrderDto;
import com.ecommerce_backend.payload.OrderRequest;
import com.ecommerce_backend.payload.OrderResponse;
import com.ecommerce_backend.repository.CartRepository;
import com.ecommerce_backend.repository.OrderRepository;
import com.ecommerce_backend.repository.UserRepository;
import com.ecommerce_backend.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(OrderRequest orderRequest, String username) {

        User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("No username found with username " + username));

        int cartId = orderRequest.getCartId();
        String orderAddress = orderRequest.getOrderAddress();

        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("No Cart found with cartId " + cartId));


        Set<CartItem> items = cart.getItems();

        Order order = new Order();
        AtomicReference<Double> totalOrderPrice = new AtomicReference<>(0.0);

        Set<OrderItem> orderItems = items.stream().map((cartItem) -> {

            OrderItem orderItem = new OrderItem();

            orderItem.setProductQuantity(cartItem.getQuantity());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setTotalProductPrice(cartItem.getTotalPrice());
            orderItem.setOrder(order);

            totalOrderPrice.set(totalOrderPrice.get() + orderItem.getTotalProductPrice());

            int productId = orderItem.getProduct().getProduct_id();

            return orderItem;

        }).collect(Collectors.toSet());

        order.setBillingAddress(orderAddress);
        order.setOrderDelivered(null);
        order.setOrderStatus("CREATED");

        order.setPaymentStatus("NOT PAID");
        order.setUser(user);
        order.setOrderItem(orderItems);
        order.setOrderAmt(totalOrderPrice.get());
        order.setOrderCreatedAt(new Date());
        Order save;

        if (order.getOrderAmt() > 0) {
            save = orderRepository.save(order);
            cart.getItems().clear();

            cartRepository.save(cart);
            System.out.println("Hello");
        } else {
            System.out.println(order.getOrderAmt());
            throw new ResourceNotFoundException("Please Add Cart First and then place your order");

        }
        return modelMapper.map(save, OrderDto.class);
    }

    @Override
    public void cancelOrder(int orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("No Order found with orderId " + orderId));
        orderRepository.delete(order);
    }

    public OrderDto getOrderByOrderId(int orderId) {
        Order orderById = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("No Order found with orderId " + orderId));
        return modelMapper.map(orderById, OrderDto.class);
    }

    @Override
    public OrderResponse findAllOrders(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Order> findAll = orderRepository.findAll(pageable);
        List<Order> content = findAll.getContent();

        /*Change order to orderDto*/
        List<OrderDto> collect = content.stream().map((each) -> modelMapper.map(each, OrderDto.class)).collect(Collectors.toList());

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setContent(collect);
        orderResponse.setPageNumber(findAll.getNumber());
        orderResponse.setLastPage(findAll.isLast());
        orderResponse.setPageSize(findAll.getSize());
        orderResponse.setTotalPage(findAll.getTotalPages());
        orderResponse.setTotalElement((int) findAll.getTotalElements());
        return orderResponse;
    }
}