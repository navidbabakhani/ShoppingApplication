package com.egs.shoppingapplication.repository;

import com.egs.shoppingapplication.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
}