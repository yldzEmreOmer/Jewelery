package com.star.altineller_kuyumcu.repository;

import com.star.altineller_kuyumcu.model.Product;
import com.star.altineller_kuyumcu.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    List<Product> findByCategory(Category category);

    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Product> findByStockQuantityLessThan(Integer stockThreshold);

    boolean existsByName(String name);
}