package com.mohammed.order.dto;


import java.util.List;

public record PageResponseDto<T>(
        List<T> data,
        int page,
        int size,
        long total,
        int totalPages
) {}