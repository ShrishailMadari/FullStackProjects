package com.shyloostyle.productservice.controller;

import com.shyloostyle.productservice.dto.ProductRequest;
import com.shyloostyle.productservice.dto.ProductResponse;
import com.shyloostyle.productservice.service.ProductInterface;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    // Inject your service here
     private final ProductInterface productService;
    @Autowired
    public ProductController(ProductInterface productService) {
        this.productService = productService;
    }

    // Add your endpoints here
    // Example endpoint
     @GetMapping("/allProducts")
        public ResponseEntity<List<ProductResponse>> getProducts() {
         List<ProductResponse> allProducts = productService.getAllProducts();
         if (ObjectUtils.isEmpty(allProducts)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
         return new ResponseEntity<>(allProducts, HttpStatus.OK);
     }

    // Example endpoint to get a product by ID
        @GetMapping("/products/{id}")
        public ResponseEntity<ProductResponse> getProductById(@Valid @PathVariable Long id) {
            ProductResponse product = productService.getProductById(id);
            if (ObjectUtils.isEmpty(product.getId())){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(product, HttpStatus.OK);
        }

    // Example endpoint to create a product
         @PostMapping("/create-products")
        public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
            ProductResponse createdProduct = productService.createProduct(productRequest);

            if (ObjectUtils.isEmpty(createdProduct.getId())){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        }
    // Example endpoint to update a product
        @PutMapping("/update/{id}")
        public ResponseEntity<ProductResponse> updateProduct(@Valid @PathVariable Long id,@Valid @RequestBody ProductRequest productRequest) {
            ProductResponse updatedProduct = productService.updateProduct(id, productRequest);
            if (ObjectUtils.isEmpty(updatedProduct.getId())){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }
    // Example endpoint to delete a product
        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> deleteProduct(@Valid @PathVariable Long id) {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    // Example endpoint to get a product by category or name
        @GetMapping("/products/categoryOrName")
        public ResponseEntity<ProductResponse> getProductByCategoryOrName(@RequestParam String category, @RequestParam String name) {
            ProductRequest productRequest = new ProductRequest();
                    productRequest.setName(name);
                    productRequest.setCategory(category);

            ProductResponse product = productService.getProductByCategoryOrName(productRequest);
            if (ObjectUtils.isEmpty(product.getId())){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
}
