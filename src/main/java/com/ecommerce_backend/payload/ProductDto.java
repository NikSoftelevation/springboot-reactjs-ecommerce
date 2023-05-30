package com.ecommerce_backend.payload;

import com.ecommerce_backend.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private int product_id;
    private String product_name;
    private double product_price;
    private boolean stock;
    private int product_quantity;
    private boolean isLive;
    private String product_imageName;
    private String product_desc;
    private Category category;
}