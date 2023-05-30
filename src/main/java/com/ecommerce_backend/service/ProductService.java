package com.ecommerce_backend.service;

import com.ecommerce_backend.model.Product;
import com.ecommerce_backend.payload.ProductDto;
import com.ecommerce_backend.payload.ProductResponse;

import java.util.List;

public interface ProductService {
    public ProductDto createProduct(ProductDto productDto, int categoryId);

    public ProductResponse getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir);

    public ProductDto getProductById(int productId);

    public ProductDto updateProductByProductId(ProductDto productDto, int productId);

    public List<ProductDto> findProductByCategory(int categoryId);

    public void deleteProductByProductId(int productId);
}