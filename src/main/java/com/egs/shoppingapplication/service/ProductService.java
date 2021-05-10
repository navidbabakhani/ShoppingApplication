package com.egs.shoppingapplication.service;

import com.egs.shoppingapplication.entity.Product;
import com.egs.shoppingapplication.entity.ProductReview;

public interface ProductService {

    Iterable<Product> getAllProducts();

    Product getProduct(long id);

    Product getProductByCode(String productCode);

    String delete(long id);

    Product create(Product product);

    Product update(Product product);

    Product find(long id);

    Iterable<Product> findByProductCode(String productCode);

    Iterable<Product> findByName(String productCode);

    Iterable<Product> findByPrice(double min, double max);

    void addProductReview(ProductReview productReview);
}