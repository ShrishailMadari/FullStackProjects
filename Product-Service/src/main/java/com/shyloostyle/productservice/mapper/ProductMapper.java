package com.shyloostyle.productservice.mapper;

import com.shyloostyle.productservice.dto.ProductRequest;
import com.shyloostyle.productservice.dto.ProductResponse;
import com.shyloostyle.productservice.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    // Define mapping methods here
    // For example, if you have a Product entity and a ProductResponse DTO, you can define:
     ProductResponse ProductToProductResponse(Product product);
     Product ProductRequestToProduct(ProductRequest productRequest);

}
