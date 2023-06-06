package com.ecommerce_backend.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private int pageSize;
    private int pageNumber;
    private int totalPage;
    private int totalElement;
    private List<OrderDto> content;
    private boolean isLastPage;
}