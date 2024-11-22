package com.company.jpa.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    @NotBlank(message = "Product name cannot be null or empty")
    private String name;

    private String description;

    @NotNull(message = "Price must be provided")
    @Min(value = 1, message = "Price must be greater than 0")
    private Double price;
}