package com.mohammed.order.dto;

public record ErrorResponseDto(
        int status,
        String message
) {
}
