package com.ecommerce_backend.repository;

import com.ecommerce_backend.model.Cart;
import com.ecommerce_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    public Optional<Cart> findByUser(User user);
}