package com.rehi.productservicedrehicapstone.repositories;

import com.rehi.productservicedrehicapstone.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}


