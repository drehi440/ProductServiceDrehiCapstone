package com.rehi.productservicedrehicapstone.controllers;


import com.rehi.productservicedrehicapstone.dtos.ProductResponseDto;
import com.rehi.productservicedrehicapstone.exceptions.ProductNotFoundException;
import com.rehi.productservicedrehicapstone.models.Category;
import com.rehi.productservicedrehicapstone.models.Product;
import com.rehi.productservicedrehicapstone.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest
{
    @MockitoBean
    @Qualifier("productDBService")
    public ProductService productService;

    @Autowired
    public ProductController productController;


    @Test
    public void testGetProductByIdReturnsProductResponseDto() throws ProductNotFoundException
    {
        Product dummyProduct = new Product();
        dummyProduct.setId(1L);
        dummyProduct.setName("name");
        dummyProduct.setDescription("description");
        dummyProduct.setPrice(12.7);
        dummyProduct.setImageUrl("img.url");

        Category dummyCategory = new Category();
        dummyCategory.setId(1L);
        dummyCategory.setName("category");
        dummyCategory.setDescription("description");

        dummyProduct.setCategory(dummyCategory);

        when(productService.getProductById(1L)).thenReturn(dummyProduct);

        ProductResponseDto productResponseDto
                = productController.getProductById(1L);

        assertEquals(1L, productResponseDto.getId());
        assertEquals("name", productResponseDto.getName());
        assertEquals("description", productResponseDto.getDescription());
        assertEquals("img.url", productResponseDto.getImageUrl());
        assertEquals(12.7, productResponseDto.getPrice());
        assertEquals(1L, dummyCategory.getId());
    }

    @Test
    public void testGetProductByIdReturnsNull() throws ProductNotFoundException
    {
        when(productService.getProductById(1L)).thenReturn(null);

        ProductResponseDto productResponseDto = productController.getProductById(1L);

        assertNull(productResponseDto);
    }
}
