package com.company.service;

import com.company.exception.ResourceNotFoundException;
import com.company.jpa.dto.ProductDto;
import com.company.jpa.entity.Product;
import com.company.jpa.repository.ProductRepository;
import com.company.mapper.ProductMapper;
import com.company.mapper.ProductMapperImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {


    @Mock
    private ProductRepository productRepository;

    private final ProductMapper productMapper = new ProductMapperImpl();


    private ProductServiceImpl productService;

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @BeforeEach
    void init() {
        productService = new ProductServiceImpl(productRepository, productMapper);
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product(1L, "Product1", "Description1", 100.0);
        Product product2 = new Product(2L, "Product2", "Description2", 200.0);
        List<Product> productList = Arrays.asList(product1, product2);

        ProductDto productDto1 = new ProductDto(1L, "Product1", "Description1", 100.0);
        ProductDto productDto2 = new ProductDto(2L, "Product2", "Description2", 200.0);

        List<ProductDto> expectedProductDtos = Arrays.asList(productDto1, productDto2);
        when(productRepository.findAll()).thenReturn(productList);
        List<ProductDto> result = productService.getAllProducts();
        Assertions.assertThatCollection(result).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedProductDtos);
    }


    @Test
    void testGetProductById() {
        Long productId = 1L;
        Product product = new Product(productId, "Product1", "Description1", 100.0);
        ProductDto expectedProductDto = new ProductDto(productId, "Product1", "Description1", 100.0);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        ProductDto result = productService.getProductById(productId);
        assertEquals(result.getName(), expectedProductDto.getName());
    }

    @Test
    void testGetProductByIdNotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(productId));
    }

    @Test
    void testSaveProduct() {
        ProductDto productDto = new ProductDto(null, "New Product", "New Description", 150.0);
        Product savedProduct = new Product(1L, "New Product", "New Description", 150.0);
        when(productRepository.save(any())).thenReturn(savedProduct);
        productService.saveProduct(productDto);
        verify(productRepository, times(1)).save(any());
    }


    @Test
    void testUpdateProduct() {
        Long productId = 1L;
        ProductDto productDto = new ProductDto(productId, "Updated Product", "Updated Description", 200.0);
        Product existingProduct = new Product(productId, "Old Product", "Old Description", 100.0);
        Product updatedProduct = new Product(productId, "Updated Product", "Updated Description", 200.0);
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any())).thenReturn(updatedProduct);
        productService.updateProduct(productId, productDto);
        verify(productRepository, times(1)).save(any());
    }

    @Test
    void testUpdateProductNotFound() {
        Long productId = 1L;
        ProductDto productDto = new ProductDto(productId, "Updated Product", "Updated Description", 200.0);
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(productId, productDto));
    }


    @Test
    void testDeleteProduct() {
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);
        productService.deleteProduct(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testDeleteProductNotFound() {
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(productId));
    }


}
