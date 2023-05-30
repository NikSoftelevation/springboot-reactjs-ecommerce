package com.ecommerce_backend.service;

import com.ecommerce_backend.model.Product;
import com.ecommerce_backend.payload.ProductDto;

import java.util.List;

public interface ProductService {
    public ProductDto createProduct(ProductDto productDto, int categoryId);

    public List<ProductDto> getAllProducts();

    public ProductDto getProductById(int productId);

    public ProductDto updateProductByProductId(ProductDto productDto, int productId);

    public void deleteProductByProductId(int productId);
}