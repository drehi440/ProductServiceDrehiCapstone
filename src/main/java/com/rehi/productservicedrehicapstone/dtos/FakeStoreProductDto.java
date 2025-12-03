package com.rehi.productservicedrehicapstone.dtos;

import com.rehi.productservicedrehicapstone.models.Category;
import com.rehi.productservicedrehicapstone.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto
{
    private long id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;

    public Product toProduct()
    {
        Product product = new Product();
        product.setProductId(id);
        product.setDescription(description);
        product.setProductName(title);
        product.setPrice(price);
        product.setImage(image);

        Category category1 = new Category();
        category1.setCategoryName(category);

        product.setCategory(category1);
        return product;
    }
}