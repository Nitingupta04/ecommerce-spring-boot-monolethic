package com.ecommerce.service;

import com.ecommerce.entity.Review;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for reviews. Encapsulates business logic for creating,
 * retrieving, updating and deleting reviews, as well as computing
 * average ratings for products.
 */
@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductService productService;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> getReviewsByProduct(Product product) {
        return reviewRepository.findByProduct(product);
    }

    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public List<Review> getReviewsByUser(User user) {
        return reviewRepository.findByUser(user);
    }

    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    public Review createReview(Review review) {
        Review savedReview = reviewRepository.save(review);
        
        // Update product rating after creating a review
        if (review.getProduct() != null) {
            productService.updateProductRating(review.getProduct().getId());
        }
        
        return savedReview;
    }

    public Review updateReview(Long id, Review data) {
        return reviewRepository.findById(id).map(review -> {
            review.setRating(data.getRating());
            review.setComment(data.getComment());
            Review updatedReview = reviewRepository.save(review);
            
            // Update product rating after updating a review
            if (review.getProduct() != null) {
                productService.updateProductRating(review.getProduct().getId());
            }
            
            return updatedReview;
        }).orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public void deleteReview(Long id) {
        Optional<Review> reviewOpt = reviewRepository.findById(id);
        if (reviewOpt.isPresent()) {
            Review review = reviewOpt.get();
            reviewRepository.deleteById(id);
            
            // Update product rating after deleting a review
            if (review.getProduct() != null) {
                productService.updateProductRating(review.getProduct().getId());
            }
        }
    }

    public Double getAverageRating(Long productId) {
        Double avg = reviewRepository.findAverageRatingByProductId(productId);
        return avg != null ? avg : 0.0;
    }
}
