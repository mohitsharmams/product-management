package com.company.controller;

import com.company.jpa.dto.ProductDto;
import com.company.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "For Product Operations")
public class ProductController {

    private final ProductService productService;

    /**
     * Retrieves all products.
     *
     * @return a list of all products wrapped in a response entity
     */
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to be retrieved
     * @return the product corresponding to the given ID wrapped in a response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Creates a new product.
     *
     * @param productDTO the product data to be saved
     * @return the created product wrapped in a response entity with HTTP status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDTO) {
        return new ResponseEntity<>(productService.saveProduct(productDTO), HttpStatus.CREATED);
    }

    /**
     * Updates an existing product by its ID.
     *
     * @param id         the ID of the product to be updated
     * @param productDTO the new product data to be updated
     * @return the updated product wrapped in a response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to be deleted
     * @return a confirmation message wrapped in a response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().body("Product deleted with id: " + id);
    }

}
