package com.mohammed.order.dto;

public record OrderResponseDto(
        Long id,
        String product,
        int quantity,
        String status
) {

}
