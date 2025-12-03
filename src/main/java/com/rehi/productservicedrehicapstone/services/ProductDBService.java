package com.rehi.productservicedrehicapstone.services;

import com.rehi.productservicedrehicapstone.dtos.CategoryDto;
import com.rehi.productservicedrehicapstone.dtos.ProductResponseDto;
import com.rehi.productservicedrehicapstone.exceptions.ProductNotFoundException;
import com.rehi.productservicedrehicapstone.models.Category;
import com.rehi.productservicedrehicapstone.models.Product;
import com.rehi.productservicedrehicapstone.repositories.CategoryRepository;
import com.rehi.productservicedrehicapstone.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("productDBService")
public class ProductDBService implements ProductService
{

    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public ProductDBService(ProductRepository productRepository,
                            CategoryRepository categoryRepository)
    {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException
    {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if(optionalProduct.isEmpty())
        {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String name, String description, double price,
                                 String imageUrl, String category)
    {
        Product product = new Product();
        product.setProductName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImage(imageUrl);

        Category categoryObj = getCategoryFromDB(category);

        product.setCategory(categoryObj);
        return productRepository.save(product);
    }

    private Category getCategoryFromDB(String name)
    {
        Optional<Category> optionalCategory = categoryRepository.findByCategoryName(name);
        if(optionalCategory.isPresent())
        {
            return optionalCategory.get();
        }

        Category category = new Category();
        category.setCategoryName(name);

        return categoryRepository.save(category);
    }

    /**
     * Returns a page of products mapped to DTOs. This powers the main product
     * catalog listing with server-side pagination.
     */
    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(ProductResponseDto::from);
    }

    /**
     * Returns detailed information about a single product.
     */
    public ProductResponseDto getProductDetails(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product with id " + productId + " not found"));
        return ProductResponseDto.from(product);
    }

    /**
     * Fetch products filtered by category with pagination support.
     */
    public Page<ProductResponseDto> getProductsByCategory(Long categoryId, Pageable pageable) {
        Page<Product> productPage =
                productRepository.findByCategory_CategoryId(categoryId, pageable);
        return productPage.map(ProductResponseDto::from);
    }

    /**
     * Search for products whose name or description contains the keyword
     * (case-insensitive), with pagination.
     */
    public Page<ProductResponseDto> searchProducts(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllProducts(pageable);
        }
        String trimmed = keyword.trim();
        Page<Product> productPage =
                productRepository.findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        trimmed, trimmed, pageable);
        return productPage.map(ProductResponseDto::from);
    }

    /**
     * Lists all categories for building the browse menu in the UI.
     */
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll(Sort.by("categoryName").ascending());
        List<CategoryDto> dtos = new ArrayList<>();
        for (Category category : categories) {
            dtos.add(CategoryDto.from(category));
        }
        return dtos;
    }
}