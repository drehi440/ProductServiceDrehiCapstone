package com.rehi.productservicedrehicapstone.repositories;

import com.rehi.productservicedrehicapstone.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// JPARepository: 1st Argument: Table name;
// 2nd Argument: Type of primary key of the Table
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategory_CategoryId(Long categoryId, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String keyword,
            String keyword2,
            Pageable pageable);
}

