package com.rehi.productservicedrehicapstone.services;

import com.rehi.productservicedrehicapstone.dtos.FakeStoreProductDto;
import com.rehi.productservicedrehicapstone.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeStoreProductService implements ProductService {

    RestTemplate restTemplate;
    public FakeStoreProductService(RestTemplate restTemplate)
    {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Product getProductById(long id)
    {
        FakeStoreProductDto fakeStoreProductDto =
        restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class);


        return fakeStoreProductDto.toProduct();



    }


}
