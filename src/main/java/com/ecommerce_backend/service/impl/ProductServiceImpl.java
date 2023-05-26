package com.ecommerce_backend.service.impl;

import com.ecommerce_backend.model.Product;
import com.ecommerce_backend.repository.ProductRepository;
import com.ecommerce_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}