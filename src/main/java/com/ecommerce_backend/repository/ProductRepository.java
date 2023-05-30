package com.ecommerce_backend.repository;

import com.ecommerce_backend.model.Category;
import com.ecommerce_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    public List<Product> findByCategory(Category category);
}