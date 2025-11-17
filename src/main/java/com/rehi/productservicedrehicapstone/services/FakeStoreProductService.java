package com.rehi.productservicedrehicapstone.services;

import com.rehi.productservicedrehicapstone.dtos.FakeStoreProductDto;
import com.rehi.productservicedrehicapstone.dtos.FakeStoreProductRequestDto;
import com.rehi.productservicedrehicapstone.exceptions.ProductNotFoundException;
import com.rehi.productservicedrehicapstone.models.Product;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FakeStoreProductService implements ProductService
{
    RestTemplate restTemplate;
    public FakeStoreProductService(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        FakeStoreProductDto fakeStoreProductDto =
                restTemplate.getForObject(
                        "https://fakestoreapi.com/products/" + id,
                        FakeStoreProductDto.class);

        if(fakeStoreProductDto == null)
        {
            throw new ProductNotFoundException("The product for id " + id + " does not exist");
        }

        return fakeStoreProductDto.toProduct();
    }

    @Override
    public List<Product> getAllProducts()
    {
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class);

        if(fakeStoreProductDtos == null)
        {
            return new ArrayList<>();
        }

        List<Product> products = new ArrayList<>();

        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos)
        {
            Product product = fakeStoreProductDto.toProduct();
            products.add(product);
        }

        return products;
    }

    @Override
    public Product createProduct(String name,
                                 String description, double price,
                                 String imageUrl, String category)
    {
        FakeStoreProductRequestDto fakeStoreProductRequestDto =
                new FakeStoreProductRequestDto();

        fakeStoreProductRequestDto.setTitle(name);
        fakeStoreProductRequestDto.setDescription(description);
        fakeStoreProductRequestDto.setPrice(price);
        fakeStoreProductRequestDto.setImage(imageUrl);
        fakeStoreProductRequestDto.setCategory(category);

        FakeStoreProductDto fakeStoreProductDto = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                fakeStoreProductRequestDto,
                FakeStoreProductDto.class);

        if(fakeStoreProductDto == null)
        {
            throw new IllegalStateException("Failed to create product");
        }

        return fakeStoreProductDto.toProduct();
    }

    @Override
    public Product updateProduct(long id, String name,
                                 String description, double price,
                                 String imageUrl, String category) throws ProductNotFoundException
    {
        FakeStoreProductRequestDto fakeStoreProductRequestDto =
                new FakeStoreProductRequestDto();

        fakeStoreProductRequestDto.setTitle(name);
        fakeStoreProductRequestDto.setDescription(description);
        fakeStoreProductRequestDto.setPrice(price);
        fakeStoreProductRequestDto.setImage(imageUrl);
        fakeStoreProductRequestDto.setCategory(category);

        RequestEntity<FakeStoreProductRequestDto> requestEntity = RequestEntity
                .put(Objects.requireNonNull(URI.create("https://fakestoreapi.com/products/" + id)))
                .body(fakeStoreProductRequestDto);

        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.exchange(
                requestEntity,
                FakeStoreProductDto.class);

        FakeStoreProductDto fakeStoreProductDto = responseEntity.getBody();

        if(fakeStoreProductDto == null)
        {
            throw new ProductNotFoundException("The product for id " + id + " does not exist");
        }

        return fakeStoreProductDto.toProduct();
    }
}