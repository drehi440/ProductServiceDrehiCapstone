package com.rehi.productservicedrehicapstone.repositories;

import com.rehi.productservicedrehicapstone.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);
}


