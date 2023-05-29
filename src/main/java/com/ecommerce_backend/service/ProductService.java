package com.ecommerce_backend.service;

import com.ecommerce_backend.model.Product;

import java.util.List;

public interface ProductService {
    public Product createProduct(Product product);

    public List<Product> getAllProducts();

    public Product getProductById(int productId);

    public Product updateProductByProductId(Product product, int productId);

    public void deleteProductByProductId(int productId);
}