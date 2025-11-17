package com.rehi.productservicedrehicapstone.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto
{
    private long id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private String category;
}
