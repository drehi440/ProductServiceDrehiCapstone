package com.rehi.productservicedrehicapstone.repositories;

import com.rehi.productservicedrehicapstone.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}


