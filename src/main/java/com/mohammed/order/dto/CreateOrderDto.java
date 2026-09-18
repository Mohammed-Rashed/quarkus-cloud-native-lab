package com.mohammed.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateOrderDto (
        @NotBlank(message = "Product is required")
        String product,

        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity
){

}
