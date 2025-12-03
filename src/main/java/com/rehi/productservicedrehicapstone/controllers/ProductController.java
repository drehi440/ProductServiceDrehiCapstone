package com.rehi.productservicedrehicapstone.controllers;


import com.rehi.productservicedrehicapstone.dtos.CreateFakeStoreProductDto;
import com.rehi.productservicedrehicapstone.dtos.ProductResponseDto;
import com.rehi.productservicedrehicapstone.exceptions.ProductNotFoundException;
import com.rehi.productservicedrehicapstone.models.Product;
import com.rehi.productservicedrehicapstone.services.ProductDBService;
import com.rehi.productservicedrehicapstone.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController
{

    ProductService productService;
    ProductDBService productDBService;

    public ProductController(@Qualifier("productDBService")
                             ProductService productService,
                             ProductDBService productDBService)
    {
        this.productService = productService;
        this.productDBService = productDBService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(
            @PathVariable("id") long id) throws ProductNotFoundException {

        Product product = productService.getProductById(id);
        ProductResponseDto productResponseDto = ProductResponseDto.from(product);

        ResponseEntity<ProductResponseDto> responseEntity =
                new ResponseEntity<>(productResponseDto, HttpStatus.OK);

        return responseEntity;
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts()
    {
        List<Product> products = productService.getAllProducts();
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        
        // List<ProductResponseDto> productResponseDtos = products.stream()
        //     .map(ProductResponseDto::from)
        //     .collect(Collectors.toList());

        for(Product product : products)
        {
            ProductResponseDto productResponseDto = ProductResponseDto.from(product);
            productResponseDtos.add(productResponseDto);
        }

        return productResponseDtos;
    }

    @GetMapping("/products/search")
    public List<ProductResponseDto> searchProductsByName(
            @RequestParam("name") String name)
    {
        List<Product> products = productDBService.searchProductsByName(name);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

        for(Product product : products)
        {
            ProductResponseDto productResponseDto = ProductResponseDto.from(product);
            productResponseDtos.add(productResponseDto);
        }

        return productResponseDtos;
    }

    @GetMapping("/products/price-range")
    public List<ProductResponseDto> getProductsByPriceRange(
            @RequestParam("minPrice") double minPrice,
            @RequestParam("maxPrice") double maxPrice)
    {
        List<Product> products = productDBService.getProductsByPriceRange(minPrice, maxPrice);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

        for(Product product : products)
        {
            ProductResponseDto productResponseDto = ProductResponseDto.from(product);
            productResponseDtos.add(productResponseDto);
        }

        return productResponseDtos;
    }

    @GetMapping("/products/by-category")
    public List<ProductResponseDto> getProductsByCategory(
            @RequestParam("categoryName") String categoryName)
    {
        List<Product> products = productDBService.getProductsByCategoryName(categoryName);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

        for(Product product : products)
        {
            ProductResponseDto productResponseDto = ProductResponseDto.from(product);
            productResponseDtos.add(productResponseDto);
        }

        return productResponseDtos;
    }

    @GetMapping("/products/active")
    public List<ProductResponseDto> getActiveProducts()
    {
        List<Product> products = productDBService.getActiveProducts();
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

        for(Product product : products)
        {
            ProductResponseDto productResponseDto = ProductResponseDto.from(product);
            productResponseDtos.add(productResponseDto);
        }

        return productResponseDtos;
    }

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody
                                            CreateFakeStoreProductDto createFakeStoreProductDto)
    {
        Product product = productService.createProduct(
                createFakeStoreProductDto.getName(),
                createFakeStoreProductDto.getDescription(),
                createFakeStoreProductDto.getPrice(),
                createFakeStoreProductDto.getImageUrl(),
                createFakeStoreProductDto.getCategory()
        );

        ProductResponseDto productResponseDto = ProductResponseDto.from(product);

        return productResponseDto;
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProductById(
            @PathVariable("id") long id,
            @RequestBody CreateFakeStoreProductDto createFakeStoreProductDto)
            throws ProductNotFoundException
    {
        Product product = productService.updateProduct(
                id,
                createFakeStoreProductDto.getName(),
                createFakeStoreProductDto.getDescription(),
                createFakeStoreProductDto.getPrice(),
                createFakeStoreProductDto.getImageUrl(),
                createFakeStoreProductDto.getCategory());

        ProductResponseDto productResponseDto = ProductResponseDto.from(product);

        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") long id)
            throws ProductNotFoundException
    {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
