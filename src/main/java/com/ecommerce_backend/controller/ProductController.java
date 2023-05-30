package com.ecommerce_backend.controller;

import com.ecommerce_backend.model.Product;
import com.ecommerce_backend.payload.ProductDto;
import com.ecommerce_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create/{categoryId}")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto, @PathVariable("categoryId") int categoryId) {

        ProductDto createProduct = productService.createProduct(productDto, categoryId);
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") int productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable("productId") int productId) {
        return new ResponseEntity<>(productService.updateProductByProductId(new ProductDto(), productId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProductByProductId(@PathVariable("productId") int productId) {
        productService.deleteProductByProductId(productId);
        return new ResponseEntity<>("Product Deleted Successfully", HttpStatus.GONE);
    }
}