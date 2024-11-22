package com.company.service;

import com.company.jpa.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mohit Sharma
 */
@Service
public interface ProductService {

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long id);

    ProductDto saveProduct(ProductDto product);

    ProductDto updateProduct(Long id, ProductDto product);

    void deleteProduct(Long id);

}
