package com.mohammed.order;

import com.mohammed.order.dto.CreateOrderDto;
import com.mohammed.order.dto.OrderResponseDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

@ApplicationScoped
public class OrderService {

    public OrderResponseDto create(@Valid CreateOrderDto dto){
        return  new OrderResponseDto(
                1L,
                dto.product(),
                dto.quantity(),
                "Pending"
        );
    }
}
