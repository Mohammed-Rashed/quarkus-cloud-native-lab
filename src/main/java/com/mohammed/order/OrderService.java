package com.mohammed.order;

import com.mohammed.order.dto.CreateOrderDto;
import com.mohammed.order.dto.OrderResponseDto;
import com.mohammed.order.exception.OrderNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OrderService {
    private final List<OrderResponseDto> orders = new ArrayList<>();
    public OrderService() {
        orders.add(new OrderResponseDto(
                1L,
                "MacBook Pro",
                2,
                "PENDING"
        ));

        orders.add(new OrderResponseDto(
                2L,
                "iPhone",
                1,
                "COMPLETED"
        ));
    }
    public List<OrderResponseDto> getOrders(String status) {
        if (status == null) {
            return orders;
        }
        return orders.stream()
                .filter(order -> order.status().equalsIgnoreCase(status))
                .toList();
    }

    public OrderResponseDto create(@Valid CreateOrderDto dto){
        return  new OrderResponseDto(
                1L,
                dto.product(),
                dto.quantity(),
                "Pending"
        );
    }

    public OrderResponseDto getOrderById(Long id){
        return orders.stream()
                .filter(order -> order.id().equals(id))
                .findFirst()
                .orElseThrow(()-> new OrderNotFoundException(id));
    }
}
