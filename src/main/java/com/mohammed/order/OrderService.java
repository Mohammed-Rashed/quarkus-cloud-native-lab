package com.mohammed.order;

import com.mohammed.order.dto.CreateOrderDto;
import com.mohammed.order.dto.OrderResponseDto;
import com.mohammed.order.dto.UpdateOrderDto;
import com.mohammed.order.entity.OrderEntity;
import com.mohammed.order.exception.OrderNotFoundException;
import com.mohammed.order.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OrderService {
    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository =  orderRepository;
    }
    public List<OrderResponseDto> getOrders(String status) {
        List<OrderResponseDto> orders = orderRepository.findByStatus(status);
        return orders;
//        if (status == null) {
//            return orders;
//        }
//        return orders.stream()
//                .filter(order -> order.status().equalsIgnoreCase(status))
//                .toList();
    }

    public OrderResponseDto create(@Valid CreateOrderDto dto){
       OrderEntity order = new OrderEntity();
        order.product=dto.product();
        order.quantity=dto.quantity();
        order.status = "PENDING";
        orderRepository.persist(order);
        return new OrderResponseDto(
                order.id,
                order.product,
                order.quantity,
                order.status
        );
    }

    public OrderResponseDto getOrderById(Long id){
        OrderEntity order= orderRepository.findByIdOptional(id)
                .orElseThrow(()-> new OrderNotFoundException(id));
        return new OrderResponseDto(
                order.id,
                order.product,
                order.quantity,
                order.status
        );
    }

    public OrderResponseDto update(@Valid UpdateOrderDto dto, Long id){
        OrderEntity order = orderRepository.findByIdOptional(id)
                .orElseThrow(()-> new OrderNotFoundException(id));
        order.product=dto.product();
        order.quantity=dto.quantity();
        order.status = dto.status();
        return new OrderResponseDto(
                order.id,
                order.product,
                order.quantity,
                order.status
        );
    }
}
