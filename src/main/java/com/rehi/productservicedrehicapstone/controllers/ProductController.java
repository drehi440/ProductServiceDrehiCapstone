package com.rehi.productservicedrehicapstone.controllers;


import com.rehi.productservicedrehicapstone.dtos.ProductResponseDto;
import com.rehi.productservicedrehicapstone.models.Product;
import com.rehi.productservicedrehicapstone.services.FakeStoreProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController
{

    FakeStoreProductService fakeStoreProductService;

    public ProductController(FakeStoreProductService fakeStoreProductService)
    {
        this.fakeStoreProductService = fakeStoreProductService;
    }


//    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable long id)
    {
//       ProductResponseDto dummyProductResponseDto = new ProductResponseDto();
//       dummyProductResponseDto.setId(1);
//       dummyProductResponseDto.setName("Product " + id);
//       dummyProductResponseDto.setDescription("Product Description ");
//       dummyProductResponseDto.setPrice(100);
//       dummyProductResponseDto.setImageUrl("https://dummy.image");
//
//
//        //Jackson - library used to convert DTO to application/json
//        return dummyProductResponseDto;

        Product product = fakeStoreProductService.getProductById(id);
        ProductResponseDto productResponseDto = ProductResponseDto.from(product);

        ResponseEntity<ProductResponseDto> responseEntity =
                new ResponseEntity<>(productResponseDto, HttpStatus.OK);

        return responseEntity;

    }

}
