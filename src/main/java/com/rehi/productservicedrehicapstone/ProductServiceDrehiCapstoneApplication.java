package com.rehi.productservicedrehicapstone;

import com.rehi.productservicedrehicapstone.models.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductServiceDrehiCapstoneApplication {

    public static void main(String[] args) {

        Product product = new Product();

        SpringApplication.run(ProductServiceDrehiCapstoneApplication.class, args);
    }

}
