package com.ecommerce.service;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.Product.ProductStatus;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for product operations. Provides CRUD functionality and
 * various query helpers for the monolithic e-commerce application.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Product> getAllProductsAsList() {
        return productRepository.findAll();
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setOriginalPrice(productDetails.getOriginalPrice());
        product.setStockQuantity(productDetails.getStockQuantity());
        product.setSku(productDetails.getSku());
        product.setImageUrl(productDetails.getImageUrl());
        product.setCategory(productDetails.getCategory());
        product.setStatus(productDetails.getStatus());
        product.setFeatured(productDetails.isFeatured());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> getProductsByStatus(ProductStatus status, Pageable pageable) {
        return productRepository.findByStatus(status, pageable);
    }

    public Page<Product> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }

    public List<Product> getFeaturedProductsAsList() {
        return productRepository.findByIsFeaturedTrue();
    }

    public Page<Product> getFeaturedProducts(Pageable pageable) {
        return productRepository.findByIsFeaturedTrue(pageable);
    }

    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        return productRepository.searchByKeyword(keyword, pageable);
    }

    public Page<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return productRepository.findByPriceRange(minPrice, maxPrice, pageable);
    }

    public Page<Product> getProductsByMinRating(Double minRating, Pageable pageable) {
        return productRepository.findByMinRating(minRating, pageable);
    }

    public Page<Product> getInStockProducts(Pageable pageable) {
        return productRepository.findInStock(pageable);
    }

    public List<Product> getTopRatedProducts() {
        return productRepository.findTop10ByOrderByRatingDesc();
    }

    public List<Product> getLatestProducts() {
        return productRepository.findTop10ByOrderByCreatedAtDesc();
    }

    public void updateStockQuantity(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStockQuantity(product.getStockQuantity() - quantity);
        if (product.getStockQuantity() <= 0) {
            product.setStatus(ProductStatus.OUT_OF_STOCK);
        }

        productRepository.save(product);
    }

    public void updateProductRating(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Double averageRating = reviewRepository.findAverageRatingByProductId(productId);
        Long reviewCount = reviewRepository.countByProductId(productId);

        product.setRating(averageRating != null ? averageRating : 0.0);
        product.setReviewCount(reviewCount != null ? reviewCount.intValue() : 0);

        productRepository.save(product);
    }
}
