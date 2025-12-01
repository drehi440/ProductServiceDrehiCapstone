package com.rehi.productservicedrehicapstone.services;


import com.rehi.productservicedrehicapstone.exceptions.ProductNotFoundException;
import com.rehi.productservicedrehicapstone.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productDBService")
public class ProductDBService implements ProductService
{

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(String name, String description, double price, String imageUrl, String category) {
        return null;
    }

    @Override
    public Product updateProduct(long id, String name, String description, double price,
                                 String imageUrl, String category) throws ProductNotFoundException
    {
        throw new UnsupportedOperationException("updateProduct is not implemented for ProductDBService");
    }
}
