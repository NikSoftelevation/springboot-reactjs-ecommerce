package com.ecommerce_backend.repository;

import com.ecommerce_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByEmail(String email);

    public List<User> findByName(String name);

    public User findByEmailAndPassword(String email, String name);

    public List<User> findByActiveTrue();

    public List<User> findByAboutIsNotNull();

    public List<User> findByNameStartingWith(String prefix);

    public List<User> findByNameContaining(String infix);

    public List<User> findByNameLike(String like);

    @Query("select u from User u")
    public List<User> getAllUsers();
}