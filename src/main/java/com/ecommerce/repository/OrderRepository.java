package com.ecommerce.repository;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Order entities. Provides CRUD operations and custom
 * query methods. Spring Data JPA will automatically implement these
 * methods based on the method names.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Find all orders placed by a specific user.
     *
     * @param user the User entity
     * @return list of orders belonging to the user
     */
    List<Order> findByUser(User user);

    /**
     * Find all orders placed by a specific user ID.
     *
     * @param userId the ID of the user
     * @return list of orders belonging to the user
     */
    List<Order> findByUserId(Long userId);
}
