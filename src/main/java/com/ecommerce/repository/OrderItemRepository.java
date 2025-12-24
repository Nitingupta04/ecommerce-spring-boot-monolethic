package com.ecommerce.repository;

import com.ecommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for OrderItem entities. Spring Data JPA will provide all
 * standard CRUD operations automatically.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
