package com.egs.shoppingapplication.repository;

import com.egs.shoppingapplication.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findSingleByProductCode(String productCode);

    @Query("Select p from Product p where p.productCode like %:code%")
    Iterable<Product> findByProductCode(@Param("code") String productCode);

    @Query("Select p from Product p where p.name like %:name%")
    Iterable<Product> findByName(@Param("name") String name);

    @Query("Select p from Product p where p.price > :min and p.price< :max")
    Iterable<Product> findByPrice(@Param("min") double min, @Param("max") double max);
}