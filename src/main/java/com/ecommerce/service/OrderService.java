package com.ecommerce.service;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.User;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Order entities. Encapsulates business logic for
 * creating, updating and retrieving orders and ensures that related
 * operations such as calculating the total price are performed correctly.
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductService productService;

    /**
     * Retrieve all orders.
     *
     * @return list of all orders
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Find a specific order by its ID.
     *
     * @param id the order ID
     * @return optional order
     */
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    /**
     * Retrieve all orders placed by a specific user.
     *
     * @param user the User entity
     * @return list of orders for the user
     */
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    /**
     * Retrieve all orders placed by a specific user ID.
     *
     * @param userId the ID of the user
     * @return list of orders for the user
     */
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    /**
     * Create a new order. This method calculates the total price based on
     * the associated order items and persists both the order and items.
     * Also updates product stock quantities.
     *
     * @param order the order to create
     * @return the created order
     */
    public Order createOrder(Order order) {
        // Calculate total price based on items
        BigDecimal total = BigDecimal.ZERO;
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                // link each item back to the order
                item.setOrder(order);
                if (item.getPrice() != null && item.getQuantity() != null) {
                    total = total.add(item.getPrice().multiply(new BigDecimal(item.getQuantity())));
                }
                
                // Update product stock quantity
                if (item.getProduct() != null && item.getQuantity() != null) {
                    productService.updateStockQuantity(item.getProduct().getId(), item.getQuantity());
                }
            }
        }
        order.setTotalPrice(total);
        // Save the order first to generate an ID (cascade will save items)
        return orderRepository.save(order);
    }

    /**
     * Update the status of an existing order.
     *
     * @param orderId the ID of the order
     * @param status  the new status
     * @return the updated order, if found
     */
    public Optional<Order> updateOrderStatus(Long orderId, Order.OrderStatus status) {
        return orderRepository.findById(orderId).map(order -> {
            order.setStatus(status);
            return orderRepository.save(order);
        });
    }

    /**
     * Delete an order and all its items.
     *
     * @param orderId the ID of the order to delete
     */
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
