package com.mohammed.order;

import com.mohammed.order.dto.CreateOrderDto;
import com.mohammed.order.dto.OrderResponseDto;
import com.mohammed.order.dto.PageResponseDto;
import com.mohammed.order.dto.UpdateOrderDto;
import com.mohammed.order.entity.OrderEntity;
import com.mohammed.order.exception.OrderNotFoundException;
import com.mohammed.order.messaging.OrderEventProducer;
import com.mohammed.order.repository.OrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;
    public OrderService(OrderRepository orderRepository,OrderEventProducer orderEventProducer) {
        this.orderRepository =  orderRepository;
        this.orderEventProducer = orderEventProducer;
    }
    public PageResponseDto<OrderResponseDto> getOrders(String status, int page, int size) {
        PageResponseDto<OrderResponseDto> orders = orderRepository.findAllOrders(status,page,size);
        return orders;

    }

    public OrderResponseDto create(@Valid CreateOrderDto dto){
       OrderEntity order = new OrderEntity();
        order.product=dto.product();
        order.quantity=dto.quantity();
        order.status = "PENDING";
        orderRepository.persist(order);
        orderEventProducer.send(
                "Order created: " + order.id
        );
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
