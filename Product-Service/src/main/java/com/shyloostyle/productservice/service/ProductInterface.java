package com.shyloostyle.productservice.service;

import com.shyloostyle.productservice.dto.ProductRequest;
import com.shyloostyle.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductInterface {
    // Define the methods that will be implemented in the service class
    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();

    ProductResponse updateProduct(Long id, ProductRequest productRequest);

    void deleteProduct(Long id);

    ProductResponse getProductByCategoryOrName(ProductRequest productRequest);
}
