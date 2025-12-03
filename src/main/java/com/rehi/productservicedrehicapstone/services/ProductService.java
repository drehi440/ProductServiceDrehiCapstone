package com.rehi.productservicedrehicapstone.services;

import com.rehi.productservicedrehicapstone.exceptions.ProductNotFoundException;
import com.rehi.productservicedrehicapstone.models.Product;

import java.util.List;


import java.util.List;

public interface ProductService
{
    Product getProductById(long id) throws ProductNotFoundException;
    List<Product> getAllProducts();
    Product createProduct(String name, String description, double price,
                          String imageUrl, String category);
}