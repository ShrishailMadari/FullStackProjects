package com.shyloostyle.productservice.repository;

import com.shyloostyle.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Custom query methods can be defined here if needed
    // For example, findByCategory, findByPriceRange, etc.
     List<Product> findByCategoryOrName(String category,String name);
}
