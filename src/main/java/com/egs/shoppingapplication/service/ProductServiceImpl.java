package com.egs.shoppingapplication.service;

import com.egs.shoppingapplication.entity.Product;
import com.egs.shoppingapplication.entity.ProductReview;
import com.egs.shoppingapplication.repository.ProductRepository;
import com.egs.shoppingapplication.repository.ProductReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductReviewRepository productReviewRepository;

    @Override
    public Iterable<Product> getAllProducts() {
        //TODO: can have pagination, since JpaRepository supports pagination and sort
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public Product getProductByCode(String productCode) {
        return productRepository
                .findSingleByProductCode(productCode)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with the code: " + productCode));
    }

    @Override
    public String delete(long id) {
        Product product = getProduct(id);//to make sure the product exists
        productRepository.delete(product);
        return "product is deleted";
    }

    @Override
    public Product create(Product product) {
        product.setCreationDate(LocalDate.now());
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        Product productInDb = getProduct(product.getId());//to make sure the product exists
        product.setId(productInDb.getId());
        return productRepository.save(product);
    }

    @Override
    public Product find(long id) {
        return getProduct(id);
    }

    @Override
    public Iterable<Product> findByProductCode(String productCode) {
        return productRepository.findByProductCode(productCode);
    }

    @Override
    public Iterable<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Iterable<Product> findByPrice(double min, double max) {
        return productRepository.findByPrice(min, max);
    }

    @Override
    public void addProductReview(ProductReview productReview) {
        Assert.notNull(productReview, "ProductReview is null!");
        Product product = productReview.getProduct();
        Assert.notNull(product, "The associated Product to this ProductReview is null!");
        Set<ProductReview> reviews = product.getReviews();
        reviews = reviews == null ? new HashSet<>() : reviews;
        reviews.add(productReview);
        productReviewRepository.save(productReview);
        productRepository.save(product);
    }
}