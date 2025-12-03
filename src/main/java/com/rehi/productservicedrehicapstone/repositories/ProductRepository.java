package com.rehi.productservicedrehicapstone.repositories;

import com.rehi.productservicedrehicapstone.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


// JPARepository: 1st Argument: Table name;
// 2nd Argument: Type of primary key of the Table
public interface ProductRepository extends JpaRepository<Product, Long>
{
    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByCategory_NameIgnoreCase(String categoryName);

    List<Product> findByIsDeletedFalse();

    java.util.Optional<Product> findByIdAndIsDeletedFalse(long id);
}
