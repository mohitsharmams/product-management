package com.company.service;

import com.company.enums.ErrorCodes;
import com.company.exception.ResourceNotFoundException;
import com.company.jpa.dto.ProductDto;
import com.company.jpa.entity.Product;
import com.company.jpa.repository.ProductRepository;
import com.company.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    private final ProductMapper productMapper;

    /**
     * Retrieves all products from the repository and maps them to DTOs.
     *
     * @return a list of all products as ProductDto objects
     */
    @Override
    public List<ProductDto> getAllProducts() {
        log.info("Fetching product list");
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to be retrieved
     * @return the product as a ProductDto
     * @throws ResourceNotFoundException if no product with the given ID is found
     */
    @Override
    public ProductDto getProductById(Long id) {
        log.info("Request to get product by id : {}", id);
        return productMapper.toDto(productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND, "Product not found with id: " + id)));
    }

    /**
     * Creates a new product from the provided ProductDto.
     *
     * @param productDTO the product data to be saved
     * @return the created product as a ProductDto
     */
    @Override
    public ProductDto saveProduct(ProductDto productDTO) {
        log.info("Adding new product with name : {}", productDTO.getName());
        Product product = productMapper.toEntity(productDTO);
        return productMapper.toDto(productRepository.save(product));
    }

    /**
     * Updates an existing product by its ID.
     *
     * @param id         the ID of the product to be updated
     * @param productDTO the new product data to update the product with
     * @return the updated product as a ProductDto
     * @throws ResourceNotFoundException if no product with the given ID is found
     */
    @Override
    public ProductDto updateProduct(Long id, ProductDto productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND, "Product not found with id: " + id));
        log.info("Updating product with id : {}", id);
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        return productMapper.toDto(productRepository.save(product));
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to be deleted
     * @throws ResourceNotFoundException if no product with the given ID is found
     */
    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException(ErrorCodes.PRODUCT_NOT_FOUND, "Product not found with id: " + id);
        }
        log.info("Deleting the product with Id : {}", id);
        productRepository.deleteById(id);
    }
}