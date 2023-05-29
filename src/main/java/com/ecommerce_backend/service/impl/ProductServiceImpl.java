package com.ecommerce_backend.service.impl;

import com.ecommerce_backend.model.Product;
import com.ecommerce_backend.repository.ProductRepository;
import com.ecommerce_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int productId) {
        return productRepository.findById(productId).get();
    }

    @Override
    public Product updateProductByProductId(Product product, int productId) {
        Product productById = productRepository.findById(productId).get();

        productById.setProduct_desc(product.getProduct_desc());
        productById.setProduct_name(product.getProduct_name());
        productById.setProduct_price(product.getProduct_price());
        productById.setProduct_quantity(product.getProduct_quantity());
        productById.setProduct_imageName(product.getProduct_imageName());
        productById.setStock(product.isStock());
        productById.setLive(product.isLive());

        Product updatedProduct = productRepository.save(productById);
        return updatedProduct;
    }

    @Override
    public void deleteProductByProductId(int productId) {

        Product productToDelete = productRepository.findById(productId).get();
        productRepository.delete(productToDelete);
    }
}