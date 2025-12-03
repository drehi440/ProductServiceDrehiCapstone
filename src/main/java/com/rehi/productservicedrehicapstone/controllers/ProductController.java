package com.rehi.productservicedrehicapstone.controllers;

import com.rehi.productservicedrehicapstone.dtos.CategoryDto;
import com.rehi.productservicedrehicapstone.dtos.ProductResponseDto;
import com.rehi.productservicedrehicapstone.exceptions.ProductNotFoundException;
import com.rehi.productservicedrehicapstone.services.ProductDBService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductDBService productDBService;

    /**
     * GET /api/products
     * Supports optional keyword search and pagination.
     */
    @GetMapping("/products")
    public Page<ProductResponseDto> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("productName").ascending());

        if (keyword != null && !keyword.trim().isEmpty()) {
            log.info("Searching products with keyword='{}', page={}, size={}", keyword, page, size);
            return productDBService.searchProducts(keyword, pageable);
        }

        log.info("Fetching all products page={}, size={}", page, size);
        return productDBService.getAllProducts(pageable);
    }

    /**
     * GET /api/products/{id}
     * Returns details of a single product.
     */
    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        log.info("Fetching product with id={}", id);
        return productDBService.getProductDetails(id);
    }

    /**
     * GET /api/categories/{categoryId}/products
     * Browses products by a specific category with pagination.
     */
    @GetMapping("/categories/{categoryId}/products")
    public Page<ProductResponseDto> getProductsByCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("productName").ascending());
        log.info("Fetching products for categoryId={}, page={}, size={}", categoryId, page, size);
        return productDBService.getProductsByCategory(categoryId, pageable);
    }

    /**
     * GET /api/categories
     * Lists all categories for building the browse menu.
     */
    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories() {
        log.info("Fetching all categories for browse menu");
        return productDBService.getAllCategories();
    }
}
