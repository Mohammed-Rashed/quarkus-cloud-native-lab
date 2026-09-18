package com.mohammed.order;

import com.mohammed.order.dto.CreateOrderDto;
import com.mohammed.order.dto.OrderResponseDto;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderService {

    public OrderResponseDto create(CreateOrderDto dto){
        return  new OrderResponseDto(
                1L,
                dto.product,
                dto.quantity,
                "Pending"
        );
    }
}
