package com.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the monolithic e-commerce application.
 * This application combines all previously separate microservices:
 * - Product Service (products and categories)
 * - Order Service (orders and order items)
 * - User Service (user management)
 * - Review Service (product reviews)
 * - Frontend Service (web UI)
 */

@SpringBootApplication
public class MonolithicEcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonolithicEcommerceApplication.class, args);
        System.out.println("-- E-commerce Application is running --");
    }
}