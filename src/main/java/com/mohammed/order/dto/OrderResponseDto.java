package com.mohammed.order.dto;

public record OrderResponseDto(
        Long id,
        String product,
        Long productId,
        int quantity,
        String status
) {

}
