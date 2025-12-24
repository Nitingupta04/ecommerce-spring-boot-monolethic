package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.Review;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.User;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.ReviewService;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Web controller for the monolithic e-commerce application frontend.
 * Handles routing for displaying products, product details, categories,
 * and submitting new reviews. Uses direct service calls instead of REST calls.
 */
@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    /**
     * Display the home page with hero section, featured products, and categories.
     */
    @GetMapping("/")
    public String index(Model model) {
        try {
            // Fetch featured products (limit to 6 for homepage)
            List<Product> featuredProducts = productService.getFeaturedProductsAsList();
            if (featuredProducts.size() > 6) {
                featuredProducts = featuredProducts.subList(0, 6);
            }
            model.addAttribute("featuredProducts", featuredProducts);
        } catch (Exception e) {
            model.addAttribute("featuredProducts", Collections.emptyList());
        }

        try {
            // Fetch categories for the category section
            List<Category> categories = categoryService.getActiveCategories();
            model.addAttribute("categories", categories);
        } catch (Exception e) {
            model.addAttribute("categories", Collections.emptyList());
        }

        return "index";
    }

    /**
     * Display all products page.
     */
    @GetMapping("/products")
    public String products(Model model) {
        try {
            List<Product> products = productService.getAllProductsAsList();
            model.addAttribute("products", products);
        } catch (Exception e) {
            model.addAttribute("products", Collections.emptyList());
        }
        return "products";
    }

    /**
     * Display categories page.
     */
    @GetMapping("/categories")
    public String categories(Model model) {
        try {
            List<Category> categories = categoryService.getActiveCategories();
            model.addAttribute("categories", categories);
        } catch (Exception e) {
            model.addAttribute("categories", Collections.emptyList());
        }
        return "categories";
    }

    /**
     * Display about page.
     */
    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }

    /**
     * Display details for a single product. Includes product details,
     * associated reviews and the average rating.
     */
    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        try {
            // Fetch product
            Optional<Product> productOpt = productService.getProductById(id);
            if (productOpt.isPresent()) {
                model.addAttribute("product", productOpt.get());
            } else {
                return "redirect:/";
            }
        } catch (Exception e) {
            return "redirect:/";
        }
        
        try {
            // Fetch reviews
            List<Review> reviews = reviewService.getReviewsByProductId(id);
            model.addAttribute("reviews", reviews);
        } catch (Exception e) {
            model.addAttribute("reviews", Collections.emptyList());
        }
        
        try {
            // Fetch average rating
            Double averageRating = reviewService.getAverageRating(id);
            model.addAttribute("averageRating", averageRating);
        } catch (Exception e) {
            model.addAttribute("averageRating", 0.0);
        }
        
        // Add a blank review object for the form
        model.addAttribute("newReview", new Review());
        return "product-detail";
    }

    /**
     * Handle submission of a new review from the product detail page.
     * The user is not authenticated in this demo, so a default user is used.
     * After saving the review, the user is redirected back to the product detail page.
     */
    @PostMapping("/product/{id}/review")
    public String submitReview(@PathVariable Long id,
                               @ModelAttribute("newReview") Review review) {
        try {
            // Get the product
            Optional<Product> productOpt = productService.getProductById(id);
            if (productOpt.isPresent()) {
                review.setProduct(productOpt.get());
                
                // Use a default user (create one if it doesn't exist)
                Optional<User> userOpt = userService.getUserById(1L);
                if (!userOpt.isPresent()) {
                    User defaultUser = new User();
                    defaultUser.setUsername("anonymous");
                    defaultUser.setEmail("anonymous@example.com");
                    defaultUser.setPassword("password");
                    defaultUser.setFullName("Anonymous User");
                    defaultUser = userService.registerUser(defaultUser);
                    review.setUser(defaultUser);
                } else {
                    review.setUser(userOpt.get());
                }
                
                // Save the review
                reviewService.createReview(review);
            }
        } catch (Exception e) {
            // If there's an error, just redirect back
        }
        return "redirect:/product/" + id;
    }
}
