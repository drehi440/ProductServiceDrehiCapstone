package com.rehi.productservicedrehicapstone.integrations.fakestore;

import com.rehi.productservicedrehicapstone.exceptions.ProductNotFoundException;
import com.rehi.productservicedrehicapstone.models.Product;
import com.rehi.productservicedrehicapstone.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Legacy integration with the public https://fakestoreapi.com for sample products.
 * Kept under the integrations.fakestore package for potential future use or demos.
 */
@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(FakeStoreProductService.class);

    private final RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        FakeStoreProductDto fakeStoreProductDto =
                restTemplate.getForObject(
                        "https://fakestoreapi.com/products/" + id,
                        FakeStoreProductDto.class);

        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException("The product for id " + id + " does not exist");
        }

        log.info("Fetched product {} from FakeStore API", id);
        return fakeStoreProductDto.toProduct();
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class);

        List<Product> products = new ArrayList<>();
        if (fakeStoreProductDtos != null) {
            for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
                products.add(fakeStoreProductDto.toProduct());
            }
        }

        return products;
    }

    @Override
    public Product createProduct(String name,
                                 String description, double price,
                                 String imageUrl, String category) {
        FakeStoreProductRequestDto request = new FakeStoreProductRequestDto();
        request.setTitle(name);
        request.setDescription(description);
        request.setPrice(price);
        request.setImage(imageUrl);
        request.setCategory(category);

        FakeStoreProductDto fakeStoreProductDto = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                request,
                FakeStoreProductDto.class);

        if (fakeStoreProductDto == null) {
            throw new IllegalStateException("Failed to create product in FakeStore API");
        }

        log.info("Created product {} in FakeStore API", fakeStoreProductDto.getId());
        return fakeStoreProductDto.toProduct();
    }
}


