package com.ecommerce_backend.payload;

import com.ecommerce_backend.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private int categoryId;
    private String title;
    private Set<Product> product;
}