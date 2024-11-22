package com.company.mapper;

import com.company.jpa.dto.ProductDto;
import com.company.jpa.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);
}