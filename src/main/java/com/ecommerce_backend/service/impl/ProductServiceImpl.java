package com.ecommerce_backend.service.impl;

import com.ecommerce_backend.exception.ResourceNotFoundException;
import com.ecommerce_backend.model.Category;
import com.ecommerce_backend.model.Product;
import com.ecommerce_backend.payload.ProductDto;
import com.ecommerce_backend.repository.CategoryRepository;
import com.ecommerce_backend.repository.ProductRepository;
import com.ecommerce_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto, int categoryId) {

        /*Fetch that category exists or not*/
        Category findById = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("No Category found with categoryId : " + categoryId));

        /*ProductDto to Product*/
        Product product = toEntity(productDto);
        product.setCategory(findById);

        Product save = productRepository.save(product);

        /*Product to productDto*/
        ProductDto dto = toDto(save);
        return dto;
    }

    @Override
    public List<ProductDto> getAllProducts() {

        /*ProductDto to Product*/
        List<Product> findAll = productRepository.findAll();

        List<ProductDto> findAllDto = findAll.stream().map(product -> toDto(product)).collect(Collectors.toList());

        return findAllDto;
    }

    @Override
    public ProductDto getProductById(int productId) {

        Product findById = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(" No Product found with productId :" + productId));

        ProductDto dto = toDto(findById);

        return dto;
    }

    @Override
    public ProductDto updateProductByProductId(ProductDto productDto, int productId) {
        Product productById = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("No Product found with productId :" + productId));

        productById.setProduct_desc(productDto.getProduct_desc());
        productById.setProduct_name(productDto.getProduct_name());
        productById.setProduct_price(productDto.getProduct_price());
        productById.setProduct_quantity(productDto.getProduct_quantity());
        productById.setProduct_imageName(productDto.getProduct_imageName());
        productById.setStock(productDto.isStock());
        productById.setLive(productDto.isLive());

        Product updatedProduct = productRepository.save(productById);

        ProductDto dto = toDto(updatedProduct);
        return dto;
    }

    @Override
    public void deleteProductByProductId(int productId) {

        Product productToDelete = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("No Product found with productId :" + productId));
        productRepository.delete(productToDelete);
    }

    public Product toEntity(ProductDto productDto) {

        Product p = new Product();
        p.setProduct_name(productDto.getProduct_name());
        p.setProduct_id(productDto.getProduct_id());
        p.setProduct_imageName(productDto.getProduct_imageName());
        p.setProduct_desc(productDto.getProduct_desc());
        p.setProduct_quantity(productDto.getProduct_quantity());
        p.setProduct_price(productDto.getProduct_price());
        p.setLive(productDto.isLive());
        p.setStock(productDto.isStock());
        return p;
    }

    /*Product to productDto*/
    public ProductDto toDto(Product product) {

        ProductDto productDto = new ProductDto();

        productDto.setProduct_id(product.getProduct_id());
        productDto.setProduct_imageName(product.getProduct_imageName());
        productDto.setProduct_name(product.getProduct_name());
        productDto.setProduct_desc(product.getProduct_desc());
        productDto.setProduct_price(product.getProduct_price());
        productDto.setProduct_quantity(product.getProduct_quantity());
        productDto.setStock(product.isStock());
        productDto.setLive(product.isLive());

        return productDto;
    }
}