package com.shyloostyle.productservice.service.impl;

import com.shyloostyle.productservice.dto.ProductRequest;
import com.shyloostyle.productservice.dto.ProductResponse;
import com.shyloostyle.productservice.exception.ProductEmptyException;
import com.shyloostyle.productservice.exception.ProductNotFoundException;
import com.shyloostyle.productservice.mapper.ProductMapper;
import com.shyloostyle.productservice.model.Product;
import com.shyloostyle.productservice.repository.ProductRepository;
import com.shyloostyle.productservice.service.ProductInterface;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductInterface {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper; // Uncomment if using MapStruct for mapping

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        if (ObjectUtils.isEmpty(productRequest)) {
            throw new ProductEmptyException("Product request cannot be null or empty");
        }
        Product product = productMapper.ProductRequestToProduct(productRequest);
        Product saved = productRepository.save(product);
        System.out.println("Product saved: " + saved);
        // Convert Product entity to ProductResponse
        if (saved.getId() == null) {
            throw new ProductNotFoundException("Product could not be saved. Product Name is null.");
        }
        return productMapper.ProductToProductResponse(saved);
    }

    @Override
    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
        // Convert Product entity to ProductResponse
        if (product.getId() == null) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        return productMapper.ProductToProductResponse(product);

    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found");
        }
        // Convert List<Product> to List<ProductResponse>
        return products.stream()
                .map(productMapper::ProductToProductResponse)
                .toList();
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product existing = productRepository.findById(id).get();
        if (ObjectUtils.isEmpty(existing)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        existing.setName(productRequest.getName());
        existing.setDescription(productRequest.getDescription());
        existing.setPrice(productRequest.getPrice());
        existing.setCategory(productRequest.getCategory());

        // Save the updated product
        // Convert Product entity to ProductResponse
        Product save = productRepository.save(existing);
        if (save.getId() == null) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        // Convert Product entity to ProductResponse
        return productMapper.ProductToProductResponse(save);


    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
        productRepository.deleteById(id);
        product.setDeleted(true);

    }

    @Override
    public ProductResponse getProductByCategoryOrName(ProductRequest productRequest) {
        List<Product> product = productRepository.findByCategoryOrName(productRequest.getCategory(), productRequest.getName());

        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found with name: " + productRequest.getName());
        }
        // Convert List<Product> to List<ProductResponse>
        List<ProductResponse> list = product.stream()
                .map(productMapper::ProductToProductResponse).toList();
        if (list.isEmpty()) {
            throw new ProductNotFoundException("Product not found with name: " + productRequest.getName());
        }
        return list.get(0); // Return the first product found
    }
}
