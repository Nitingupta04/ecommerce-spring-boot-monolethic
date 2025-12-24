package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.Product.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByStatus(ProductStatus status, Pageable pageable);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    List<Product> findByIsFeaturedTrue();
    
    Page<Product> findByIsFeaturedTrue(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    Page<Product> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    Page<Product> findByPriceRange(@Param("minPrice") BigDecimal minPrice,
                                   @Param("maxPrice") BigDecimal maxPrice,
                                   Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.rating >= :minRating")
    Page<Product> findByMinRating(@Param("minRating") Double minRating, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.stockQuantity > 0")
    Page<Product> findInStock(Pageable pageable);

    List<Product> findTop10ByOrderByRatingDesc();

    List<Product> findTop10ByOrderByCreatedAtDesc();
}
