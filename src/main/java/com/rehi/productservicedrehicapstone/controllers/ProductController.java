package com.rehi.productservicedrehicapstone.controllers;


import com.rehi.productservicedrehicapstone.dtos.ProductResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController
{
//    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(@PathVariable long id)
    {
       ProductResponseDto dummyProductResponseDto = new ProductResponseDto();
       dummyProductResponseDto.setId(1);
       dummyProductResponseDto.setName("Product " + id);
       dummyProductResponseDto.setDescription("Product Description ");
       dummyProductResponseDto.setPrice(100);
       dummyProductResponseDto.setImageUrl("https://dummy.image");


        //Jackson - library used to convert DTO to application/json
        return dummyProductResponseDto;
    }

}
