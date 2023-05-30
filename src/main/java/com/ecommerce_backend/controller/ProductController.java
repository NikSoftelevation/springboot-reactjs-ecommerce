package com.ecommerce_backend.controller;

import com.ecommerce_backend.model.Product;
import com.ecommerce_backend.payload.AppConstants;
import com.ecommerce_backend.payload.ProductDto;
import com.ecommerce_backend.payload.ProductResponse;
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

    /*Implementing pagination in getAllProducts API*/
    @GetMapping("/products")
    public ProductResponse getAllProducts(
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE_STRING, required = false) int pageSize,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER_STRING, required = false) int pageNumber,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_STRING, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_STRING, required = false) String sortDir) {
        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir);
        return productResponse;
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

    /*find product by category*/
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductByCategory(@PathVariable("categoryId") int categoryId) {

        List<ProductDto> productsByCategory = productService.findProductByCategory(categoryId);
        return new ResponseEntity<>(productsByCategory, HttpStatus.ACCEPTED);
    }
}