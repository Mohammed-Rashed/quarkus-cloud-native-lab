package com.mohammed.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateOrderDto (
        @NotNull(message = "Product ID is required")
        Long productId,

        @NotBlank(message = "Product is required")
        String product,

        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity
){

}
